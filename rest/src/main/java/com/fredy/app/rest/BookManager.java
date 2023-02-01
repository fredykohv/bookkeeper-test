package com.fredy.app.rest;

import com.fredy.app.rest.repository.BookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookManager
{
    public List<Book> get_books_by_title(String title, BookRepository bookRepository)
    {
        List<Book> books = bookRepository.findAll();
        List<Book> books_with_desired_title = new ArrayList<>();

        for (Book b : books)
        {
            if (b.getTitle().equals(title))
            {
                books_with_desired_title.add(b);
            }
        }

        return books_with_desired_title;
    }

    public List<Book> get_books_by_author(String author, BookRepository bookRepository)
    {
        List<Book> books = bookRepository.findAll();
        List<Book> books_with_desired_author = new ArrayList<>();

        for (Book b : books)
        {
            if (b.getAuthor().equals(author))
            {
                books_with_desired_author.add(b);
            }
        }

        return books_with_desired_author;
    }

    public List<Book> get_books_by_release(int year, BookRepository bookRepository)
    {
        List<Book> books = bookRepository.findAll();
        List<Book> books_with_desired_year = new ArrayList<>();

        for (Book b : books)
        {
            if (b.getRelease_year() == year)
            {
                books_with_desired_year.add(b);
            }
        }

        return books_with_desired_year;
    }

    public String update_book(Long id, Book book, BookRepository bookRepository)
    {
        Book updated_book = bookRepository.findById(id).get();
        updated_book.setTitle(book.getTitle());
        updated_book.setAuthor(book.getAuthor());
        updated_book.setRelease_year(book.getRelease_year());
        updated_book.setAvailability(book.isAvailability());
        updated_book.setAmount(book.getAmount());
        updated_book.setOn_loan_days(book.getOn_loan_days());
        bookRepository.save(updated_book);

        return "Book updated!";
    }

    public String loan_book(String title, BookRepository bookRepository)
    {
        List<Book> books = bookRepository.findAll();
        List<Book> desired_books_by_title = new ArrayList<>();

        for (Book b : books)
        {
            if (b.getTitle().equals(title) && b.isAvailability())
            {
                desired_books_by_title.add(b);
            }
        }

        if (desired_books_by_title.isEmpty())
        {
            return "No available books with the title of: " + title;
        }

        if (desired_books_by_title.size() >= 5)
        {
            desired_books_by_title.get(0).setAvailability(false);
            desired_books_by_title.get(0).setOn_loan_days(28);
        }
        else
        {
            desired_books_by_title.get(0).setAvailability(false);
            desired_books_by_title.get(0).setOn_loan_days(7);
        }

        update_book(desired_books_by_title.get(0).getId(), desired_books_by_title.get(0), bookRepository);

        return "Book with the title of: " + title + " loaned for " + desired_books_by_title.get(0).getOn_loan_days() + " days!";
    }

    public String return_book(String title, BookRepository bookRepository)
    {
        List<Book> books = bookRepository.findAll();
        List<Book> returnable_books_by_title = new ArrayList<>();

        for (Book b : books)
        {
            if (b.getTitle().equals(title) && !b.isAvailability())
            {
                returnable_books_by_title.add(b);
            }
        }

        if (returnable_books_by_title.isEmpty())
        {
            return "Not able to return this book ('" + title + "') because we either don't have it or haven't loaned it out!";
        }

        returnable_books_by_title.get(0).setAvailability(true);
        returnable_books_by_title.get(0).setOn_loan_days(0);

        update_book(returnable_books_by_title.get(0).getId(), returnable_books_by_title.get(0), bookRepository);

        return "Thank you for returning the book: " + title;
    }
}
