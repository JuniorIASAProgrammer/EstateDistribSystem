package com.realestate.serviceestate.repo;

import com.realestate.serviceestate.repo.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateRepo extends JpaRepository<Estate, Long> {
}
