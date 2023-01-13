package com.Library2JPA.controller;

import com.Library2JPA.dao.BookDAO;
import com.Library2JPA.dao.PersonDAO;
import com.Library2JPA.models.Book;
import com.Library2JPA.models.Person;
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

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {

        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String allBooks(Model model){
        model.addAttribute("books", bookDAO.index());
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

        bookDAO.save(book);
        return "redirect:/lib/books";
    }

    @GetMapping("/{id}")
    public String editBook(Model model, @PathVariable("id") int id){

        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.index());
        model.addAttribute("newOwner", new Person());
        Optional<Person> owner = bookDAO.getBookOwner(id);
        if (owner.isPresent())
            model.addAttribute("person", owner.get());

        return "library/books/show";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/lib/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));

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

        bookDAO.update(id, book);
        return "redirect:/lib/books";
    }

    @PatchMapping("/{id}/free")
    public String free(@PathVariable("id") int id){
        bookDAO.freeTheBook(id);
        return "redirect:/lib/books/"+id;
    }

    @PatchMapping("/{id}/newOwner")
    public String newOwner(@ModelAttribute("newOwner") Person person, @PathVariable("id")  int id){
        bookDAO.newOwner(id, person.getPerson_id());
        return "redirect:/lib/books/"+id;
    }
}
