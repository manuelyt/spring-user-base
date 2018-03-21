package com.ameniti.service;

import java.util.List;

import com.ameniti.model.HUserMeta;

public interface HUserMetaServ {
    List<HUserMeta> findAll();

    List<HUserMeta> findByIdhusers(long idsysusers);

    void saveHuserMeta(HUserMeta hUserMeta);
}
