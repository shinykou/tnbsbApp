package com.gxn.diamond.service;

import com.google.common.collect.Sets;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ServiceImpl {


    private int i=6;

    @Cacheable(value = "10800",key = "'hotIdSetOfShowDayCinemaMovie,'+#model1.showDay+';'+#model1.cinemaId+';'+#model1.movieId+';'+#model1.src+',SET'",
            unless = "#result.isEmpty()")
    public Set<Integer> add(Model1 model1){
        System.out.println("doning");
        return Sets.newHashSet(1,2,3,5);
    }


    @CachePut(value = "10800",key = "'hotIdSetOfShowDayCinemaMovie,'+#model1.showDay+';'+#model1.cinemaId+';'+#model1.movieId+';'+#model1.src+',SET'",
            unless = "#result == 0")
    public int addmore(Model1 model1){
        System.out.println("doning");
        return i++;
    }

    @CacheEvict(value = "10800",key = "'hotIdSetOfShowDayCinemaMovie,'+#model1.showDay+';'+#model1.cinemaId+';'+#model1.movieId+';'+#model1.src")
    public void delete(Model1 model1){
        System.out.println("doning");
    }



}
