package com.realestate.servicedeal.repo;

import com.realestate.servicedeal.repo.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepo extends JpaRepository<Offer, Long> {
    List<Offer> findAllByEstate(long estateId);

    void deleteByEstateAndRealtor(long estateId, long realtorId);
}
