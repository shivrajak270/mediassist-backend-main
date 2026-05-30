package com.shivraj.medassist.Repository;

import com.shivraj.medassist.Models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineRepo extends JpaRepository<Medicine,Long> {

    public Medicine findById(long id);
    public Optional<Medicine> findByMedicineNameIgnoreCase(String name);
}
