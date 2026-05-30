package com.shivraj.medassist.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class  ShopMedicineStock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "medicine_id")
    private Long medicineId;
    @Column(name = "shop_id")
    private Long shopId;
    private Long quantity;
    private double price;


    @CreationTimestamp
    @Column(name="creationdate")
    private LocalDateTime creationdate;

    @UpdateTimestamp
    @Column(name="last_updated")
    private LocalDateTime last_updated;



}
