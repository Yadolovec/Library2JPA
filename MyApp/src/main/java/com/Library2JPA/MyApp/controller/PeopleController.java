package com.Library2JPA.MyApp.controller;

//import com.Library2JPA.dao.BookDAO;
//import com.Library2JPA.dao.PersonDAO;

import com.Library2JPA.MyApp.models.Person;
import com.Library2JPA.MyApp.services.PeopleService;
import com.Library2JPA.MyApp.utils.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/lib/people")
public class PeopleController {



    private final PeopleService peopleService;

    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {

        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping("/main")
    public String main(){

        return "library/main";
    }
    @GetMapping()
    public String allPeople(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "library/people/index";
    }

    @GetMapping("/new")
    public String addUser(Model model){
        model.addAttribute("person", new Person());
        return "library/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){

        personValidator.validate(person, bindingResult);


        if (bindingResult.hasErrors())
            return "library/people/new";
        peopleService.save(person);
        return "redirect:/lib/people";
    }

    @GetMapping("/{id}")
    public String editPerson(Model model, @PathVariable("id") int person_id){
        model.addAttribute("person", peopleService.findOne(person_id));
        model.addAttribute("books", peopleService.getBooksOf(person_id));
        return "library/people/show";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/lib/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "library/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person personToUpdate, BindingResult bindingResult){

        personValidator.validate(personToUpdate, bindingResult);

        if (bindingResult.hasErrors())
            return "library/people/edit";
        peopleService.update(id, personToUpdate);
        return "redirect:/lib/people";
    }
}
