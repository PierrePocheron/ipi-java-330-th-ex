package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.repository.EmployeRepository;
import com.ipiecoles.java.java320.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccueilController {

    @Autowired
    private EmployeRepository employeRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String accueil(final ModelMap m)
    {

        m.put("nbEmploye", employeRepository.count());
        return "accueil";
    }
}
