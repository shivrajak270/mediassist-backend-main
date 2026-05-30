package com.shivraj.medassist.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
public class StockViewDTO implements Serializable {

    private String medicine_name;
    private long quantity;
    private double price;
}
