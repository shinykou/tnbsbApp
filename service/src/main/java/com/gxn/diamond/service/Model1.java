package com.gxn.diamond.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model1 implements Serializable{

    private String showDay;
    private int cinemaId;
    private int movieId;
    private int src;
}
