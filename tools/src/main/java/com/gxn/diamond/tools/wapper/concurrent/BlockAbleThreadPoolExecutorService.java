package com.gxn.diamond.tools.wapper.concurrent;

import com.google.common.base.Stopwatch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.time.temporal.ChronoUnit.SECONDS;
/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
/*
 设置队列大小，当任务超过队列大小的时候，自动堵塞。
 例如如果设置了队列大小为10，maxSize = 2, 那么允许最大提交的任务为12，超过此数值，自动堵塞。
 */
public class BlockAbleThreadPoolExecutorService {

    public static void main(String[] args) throws InterruptedException {
        BlockAbleThreadPoolExecutorService stopableThreadPoolExecutorService = new BlockAbleThreadPoolExecutorService(10,20,15);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i=0;i<1000;i++){
            final  int j= i;
            stopableThreadPoolExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        System.out.println("job start:"+j+" ");
                        Thread.sleep(10);
                        System.out.println("job end:"+j);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            });

        }
        countDownLatch.await();
        System.out.println(stopwatch.stop().elapsed(TimeUnit.SECONDS));


    }

    private ExecutorService executorService;
    private ReentrantLock submitLock = new ReentrantLock();
    private Condition submitConditon = submitLock.newCondition();
    private final int maxQueueCount;
    private Thread submitTaskThread ;
    private ModelQueue<Runnable> taskQueues;
    private Thread consumeResultThread;
    private ModelQueue<Future> futureModelQueue;
    private AtomicInteger totalWorker = new AtomicInteger(0);
    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger submitCount = new AtomicInteger(0);

    public BlockAbleThreadPoolExecutorService(int coreSize, int maxSize, int queueSize){
        this.maxQueueCount = queueSize+ (maxSize>coreSize?maxSize:coreSize);
        executorService = new ThreadPoolExecutor(coreSize,maxSize,3000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue(this.maxQueueCount));

        init();
    }

    public void execute(Runnable runnable){
        while (totalWorker.get() >= maxQueueCount){
            this.submitLock.lock();
            try{
                while ( totalWorker.get() >= maxQueueCount  ){
                    submitConditon.await();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                submitLock.unlock();
            }
        }
        //offer 要在锁外执行，避免死锁发生。
        totalWorker.incrementAndGet();
        taskQueues.offer(runnable );
//        System.out.println("add job, id:"+id+" totalWork:"+totalWorker.get()+" successReturn:"+successCount.get()+" submitCount:"+submitCount.get());
    }

    private void init(){
        taskQueues = new ModelQueue<Runnable>();
        futureModelQueue = new ModelQueue<Future>();
        submitTaskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Future<Boolean> result=null;
                    try{
                        Runnable runnable = taskQueues.pull();
                        result = executorService.submit(new Callable<Boolean>() {
                            @Override
                            public Boolean call() {
                                try {
                                    runnable.run();
                                } finally {
                                    return true;
                                }
                            }
                        });
                    }catch (Exception e){
                        System.out.println("submit fail");
                    }finally {
                        futureModelQueue.offer(result);
                        submitCount.incrementAndGet();
                    }
                }
            }
        });
        consumeResultThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Future<Boolean> future = futureModelQueue.pull();
                        try{
                            future.get();
                            successCount.incrementAndGet();
//                            System.out.println("check.totalWork:"+totalWorker.get()+" successReturn:"+successCount.get()+" submitCount:"+submitCount.get());
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            signalSubmitAble();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        submitTaskThread.start();
        consumeResultThread.start();
    }

    private void signalSubmitAble(){
        submitLock.lock();
        int count = totalWorker.decrementAndGet();
        submitConditon.signalAll();
//        System.out.println("signalSubmit.totalWork:"+count+" successReturn:"+successCount.get()+" submitCount:"+submitCount.get());
        submitLock.unlock();
    }
}
