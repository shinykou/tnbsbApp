package com.gxn.diamond.tools.wapper.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimitListener {
    private transient RateLimitModel head;
    private transient RateLimitModel last;

    private ReentrantLock putLock = new ReentrantLock();

    private ReentrantLock takeLock = new ReentrantLock();
    private Condition emptyCondition = takeLock.newCondition();

    public void setRateLimit(String key,float rate){
        putLock.lock();
        try{
            RateLimitModel rateLimitModel = new RateLimitModel(key,rate);
            if ( last == null ){
                head = last = rateLimitModel;
            }else{
                last = last.next = rateLimitModel;
            }
        }finally {
            putLock.unlock();
        }
        signalNotEmpty();
    }

    public RateLimitModel getRateLimitModel(){
        takeLock.lock();
        try{
            if ( head == null ){
                emptyCondition.await();
            }
            RateLimitModel rateLimitModel = head;
            if ( head == last ){
                head = last = null;
            }else{
                head = head.next;
            }
            return rateLimitModel;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            takeLock.unlock();
        }
    }

    private void signalNotEmpty(){
        takeLock.lock();
        try{
            emptyCondition.signalAll();
        }finally {
            takeLock.unlock();
        }
    }

    @Data
    @AllArgsConstructor
    public static class RateLimitModel{
        private String key;
        private float rate;
        private RateLimitModel next;

        public RateLimitModel(String key, float rate) {
            this.key=key;
            this.rate=rate;
        }
    }
}
