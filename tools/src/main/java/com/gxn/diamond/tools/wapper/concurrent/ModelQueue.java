package com.gxn.diamond.tools.wapper.concurrent;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/3/6
 * /*
 *  堵塞队列，consumer producer 模式
 *  bugFix 2019-03-18 ，
 *  case:任务提交被堵塞，导致job堵塞在submitConditon 中。@BlockAbleThreadPoolExecutorService
 *  原因：由于部分任务被offer后，不能被pull到，导致totalWork超过设定值，被堵塞。经过分析发现pullCount！=offerCount，
 *  问题在于pull没有同步，由于在执行head=head.next()没有和offer（）同步，由于offer中，setTask 和 setNext不是原子行为，所以pull中
 *  check 和 指针下移应当同时考虑同步。
 *  fix:对 指针下移加锁，和offer（）setNext同步，问题解决。
 */
public class ModelQueue<T> {

    private ModelQueue.Model<T> head;
    private ModelQueue.Model<T> last;
    private ReentrantLock takeLock = new ReentrantLock();
    private ReentrantLock putLock = new ReentrantLock();
    private Condition notEmptyCondition = takeLock.newCondition();
    private AtomicInteger totalOffered = new AtomicInteger(0);
    private AtomicInteger totalPulled = new AtomicInteger(0);
    public void offer(T r){
        putLock.lock();
        totalOffered.incrementAndGet();
        try{
            if ( this.last == null) {
                System.out.println("create last");
                ModelQueue.Model newOne = new ModelQueue.Model(r);
                this.head = this.last = newOne;
                newOne.setNext( new ModelQueue.Model() );
            }else{
                this.last = this.last.next;
                this.last.setTask(r);
                this.last.setNext(new ModelQueue.Model<T>());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            putLock.unlock();
        }
        signalNotEmpty();
    }
    private void signalNotEmpty(){
        takeLock.lock();
        try{
            notEmptyCondition.signalAll();
        }finally {
            takeLock.unlock();
        }
    }

    /*
//fix 队列头部被抢先的bug,问题在于head.next 为null 时，指针问题
方法1：和offer设置next时同步
方法2：增加null == head.next 判断，也可以解决问题。
推荐方法2 ，性能更高
 */
    public T pull(){
        takeLock.lock();
        try{
            while ( head == null || head.isEmpty() || null == head.next  ) {
                notEmptyCondition.await();
            }
            ModelQueue.Model<T> temp = head;
            head = head.next;
            totalPulled.incrementAndGet();
            return temp.getTask();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            takeLock.unlock();
        }
    }

    @Data
    private static class Model<T>{
        private T task;
        private ModelQueue.Model<T> next;
        public Model(T task){
            this.task = task;
        }

        public boolean isEmpty(){
            return this.task == null;
        }
        public Model(){}
    }
}