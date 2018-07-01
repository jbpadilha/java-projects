package com.joaopadilha.plasticmobile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joaopadilha.plasticmobile.entity.Craiglist;
import com.joaopadilha.plasticmobile.repository.CraiglistRepository;

@RestController
@RequestMapping(value = "/plasticmobile")
public class CraiglistController {
	
	private CraiglistRepository craiglistRepository = new CraiglistRepository();
	
    @RequestMapping(value = "/{search}", method = RequestMethod.GET)
    public List<Craiglist> listResults(@PathVariable(value="search") String search) {
          List<Craiglist> listCraiglist = craiglistRepository.findBySearch(search);     
          return listCraiglist;
    }
}
