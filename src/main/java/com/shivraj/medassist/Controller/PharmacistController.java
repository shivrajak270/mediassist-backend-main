package com.shivraj.medassist.Controller;


import com.shivraj.medassist.Dto.StockViewDTO;
import com.shivraj.medassist.Models.UserPrincipal;
import com.shivraj.medassist.Service.ParmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pharmasists")
@CrossOrigin(origins = "*")
public class PharmacistController {

    @Autowired
    private ParmaService parmaService;


    @GetMapping("/check")
    public ResponseEntity<?> getPharmacists(){
        return new ResponseEntity<>("this is pharma",HttpStatus.OK);
    }

    @PostMapping("/upload-stock-excel")
    public ResponseEntity<?> uploadStockExcel(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserPrincipal user){
        parmaService.processExcel(file,user);
        return ResponseEntity.ok("processed successfully");

    }

    @GetMapping("/stockscheck")
    public ResponseEntity<?>getstock(@AuthenticationPrincipal UserPrincipal user){
        String username=user.getUsername();
        List<StockViewDTO>listofstocks=parmaService.allstocks(username);
        return new ResponseEntity<>(listofstocks,HttpStatus.OK);

    }

    @PatchMapping("/stock/update")
    public ResponseEntity<StockViewDTO> updateStock(@AuthenticationPrincipal UserPrincipal user,@RequestBody StockViewDTO stockViewDTO){
        String username=user.getUsername();
        StockViewDTO stockViewDTO1=parmaService.updateStocks(username,stockViewDTO);
        return new ResponseEntity<>(stockViewDTO1,HttpStatus.OK);
    }
    @DeleteMapping("/stock/delete")
    public ResponseEntity<String> deleteStock(@AuthenticationPrincipal UserPrincipal user,@RequestBody StockViewDTO stockViewDTO){
        String username=user.getUsername();
        String stockViewDTO1=parmaService.deletestocks(username,stockViewDTO);
        return new ResponseEntity<>(stockViewDTO1,HttpStatus.OK);


    }




    @PostMapping("/stocks/add")
    public ResponseEntity<StockViewDTO> addStock(@AuthenticationPrincipal UserPrincipal user,@RequestBody StockViewDTO stockViewDTO){
        String username=user.getUsername();
        StockViewDTO addresponse=parmaService.addmedicine(username,stockViewDTO);
        return new ResponseEntity<>(addresponse,HttpStatus.OK);

    }
}
