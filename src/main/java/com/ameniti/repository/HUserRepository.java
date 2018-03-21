package com.ameniti.repository;

import com.ameniti.model.HUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by manu on 2018-3-8.
 */
public interface HUserRepository extends JpaRepository<HUser, Long> {
    HUser findByUsername(String username);
}

