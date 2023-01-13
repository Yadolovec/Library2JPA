package com.Library2JPA.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "bookname")
    @NotEmpty(message = "Book has to have a name")
    private String bookName;

    @Column(name = "author")
    private String author;


    @Column(name = "yearofpublication")
    private Integer yearOfPublication;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(String bookName, String author, Integer yearOfPublication) {

        this.bookName = bookName;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
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



    public String forShow(){
        String toReturn = bookName;

        if (!author.isEmpty())
            toReturn=toReturn+", by "+author;
        if (yearOfPublication!=null)
            toReturn=toReturn+", "+yearOfPublication;
        return toReturn;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
