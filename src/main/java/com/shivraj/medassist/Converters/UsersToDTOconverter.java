package com.shivraj.medassist.Converters;

import com.shivraj.medassist.Dto.UsersDTO;
import com.shivraj.medassist.Models.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersToDTOconverter {

    public Users converttoUsers(UsersDTO usersDTO){
        Users users = new Users();
        users.setId(usersDTO.getId());
        users.setUsername(usersDTO.getUsername());
        users.setPassword(usersDTO.getPassword());
        users.setEmail(usersDTO.getEmail());
        users.setRole(usersDTO.getRole());
        return users;
    }
    public UsersDTO converttoUsersDTO(Users users){
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setId(users.getId());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setRole(users.getRole());
        return usersDTO;
    }
}
