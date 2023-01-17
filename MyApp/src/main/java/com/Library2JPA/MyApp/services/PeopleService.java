package com.Library2JPA.MyApp.services;



import com.Library2JPA.MyApp.models.Book;
import com.Library2JPA.MyApp.models.Person;
import com.Library2JPA.MyApp.repo.PeopleRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
//    private final EntityManager entityManager;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
//        this.entityManager = entityManager;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person){

        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }


    public List<Book> getBooksOf(int personId){
//        Session session = entityManager.unwrap(Session.class);
//        return session.createQuery("select p from Book p where owner.id=:id", Book.class)
//                .setParameter("id",personId).getResultList();
        Optional<Person> person = peopleRepository.findById(personId);

        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

}
