package com.Library2JPA.dao;

import com.Library2JPA.models.Book;
import com.Library2JPA.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //get all
    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM book",
                new BeanPropertyRowMapper<>(Book.class));
    }
    //get by id
    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    //save
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book (bookName, author, yearOfPublication, person_id) VALUES (?,?,?,?)",
              book.getBookName(), book.getAuthor(),
              book.getYearOfPublication(), book.getPerson_id());
    }
    //update
    public void update(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE book SET bookName=?, author=?, yearOfPublication=?, person_id=? WHERE id=?",
                updatedBook.getBookName(), updatedBook.getAuthor(),
                updatedBook.getYearOfPublication(), updatedBook.getPerson_id(), id);
    }
    //delete

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void freeTheBook(int id){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", null, id);
    }

    public void newOwner(int id, int person_id){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person_id, id);
    }
    //get all books of user
    public List<Book> booksOfPerson(int person_id){
        List<Book> listOfBook = jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{person_id},
                new BeanPropertyRowMapper<>(Book.class));
        if (listOfBook.isEmpty())
            return null;
        return listOfBook;
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.person_id WHERE book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
