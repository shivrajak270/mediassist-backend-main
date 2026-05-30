package com.shivraj.medassist.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    private String shopName;
    private Double longitude;
    private Double latitude;
    private String addressLine;
   private String contact;
   private String city;
   private String state;
   private String country;
   private String email;
}
