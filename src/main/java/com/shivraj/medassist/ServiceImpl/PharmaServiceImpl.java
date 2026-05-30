package com.shivraj.medassist.ServiceImpl;

import com.shivraj.medassist.Dto.StockViewDTO;
import com.shivraj.medassist.Exceptions.DbException;
import com.shivraj.medassist.Exceptions.MedicineAlredypresent;
import com.shivraj.medassist.Exceptions.MedicineNotFoundException;
import com.shivraj.medassist.Exceptions.QuntityException;
import com.shivraj.medassist.Models.Medicine;
import com.shivraj.medassist.Models.ShopMedicineStock;
import com.shivraj.medassist.Models.UserPrincipal;
import com.shivraj.medassist.Repository.MedicineRepo;
import com.shivraj.medassist.Repository.PharmasistRepo;
import com.shivraj.medassist.Repository.ShopMedicineRepo;
import com.shivraj.medassist.Repository.UsersRepo;
import com.shivraj.medassist.Service.ParmaService;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PharmaServiceImpl implements ParmaService {
    @Autowired
    private UsersRepo userRepo;

    @Autowired
    private ShopMedicineRepo shopMedicineRepo;

    @Autowired
    private PharmasistRepo pharmasistRepo;

    @Autowired
    private MedicineRepo medicineRepo;

    @Override
    public void processExcel(MultipartFile file, UserPrincipal user) {
        try{
            Workbook  workbook =new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row row=sheet.getRow(i);
                if(row==null)
                    continue;
                String medicineName=row.getCell(0).getStringCellValue();
                Long quantity=(long)row.getCell(1).getNumericCellValue();
                double price=row.getCell(2).getNumericCellValue();
                System.out.println(quantity);
                System.out.println(price);
                System.out.println(medicineName);
            }


        }catch (Exception e){
            throw new RuntimeException("excel file processing failed ");
        }

    }

    @Cacheable(value = "stocks", key = "#username")
    @Override
    public List<StockViewDTO> allstocks(String username) {
        long id= userRepo.findByUsername(username).getId();
        Long shopId=pharmasistRepo.findByUserId(id).getShopId();
        List<ShopMedicineStock>allstocks=shopMedicineRepo.findByShopId(shopId);
        List<StockViewDTO>result=new ArrayList<>();
        for(ShopMedicineStock shopMedicineStock:allstocks){
            StockViewDTO stockViewDTO=new StockViewDTO();
            String medicine_name=medicineRepo.findById(shopMedicineStock.getMedicineId()).get().getMedicineName();
            stockViewDTO.setMedicine_name(medicine_name);
            stockViewDTO.setQuantity(shopMedicineStock.getQuantity());
            stockViewDTO.setPrice(shopMedicineStock.getPrice());
            result.add(stockViewDTO);
        }

        return result;
    }

    @CacheEvict(value = "stocks", key = "#username")
    @Override
    public StockViewDTO addmedicine(String username,StockViewDTO stockViewDTO) {

        if (stockViewDTO.getQuantity() <= 0) {
            throw new QuntityException("Quantity must be greater than 0");
        }
        if (stockViewDTO.getPrice() <= 0) {
            throw new QuntityException("Price must be greater than 0");
        }
        long id= userRepo.findByUsername(username).getId();
        long shopId=pharmasistRepo.findByUserId(id).getShopId();
        Medicine medicine=medicineRepo.findByMedicineNameIgnoreCase(stockViewDTO.getMedicine_name()).orElseThrow(() -> new MedicineNotFoundException("Medicine not found wrong medicine entered"));
        long medicineId=medicine.getMedicine_id();
        ShopMedicineStock shopMedicineStock=new ShopMedicineStock();
        shopMedicineStock.setMedicineId(medicineId);
        shopMedicineStock.setQuantity(stockViewDTO.getQuantity());
        shopMedicineStock.setPrice(stockViewDTO.getPrice());
        shopMedicineStock.setShopId(shopId);
        ShopMedicineStock ispresent=shopMedicineRepo.findByShopIdAndMedicineId(shopId,medicineId);
        if(ispresent!=null){
            throw new MedicineAlredypresent("Medicine Alredy Present please try editing the editing stock and price ");
        }
        shopMedicineRepo.save(shopMedicineStock);
        StockViewDTO result=new StockViewDTO();
        result.setMedicine_name(stockViewDTO.getMedicine_name());
        result.setQuantity(stockViewDTO.getQuantity());
        result.setPrice(stockViewDTO.getPrice());
        return result;

    }

    @CacheEvict(value = "stocks", key = "#username")
    @Override
    public StockViewDTO updateStocks(String username, StockViewDTO stockViewDTO) {
        long id= userRepo.findByUsername(username).getId();
        long shopId=pharmasistRepo.findByUserId(id).getShopId();
        Medicine medicine=medicineRepo.findByMedicineNameIgnoreCase(stockViewDTO.getMedicine_name()).orElseThrow(() -> new RuntimeException("Medicine not found wrong medicine entered"));
        long medicineId=medicine.getMedicine_id();
        ShopMedicineStock shopMedicineStock=shopMedicineRepo.findByShopIdAndMedicineId(shopId,medicineId);
        if(shopMedicineStock.getQuantity()!=stockViewDTO.getQuantity())
        {
            shopMedicineStock.setQuantity(stockViewDTO.getQuantity());
        }
        if(shopMedicineStock.getPrice()!=stockViewDTO.getPrice())
        {
            shopMedicineStock.setPrice(stockViewDTO.getPrice());
        }
        try {
            shopMedicineRepo.save(shopMedicineStock);
        }catch (Exception e){
            throw new DbException("db exception "+e.getMessage());
        }
        StockViewDTO result=new StockViewDTO();
        result.setMedicine_name(stockViewDTO.getMedicine_name());
        result.setQuantity(stockViewDTO.getQuantity());
        result.setPrice(stockViewDTO.getPrice());
        return result;
    }

    @CacheEvict(value = "stocks", key = "#username")
    @Override
    @Transactional
    public String deletestocks(String username, StockViewDTO stockViewDTO) {
        long id= userRepo.findByUsername(username).getId();
        long shopId=pharmasistRepo.findByUserId(id).getShopId();
        Medicine medicine=medicineRepo.findByMedicineNameIgnoreCase(stockViewDTO.getMedicine_name()).orElseThrow(() -> new RuntimeException("Medicine not found wrong medicine entered"));
        long medicineId=medicine.getMedicine_id();
        try {
            ShopMedicineStock shopMedicineStock = shopMedicineRepo.deleteByShopIdAndMedicineId(shopId, medicineId);
        }catch (Exception e){
            throw new DbException("a db exception"+e.getMessage());
        }
        String result="deleted the entry which had the ShopId as "+shopId +"and the medicine was"+stockViewDTO.getMedicine_name();
        return result;
    }
}
