package com.Library2JPA.models;

import javax.validation.constraints.Pattern;

public class Person {
    private int person_id;
    @Pattern(regexp = "[A-Z]\\w+", message = "Should start with big letter")
    private String name;
    private int age;


    public Person(int user_id, String name, int age) {
        this.person_id = person_id;
        this.name = name;
        this.age = age;
    }

    public Person(){
    };
    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



}
