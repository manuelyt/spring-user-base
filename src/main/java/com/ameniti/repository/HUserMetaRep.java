package com.ameniti.repository;

import com.ameniti.model.HUserMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by manu on 2018-3-8.
 */
public interface HUserMetaRep extends JpaRepository<HUserMeta, Long> {
    List<HUserMeta> findByIdhusers(long idhusers);
}