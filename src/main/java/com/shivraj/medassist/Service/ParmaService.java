package com.shivraj.medassist.Service;


import com.shivraj.medassist.Dto.StockViewDTO;
import com.shivraj.medassist.Models.UserPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ParmaService {
    public void processExcel(MultipartFile file, UserPrincipal user);
    public List<StockViewDTO>allstocks(String username);

    public StockViewDTO addmedicine(String username,StockViewDTO stockViewDTO);

    public StockViewDTO updateStocks(String username, StockViewDTO stockViewDTO);

    String deletestocks(String username, StockViewDTO stockViewDTO);

}
