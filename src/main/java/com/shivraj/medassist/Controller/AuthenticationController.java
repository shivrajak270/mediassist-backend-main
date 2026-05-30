package com.shivraj.medassist.Controller;


import com.shivraj.medassist.Dto.PharmasistDTO;
import com.shivraj.medassist.Dto.UsersDTO;
import com.shivraj.medassist.Models.Pharmacists;
import com.shivraj.medassist.Models.Users;
import com.shivraj.medassist.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private UsersService usersService;


    @PostMapping("/resister")
    public ResponseEntity<?> registerUser(@RequestBody Users user){
        UsersDTO usersDTO= usersService.createUsers(user);
        return ResponseEntity.ok(usersDTO);
    }
    @PostMapping("/resister/pharmacy")
    public ResponseEntity<?> registerPharmacy(@RequestBody PharmasistDTO pharmacistsdto){
                PharmasistDTO dto= usersService.createPharmasist(pharmacistsdto);
                return ResponseEntity.ok(dto);
    }

    @GetMapping("/test")
    public ResponseEntity<?> getUsers(){
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users users){
        String ans=usersService.verify(users);
        return ResponseEntity.ok(ans);
    }




}
