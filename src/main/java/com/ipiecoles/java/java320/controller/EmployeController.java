package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.SortedMap;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;


    @RequestMapping(method = RequestMethod.GET, value ="/{id}")
    public String getEmployeById(@PathVariable Long id, final ModelMap model){

        Optional<Employe> employeOptionnal = employeRepository.findById(id);
        if(employeOptionnal.isEmpty()){
            //Erreur 404
            throw new EntityNotFoundException("L'employé d'identifiant : " + id + " n'a pas été trouvé.");
        }

        model.put("employe",employeOptionnal.get());
        return "detail";
    }



    @RequestMapping(value = "", method = RequestMethod.GET, params = "matricule")
    public String findByMatricule(@RequestParam String matricule, final ModelMap model){
        Employe employe = employeRepository.findByMatricule(matricule);
        //404
//        if(employe == null){
//            throw new EntityNotFoundException("L'Employé de matricule : " + matricule + " n'a pas été trouvé.");
//        }
        model.put("employe",employe);
        return "detail";
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String listEmployes(final ModelMap model,
                               @RequestParam(value="page", defaultValue = "0") Integer page,
                               @RequestParam(value="size", defaultValue = "10") Integer size,
                               @RequestParam(value="sortDirection", defaultValue = "ASC") String sortDirection,
                                @RequestParam(defaultValue = "matricule") String sortProperty)
    {
        PageRequest pageRequest =  PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Employe> pageEmploye = employeRepository.findAll(pageRequest);

        model.put("employes", pageEmploye);
        model.put("pageNumber", page + 1);
        model.put("previousPage", page - 1);
        model.put("nextPage", page + 1);
        model.put("start", page * size + 1);
        model.put("end", page + 1 * size);
        return "listeEmployes";
    }

}
