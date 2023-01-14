package com.Library2JPA.services;

import com.Library2JPA.models.Book;
import com.Library2JPA.models.Person;
import com.Library2JPA.repo.BooksRepository;
import com.Library2JPA.repo.PeopleRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, Boolean sortByYear){
        if (page==null&&booksPerPage==null) {
            if (sortByYear==null)
                return booksRepository.findAll();
            return booksRepository.findAll(Sort.by("yearOfPublication"));
        }
        if (page!=null&&booksPerPage!=null){
            if (sortByYear==null)
                return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfPublication"))).getContent();
        }
        return booksRepository.findAll();
    }

    public Book findOne(int id){
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book){
        book.setId(id);
        book.setOwner(booksRepository.findById(id).get().getOwner());
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public Person getBookOwner(int id){
        Optional<Book> book = booksRepository.findById(id);

        return book.get().getOwner();
    }

    @Transactional
    public void freeTheBook(int id){
        Optional<Book> book = booksRepository.findById(id);
        book.get().setOwner(null);
        booksRepository.save(book.get());
    }

    @Transactional
    public void newOwner(int id, Person person){
        Optional<Book> book = booksRepository.findById(id);
        book.get().setOwner(person);
        booksRepository.save(book.get());
    }


}
