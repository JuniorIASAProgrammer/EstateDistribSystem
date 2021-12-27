package com.realestate.servicedeal.api;

import com.realestate.servicedeal.repo.model.Deal;
import com.realestate.servicedeal.repo.model.Offer;
import com.realestate.servicedeal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("EstateMarketplace/deal")
public class DealController {
    private final DealService dealService;

    //    Get all offers by estate - for client
    @GetMapping("/showOffers/estateId={estateId}")
    public ResponseEntity<List<Offer>> getByEstateId(@PathVariable long estateId) {
        final List<Offer> offerList = dealService.fetchByEstateId(estateId);
        return ResponseEntity.ok(offerList);
    }

//        Get all deals - for realtor
    @GetMapping("/showDeals/realtorId={id}")
    public ResponseEntity<List<Deal>> getByRealtorId(@PathVariable long id) {
        final List<Deal> dealList = dealService.fetchByRealtorId(id);
        return ResponseEntity.ok(dealList);
    }

    //    Make a deal - for client
    @PostMapping("/createDeal/offerId={offerId}")
    public ResponseEntity<Void> createDeal(@PathVariable long offerId) {
        long id = dealService.createDeal(offerId);
        String location = String.format("/dealList/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    //    Make an offer - for realtor
    @PostMapping("/createOffer/realtorId={reatorId}/estateId={estateId}")
    public ResponseEntity<Void> createOffer(@PathVariable long estateId, @PathVariable long realtorId) {
        long id = dealService.createOffer(estateId, realtorId);
        String location = String.format("/offerList/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

//    Cancel offer - for realtor
    @DeleteMapping("/deleteOffer/realtorId={realtorId}/estateId={estateId}")
    public ResponseEntity<String> deleteOffer(@RequestBody long realtorId, @RequestBody long estateId) {
        dealService.deleteOffer(estateId, realtorId);
        return new ResponseEntity<>("Offer cancelled", HttpStatus.OK);
    }

//    Cancel the deal\
    @PatchMapping("/cancelDeal/dealId={dealId}")
    public ResponseEntity<String> cancelDeal(@PathVariable long dealId) {
        dealService.cancelDeal(dealId);
        return new ResponseEntity<>("Deal cancelled", HttpStatus.OK);
    }

//    Close the deal - for realtor
    @PatchMapping("/closeDeal/dealId={dealId}")
    public ResponseEntity<String> closeDeal(@PathVariable long dealId) {
        dealService.closeDeal(dealId);
        return new ResponseEntity<>("Deal completed", HttpStatus.OK);
    }
}
