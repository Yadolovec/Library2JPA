package com.Library2JPA.controller;

import com.Library2JPA.dao.PersonDAO;
import com.Library2JPA.models.Book;
import com.Library2JPA.models.Person;
import com.Library2JPA.services.BooksService;
import com.Library2JPA.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/lib/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final PersonDAO personDAO;
    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, PersonDAO personDAO) {

        this.booksService = booksService;
        this.peopleService = peopleService;
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String allBooks(Model model){
        model.addAttribute("books", booksService.findAll());
        return "library/books/index";
    }

    @GetMapping("/new")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        return "library/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return "library/books/new";

        booksService.save(book);
        return "redirect:/lib/books";
    }

    @GetMapping("/{id}")
    public String editBook(Model model, @PathVariable("id") int id){

        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.index());
        model.addAttribute("newOwner", new Person());
        Person owner = booksService.getBookOwner(id);

        model.addAttribute("person", owner);



        return "library/books/show";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/lib/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));

        return "/library/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(Model model, @Valid @ModelAttribute("book") Book book, BindingResult bindingResult, @PathVariable("id") int id){

        Book b1 = (Book)model.getAttribute("book");
        System.out.println("In update id = "+b1.getId());
        System.out.println("In update name = "+b1.getBookName());
        if (bindingResult.hasErrors()) {
            System.out.println("In update  IF id = "+b1.getId());
            return "/library/books/edit";
        }

        booksService.update(id, book);
        return "redirect:/lib/books";
    }

    @PatchMapping("/{id}/free")
    public String free(@PathVariable("id") int id){
        booksService.freeTheBook(id);
        return "redirect:/lib/books/"+id;
    }

    @PatchMapping("/{id}/newOwner")
    public String newOwner(@ModelAttribute("newOwner") Person person, @PathVariable("id")  int id){
        booksService.newOwner(id, person);
        return "redirect:/lib/books/"+id;
    }
}
