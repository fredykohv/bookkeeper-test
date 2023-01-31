package com.fredy.app.rest;

import com.fredy.app.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController
{
    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/")
    public String home_page()
    {
        return "Welcome";
    }

    @GetMapping("/books")
    public List<Book> get_books()
    {
        return bookRepository.findAll();
    }

    @PostMapping("/save")
    public String save_book(@RequestBody Book book)
    {
        int total_amount = 1;

        for (Book b : bookRepository.findAll())
        {
            if (b.getTitle().equals(book.getTitle()))
            {
                total_amount += 1;
            }
        }

        for (Book b : bookRepository.findAll())
        {
            if (b.getTitle().equals(book.getTitle()))
            {
                b.setAmount(total_amount);
            }
        }
        book.setAmount(total_amount);
        bookRepository.save(book);

        return "Book added!";
    }

    @GetMapping("/books/title/{title}")
    public List<Book> get_books_by_title(@PathVariable String title)
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

    @GetMapping("/books/author/{author}")
    public List<Book> get_books_by_author(@PathVariable String author)
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

    @GetMapping("/books/year/{year}")
    public List<Book> get_books_by_year(@PathVariable int year)
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

    @PutMapping("/books/update/{id}")
    public String update_book(@PathVariable Long id, @RequestBody Book book)
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

    @PutMapping("/books/loan/{title}")
    public String loan_book(@PathVariable String title)
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

        update_book(desired_books_by_title.get(0).getId(), desired_books_by_title.get(0));

        return "Book with the title of: " + title + " loaned for " + desired_books_by_title.get(0).getOn_loan_days() + " days!";
    }

    @PutMapping("/books/return/{title}")
    public String return_book(@PathVariable String title)
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

        update_book(returnable_books_by_title.get(0).getId(), returnable_books_by_title.get(0));

        return "Thank you for return the book: " + title;
    }

    @DeleteMapping("/delete/{id}")
    public String delete_book(@PathVariable Long id)
    {
        Book delete_book = bookRepository.findById(id).get();
        Integer after_delete_amount = delete_book.getAmount() - 1;

        for (Book b : bookRepository.findAll())
        {
            if (b.getTitle().equals(delete_book.getTitle()))
            {
                b.setAmount(after_delete_amount);
            }
        }
        bookRepository.delete(delete_book);

        return "Book with the id: " + id + " deleted!";
    }
}
