package  com.ameniti.service.impl;

import com.ameniti.service.HUserMetaServ;
import com.ameniti.model.HUserMeta;
import com.ameniti.repository.HUserMetaRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HUserMetaServImpl implements HUserMetaServ{

    @Autowired
    private HUserMetaRep hUserMetaRep ;

    private static List<HUserMeta> hUserMetas;

    public List<HUserMeta> findAll() throws AccessDeniedException {
        List<HUserMeta> result = hUserMetaRep.findAll();
        return result;
    }

    public void saveHuserMeta(HUserMeta hUserMetas) {
        hUserMetaRep.save(hUserMetas);
    }

    public List<HUserMeta> findByIdhusers( long idhusers ) throws AccessDeniedException {
        List<HUserMeta> hUserMetas = hUserMetaRep.findByIdhusers( idhusers );
        return hUserMetas;
    }
/*
*/
}