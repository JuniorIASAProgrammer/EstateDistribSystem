package com.realestate.serviceestate.service;

import com.realestate.serviceestate.api.dto.EstateDto;
import com.realestate.serviceestate.api.enums.EstateDealEnum;
import com.realestate.serviceestate.repo.EstateRepo;
import com.realestate.serviceestate.repo.model.Description;
import com.realestate.serviceestate.repo.model.Estate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public final class EstateService {

    private final EstateRepo estateRepo;
    RestTemplate restTemplate = new RestTemplate();

    public List<Estate> fetchAll(){
        return estateRepo.findAll();
    }

    public Estate fetchById(long id) throws IllegalArgumentException{
        return estateRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

    public long create(EstateDto estate){
        EstateDealEnum dealType = estate.getDealType();
        Description description = estate.getDescription();
        String email = estate.getOwner();
        long owner = restTemplate.getForObject("http://127.0.0.1:30001/EstateMarketplace/user/getByEmail/email={email}", Long.class, email);
        Estate newEstate = new Estate(dealType, description, owner);
        Estate savedEstate = estateRepo.save(newEstate);
        return savedEstate.getId();
    }

    public void update(long id, EstateDto estateDto){
        EstateDealEnum dealType = estateDto.getDealType();
        String city = estateDto.getDescription().getCity();
        String district = estateDto.getDescription().getDistrict();
        String adress = estateDto.getDescription().getAddress();
        final Estate maybeEstate = estateRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Estate not found"));
        if (dealType!=null && !dealType.toString().isEmpty() && dealType!=maybeEstate.getDealtype()) maybeEstate.setDealType(dealType);
        if (city!=null && !city.isEmpty()) maybeEstate.setCity(city);
        if (district!=null && !district.isEmpty()) maybeEstate.setDistrict(district);
        if (adress!=null && !adress.isEmpty()) maybeEstate.setAddress(adress);
        estateRepo.save(maybeEstate);
    }

    public void delete(long id){
        estateRepo.deleteById(id);
    }

}
