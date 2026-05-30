package com.shivraj.medassist.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shivraj.medassist.Converters.PharmacistsConverter;
import com.shivraj.medassist.Converters.UsersToDTOconverter;
import com.shivraj.medassist.Dto.MailDTO;
import com.shivraj.medassist.Dto.PharmasistDTO;
import com.shivraj.medassist.Dto.ShopDto;
import com.shivraj.medassist.Dto.UsersDTO;
import com.shivraj.medassist.Models.Medicine;
import com.shivraj.medassist.Models.Pharmacists;
import com.shivraj.medassist.Models.ShopMedicineStock;
import com.shivraj.medassist.Models.Users;
import com.shivraj.medassist.Repository.MedicineRepo;
import com.shivraj.medassist.Repository.PharmasistRepo;
import com.shivraj.medassist.Repository.ShopMedicineRepo;
import com.shivraj.medassist.Repository.UsersRepo;
import com.shivraj.medassist.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UsersServiceImpl implements UsersService {


    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PharmasistRepo pharmasistRepo;


    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private ShopMedicineRepo shopMedicineRepo;


    @Autowired
    private UsersToDTOconverter UsersToDTOconverter;

    @Autowired
    private PharmacistsConverter PharmacistsConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServiceImpl jwtServiceImpl;

    @Autowired
     private NotificationPublisher notificationPublisher;


    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(10);


    @Override
    public UsersDTO createUsers(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        Users user=usersRepo.save(users);
        return UsersToDTOconverter.converttoUsersDTO(user);
    }

    @Override
    public PharmasistDTO createPharmasist(PharmasistDTO PharmasistDTO) {
        Pharmacists pharmacists=pharmasistRepo.save(PharmacistsConverter.convertDTOtoPharmasist(PharmasistDTO));
        return PharmacistsConverter.convertPharmasisttoDTO(pharmacists);
    }


    @Override
    public String verify(Users users){


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
        if(authentication.isAuthenticated()){
            Users dbUser=usersRepo.findByUsername(users.getUsername());

            return  jwtServiceImpl.generateToken(dbUser.getUsername(),dbUser.getRole());
        }
        else{
            throw new BadCredentialsException("Invalid username or password");
        }

    }
    @Cacheable(value = "medicines")
    @Override
    public List<Medicine> searchmedicine() {
        List<Medicine>allmedines=new ArrayList<>();
        try{
            allmedines=medicineRepo.findAll();
        }
        catch (Exception e){
            System.out.println(e);
        }
        if(!allmedines.isEmpty()){
            return allmedines;

        }
        return null;
    }

    @Override
    public List<ShopDto> searchShop(long id, String medicine_name) {
        List<ShopMedicineStock>shopList=shopMedicineRepo.findByMedicineIdlist(id);
        List<ShopDto>shopDtoList=new ArrayList<>();
        for(ShopMedicineStock shop:shopList){
            long shopId=shop.getShopId();
            Pharmacists pharma=pharmasistRepo.findByShopId(shopId);
            Optional<Users> user=usersRepo.findById(pharma.getUserId());
            String email=user.get().getEmail();
            ShopDto dto=new ShopDto();
            dto.setShopName(pharma.getShopName());
            dto.setLongitude(pharma.getLongitude());
            dto.setLatitude(pharma.getLatitude());
            dto.setCity(pharma.getShopCity());
            dto.setCountry(pharma.getShopCountry());
            dto.setState(pharma.getShopState());
            dto.setAddressLine(pharma.getAddressLine());
            dto.setEmail(email);
            if(user.isPresent()){
                dto.setContact(user.get().getEmail());
            }
            shopDtoList.add(dto);
        }
        return shopDtoList;
    }

    @Override
    public String sendReserve(
            String username,
            MailDTO dto) throws JsonProcessingException {

        dto.setUserName(username);
        Users user=usersRepo.findByUsername(username);
        dto.setUserEmail(user.getEmail());
        notificationPublisher.publish(dto);

        return "Reservation request sent successfully";
    }


}
