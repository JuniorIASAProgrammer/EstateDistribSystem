package com.realestate.servicedeal.repo;

import com.realestate.servicedeal.repo.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepo extends JpaRepository<Deal, Long> {
    List<Deal> findAllByRealtor(long realtor);
}
