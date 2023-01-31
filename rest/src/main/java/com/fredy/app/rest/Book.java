package com.fredy.app.rest;

import jakarta.persistence.*;

@Entity
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column(name = "releaseyear", nullable = false)
    private int releaseyear;

    @Column
    private boolean availability = true;

    @Column
    private Integer on_loan_days = 0;

    @Column
    private Integer amount = 1;

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public Integer getOn_loan_days()
    {
        return on_loan_days;
    }

    public void setOn_loan_days(Integer on_loan_days)
    {
        this.on_loan_days = on_loan_days;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public int getRelease_year()
    {
        return releaseyear;
    }

    public void setRelease_year(int release_year)
    {
        this.releaseyear = release_year;
    }

    public boolean isAvailability()
    {
        return availability;
    }

    public void setAvailability(boolean availability)
    {
        this.availability = availability;
    }
}
