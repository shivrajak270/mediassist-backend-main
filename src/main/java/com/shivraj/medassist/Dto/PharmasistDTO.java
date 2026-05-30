package com.shivraj.medassist.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PharmasistDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shop_id;
    private String shopName;
    private String shopState;
    private String shopCity;
    private String shopCountry;
    private String shopOpenHours;
    private Double latitude;
    private Long userid;
    private Double longitude;
}
