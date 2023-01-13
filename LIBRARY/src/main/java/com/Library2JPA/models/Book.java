package com.Library2JPA.models;

import javax.validation.constraints.NotEmpty;

public class Book {


    private int id;
    @NotEmpty(message = "Book has to have a name")
    private String bookName;
    private String author;


    private Integer yearOfPublication;
    private Integer person_id;

    public Book(int id, String bookName, String author, Integer yearOfPublication, Integer person_id) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.person_id = person_id;
    }

    public Book(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }


    public String forShow(){
        String toReturn = bookName;

        if (!author.isEmpty())
            toReturn=toReturn+", by "+author;
        if (yearOfPublication!=null)
            toReturn=toReturn+", "+yearOfPublication;
        return toReturn;
    }


}
