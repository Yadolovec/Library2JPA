package com.Library2JPA.services;

import com.Library2JPA.models.Book;
import com.Library2JPA.models.Person;
import com.Library2JPA.repo.PeopleRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final EntityManager entityManager;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
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
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select p from Book p where owner.id=:id", Book.class)
                .setParameter("id",personId).getResultList();
    }

}
