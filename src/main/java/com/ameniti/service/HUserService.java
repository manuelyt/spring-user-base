package  com.ameniti.service;

import java.util.List;
import  com.ameniti.model.HUser;

public interface HUserService {
    List<HUser> findAll();
    void saveHUser (HUser hUser);
    HUser findById(Long id);
    HUser findByUsername(String username);
}
