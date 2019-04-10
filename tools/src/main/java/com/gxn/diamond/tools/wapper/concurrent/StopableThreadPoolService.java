package com.gxn.diamond.tools.wapper.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
public class StopableThreadPoolService extends ThreadPoolExecutor implements StopableExecutorService{

    private volatile AtomicBoolean executeFlag = new AtomicBoolean(true);

    public StopableThreadPoolService(int corePoolSize, int maximumPoolSize,  int workQueue) {
        super(corePoolSize, maximumPoolSize, 3000, TimeUnit.MICROSECONDS, new LinkedBlockingQueue(workQueue));
    }

    @Override
    public void execute(Runnable runnable){
        try{
            super.execute(new Runnable() {
                @Override
                public void run() {
                    if ( executeFlag.get() == false ){
                        System.out.println("StopableThreadPoolService.executeFlag == false,so return");
                        return;
                    }
                    runnable.run();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stopWorkJob(){
        executeFlag.set(false);
    }

}
