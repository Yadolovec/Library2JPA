package com.Library2JPA.utils;

import com.Library2JPA.dao.PersonDAO;
import com.Library2JPA.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

            if (personDAO.show(person.getName())!=null)
                if(personDAO.show(person.getName()).getPerson_id()!=person.getPerson_id()){
                    errors.rejectValue("name", "", "This name is already taken");
                }



    }
}
