package com.shivraj.medassist.Converters;

import com.shivraj.medassist.Dto.PharmasistDTO;
import com.shivraj.medassist.Models.Pharmacists;
import org.springframework.stereotype.Component;

@Component
public class PharmacistsConverter {

    public Pharmacists convertDTOtoPharmasist(PharmasistDTO pharmasistDTO){
        Pharmacists pharmacists = new Pharmacists();
        pharmacists.setShopId(pharmasistDTO.getShop_id());
        pharmacists.setShopName(pharmasistDTO.getShopName());
        pharmacists.setShopCountry(pharmasistDTO.getShopCountry());
        pharmacists.setShopCity(pharmasistDTO.getShopCity());
        pharmacists.setShopState(pharmasistDTO.getShopState());
        pharmacists.setUserId(pharmasistDTO.getUserid());
        pharmacists.setLongitude(pharmasistDTO.getLongitude());
        pharmacists.setLatitude(pharmasistDTO.getLatitude());
        pharmacists.setShopOpenHours(pharmasistDTO.getShopOpenHours());
        return pharmacists;
    }
    public PharmasistDTO convertPharmasisttoDTO(Pharmacists pharmacists){
        PharmasistDTO pharmasistDTO = new PharmasistDTO();
        pharmasistDTO.setShop_id(pharmacists.getShopId());
        pharmasistDTO.setShopName(pharmacists.getShopName());
        pharmasistDTO.setShopCountry(pharmacists.getShopCountry());
        pharmasistDTO.setShopCity(pharmacists.getShopCity());
        pharmasistDTO.setShopState(pharmacists.getShopState());
        pharmasistDTO.setUserid(pharmacists.getUserId());
        pharmasistDTO.setLongitude(pharmacists.getLongitude());
        pharmasistDTO.setLatitude(pharmacists.getLatitude());
        pharmasistDTO.setShopOpenHours(pharmacists.getShopOpenHours());
        return pharmasistDTO;
    }
}
