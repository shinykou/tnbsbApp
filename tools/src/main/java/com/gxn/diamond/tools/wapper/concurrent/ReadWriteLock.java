package com.gxn.diamond.tools.wapper.concurrent;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
created by gaoxiaoning at 18/7/19
fix read write unReentrant at 18/8/24

 */
public class ReadWriteLock {

    private volatile AtomicInteger writer= new AtomicInteger();
    private volatile AtomicInteger reader = new AtomicInteger();
    private volatile AtomicInteger readRequest = new AtomicInteger();
    private volatile static ReadWriteLock[] locks = new ReadWriteLock[12];
    private static ReentrantLock sncLock = new ReentrantLock();
    private Condition lockCondition = sncLock.newCondition();

    public synchronized static ReadWriteLock getLock(Object key){
        int index = Math.abs(key.hashCode())%12;
        ReadWriteLock readWriteLock = locks[index];
        if ( readWriteLock == null){
            final Lock lock = sncLock;
            try{
                lock.lock();
                readWriteLock = locks[index];
                if ( readWriteLock == null ){
                    readWriteLock = new ReadWriteLock();
                    locks[index]=readWriteLock;
                }
            }finally {
                lock.unlock();
            }
        }
        return readWriteLock;
    }

    public void lockRead() throws InterruptedException{
        readRequest.incrementAndGet();
        if ( writer.get()>0 && reader.get() <= 0 ){
            Lock lock = sncLock;
            try{
                lock.lock();
                while( writer.get()>0 && reader.get() <= 0){
                    lockCondition.await();
                }
            }finally {
                lock.unlock();
            }
        }
        readRequest.decrementAndGet();
        reader.incrementAndGet();
    }

    public void unLockRead(){
        reader.decrementAndGet();
        Lock lock = sncLock;
        try{
            lock.lock();
            lockCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public  void lockWrite() throws InterruptedException{
        Lock lock = sncLock;
        if ( (reader.get()>0 || readRequest.get()>0 ) && writer.get() <= 0 ){
            try{
                lock.lock();
                while( (reader.get()>0 || readRequest.get()>0 ) && writer.get() <= 0 ){
                    lockCondition.await();
                }
            }finally {
                lock.unlock();
            }
        }
        writer.incrementAndGet();
    }

    public void unLockWrite(){
        writer.decrementAndGet();
        Lock lock =sncLock;
        try{
            lock.lock();
            lockCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
