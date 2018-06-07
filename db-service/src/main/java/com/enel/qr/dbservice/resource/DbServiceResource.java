package com.enel.qr.dbservice.resource;
import com.enel.qr.dbservice.repository.CustomRepository;
import com.ennel.qr.model.CAT_MCREDENCIAL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

	@Autowired
	private CustomRepository customRepository;
   
    @GetMapping("/test")
    public String listar() {
        System.out.println("Display all customers...");
        List<CAT_MCREDENCIAL> list = customRepository.listaAll();
        list.forEach(x -> System.out.println("ID" +x.mcred_id + "FECHA" + x.mcred_id + " ESTADO " + x.mcred_estadocred));

        return "";
    }



}
