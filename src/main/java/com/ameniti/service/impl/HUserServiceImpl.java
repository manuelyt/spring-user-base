package com.ameniti.service.impl;

import com.ameniti.model.HUser;
import com.ameniti.model.HUserMeta;
import com.ameniti.repository.HUserMetaRep;
import com.ameniti.repository.HUserRepository;
import com.ameniti.service.HUserService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class HUserServiceImpl implements HUserService {

    @Autowired
    private HUserMetaRep hUserMetaRep;

    @Autowired
    private HUserRepository hUserRepository;

    private static List<HUser> hUser;

    public List<HUser> findAll() throws AccessDeniedException {
        List<HUser> result = hUserRepository.findAll();
        List<HUser> result2 = hUserRepository.findAll();
        result2.clear();
        Iterator it = result.iterator();
        while (it.hasNext()) {
            HUser huser = (HUser) it.next();
            long id = huser.getId();
            List<HUserMeta> metasForhuser = hUserMetaRep.findByIdhusers(id);
            if (metasForhuser.size() > 0) {
                HashMap<String, String> meta = new HashMap<String, String>();
                Iterator ite = metasForhuser.listIterator();
                while (ite.hasNext()) {
                    HUserMeta metasResult = (HUserMeta) ite.next();
                    meta.put(metasResult.getKey(), metasResult.getValue());
                }
                huser.setMeta(meta);
            }
            result2.add(huser);
        }
        return result2;
    }

    public void saveHUser(HUser hUser) {
        hUserRepository.save(hUser);
        Iterator it = hUser.getMeta().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            HUserMeta husersMeta = new HUserMeta();
            husersMeta.setIdhusers(hUser.getId());
            husersMeta.setKey(StringEscapeUtils.escapeHtml(pair.getKey().toString()));
            husersMeta.setValue(StringEscapeUtils.escapeHtml(pair.getValue().toString()));
            hUserMetaRep.save(husersMeta);
        }
    }



    @Override
    public HUser findByUsername(String username) throws UsernameNotFoundException {
        HUser u = hUserRepository.findByUsername(username);
        return u;
    }

    public HUser findById(Long id) throws AccessDeniedException {
        HUser u = hUserRepository.findOne(id);
        return u;
    }

}
