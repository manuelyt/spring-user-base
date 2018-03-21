package com.ameniti.rest;

import com.ameniti.lib.common.*;
import com.ameniti.model.*;
import com.ameniti.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by manu on 2018-3-8.
 */

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private enum ValidVersion
    {
        v1, v2
    }

    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Autowired
    private HUserService hUserService;


    /*
     *  We are not using userService.findByUsername here(we could),
     *  so it is good that we are making sure that the user has role "ROLE_USER"
     *  to access this endpoint.
     */
    @RequestMapping("/{version}/whoami")
    @PreAuthorize("hasRole('USER')")
    public HUser user(Principal user) {
        return this.hUserService.findByUsername(user.getName());
    }
}