package com.nabuxir.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
//@PreAuthorize("admin")
public class IndexController {
    //    @PostMapping
//    @ResponseStatus(value = HttpStatus.OK)
//    public void postAuth(){
//        System.out.println("!!!");
//    }
//
    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String create() {
//        companyService.create(company);
        HttpHeaders headers = new HttpHeaders();
//        ControllerLinkBuilder linkBuilder = linkTo(methodOn(CompanyController.class).get(company.getId()));
//        headers.setLocation(company);
        return "/api/index";
    }

}
