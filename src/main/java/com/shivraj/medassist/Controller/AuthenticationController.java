package com.shivraj.medassist.Controller;


import com.shivraj.medassist.Dto.PharmasistDTO;
import com.shivraj.medassist.Dto.UsersDTO;
import com.shivraj.medassist.Models.Pharmacists;
import com.shivraj.medassist.Models.Users;
import com.shivraj.medassist.Repository.UsersRepo;
import com.shivraj.medassist.Service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepo usersRepo;



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
    public ResponseEntity<String> login(@RequestBody Users users, HttpServletResponse response){
        String ans=usersService.verify(users);
        ResponseCookie cookie = ResponseCookie.from("jwt", ans)
                .httpOnly(true)
                .secure(false) // true in production HTTPS
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok("Login Success");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {

        String username = auth.getName();

        Users user = usersRepo.findByUsername(username);

        return ResponseEntity.ok(
                Map.of(
                        "username", user.getUsername(),
                        "role", user.getRole()
                )
        );
    }




}
