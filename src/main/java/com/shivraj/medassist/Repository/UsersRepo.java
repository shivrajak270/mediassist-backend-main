package com.shivraj.medassist.Repository;


import com.shivraj.medassist.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    public Users findByUsername(String username);

    public Users findById(long id);



}
