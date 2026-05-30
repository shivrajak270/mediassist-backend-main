package com.shivraj.medassist.Dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MedicineRequestDTO {
    private String medicineName;
    private long Id;
}
