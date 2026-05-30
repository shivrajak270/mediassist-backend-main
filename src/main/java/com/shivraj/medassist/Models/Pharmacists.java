package com.shivraj.medassist.Models;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pharmacists implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id")
    private Long shopId;
    private String shopName;
    private String shopState;
    private String shopCity;
    private String shopCountry;
    private String shopOpenHours;
    private Double latitude;
    @Column(name = "user_id")
    private Long userId;
    private Double longitude;

    private String addressLine;
}
