package com.realestate.servicedeal.service;

import com.realestate.servicedeal.api.enums.DealStatusEnum;
import com.realestate.servicedeal.repo.DealRepo;
import com.realestate.servicedeal.repo.OfferRepo;
import com.realestate.servicedeal.repo.model.Deal;
import com.realestate.servicedeal.repo.model.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DealService {

    private final DealRepo dealRepo;
    private final OfferRepo offerRepo;
    RestTemplate restTemplate = new RestTemplate();

    public List<Offer> fetchByEstateId(long estateId) {
        return offerRepo.findAllByEstate(estateId);
    }

    public List<Deal> fetchByRealtorId(long realtor) {
         return dealRepo.findAllByRealtor(realtor);
    }

    public long createDeal(long offerId) {
        Offer selectedOffer = offerRepo.getById(offerId);
        long offerEstate = selectedOffer.getEstate();
        long offerRealtor = selectedOffer.getRealtor();
        Deal deal = new Deal(offerEstate, offerRealtor);
        Deal savedDeal = dealRepo.save(deal);
        return savedDeal.getId();
    }

    public void closeDeal(long dealId) {
        Deal dealToClose = dealRepo.findById(dealId).orElseThrow(IllegalArgumentException::new);
        if (dealToClose.getStatus() == DealStatusEnum.STRIKED) {
            dealToClose.setStatus(DealStatusEnum.COMPLETED);
        }
        dealRepo.save(dealToClose);
    }

    public void cancelDeal(long dealId) {
        Deal dealToCancel = dealRepo.findById(dealId).orElseThrow(IllegalArgumentException::new);
        if (dealToCancel.getStatus() == DealStatusEnum.STRIKED) {
            dealToCancel.setStatus(DealStatusEnum.REVOKED);
        }
        dealRepo.save(dealToCancel);
    }

    public long createOffer(long estateId, String realtor) {
        long realtorId = restTemplate.getForObject("http://172.29.44.47:30001/EstateMarketplace/user/getByEmail/email={email}", Long.class, realtor);
        Offer newOffer = new Offer(estateId, realtorId);
        offerRepo.save(newOffer);
        return newOffer.getId();
    }

    public void deleteOffer(long estateId, String realtorEmail) {
        long realtorId = restTemplate.getForObject("http://172.29.44.47:30001/EstateMarketplace/user/getByEmail/email={email}", Long.class, realtorEmail);
        offerRepo.deleteByEstateAndRealtor(estateId, realtorId);
    }
}
