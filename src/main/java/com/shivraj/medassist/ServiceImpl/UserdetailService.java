package com.shivraj.medassist.ServiceImpl;

import com.shivraj.medassist.Models.UserPrincipal;
import com.shivraj.medassist.Models.Users;
import com.shivraj.medassist.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserdetailService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(usersRepo.findByUsername(username)==null){
            throw new UsernameNotFoundException(username);
        }
        Users user=usersRepo.findByUsername(username);
        return new UserPrincipal(user);
    }
}