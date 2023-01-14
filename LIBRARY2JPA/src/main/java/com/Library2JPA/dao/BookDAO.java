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

}
