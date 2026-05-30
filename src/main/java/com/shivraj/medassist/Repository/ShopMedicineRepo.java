package com.shivraj.medassist.Repository;


import com.shivraj.medassist.Models.ShopMedicineStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopMedicineRepo extends JpaRepository<ShopMedicineStock,Long> {

    List<ShopMedicineStock> findByShopId(Long shopId);
    ShopMedicineStock findByShopIdAndMedicineId(Long shopId,Long medicineId);
    ShopMedicineStock deleteByShopIdAndMedicineId(Long shopId,Long medicineId);
    ShopMedicineStock findByMedicineId(long medicineId);

    @Query(nativeQuery = true,value="select * from shop_medicine_stock where medicine_id=:medicineId")
    List<ShopMedicineStock> findByMedicineIdlist(long medicineId);


}
