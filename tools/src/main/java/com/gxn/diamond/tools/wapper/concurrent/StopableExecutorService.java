package com.gxn.diamond.tools.wapper.concurrent;

import java.util.concurrent.ExecutorService;
/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/1/10
 */
public interface StopableExecutorService extends ExecutorService{

    void stopWorkJob();
}
