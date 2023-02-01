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

    @Autowired
    private BookManager bookManager;


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
        return bookManager.get_books_by_title(title, bookRepository);
    }

    @GetMapping("/books/author/{author}")
    public List<Book> get_books_by_author(@PathVariable String author)
    {
        return bookManager.get_books_by_author(author, bookRepository);
    }

    @GetMapping("/books/year/{year}")
    public List<Book> get_books_by_year(@PathVariable int year)
    {
        return bookManager.get_books_by_release(year, bookRepository);
    }

    @PutMapping("/books/update/{id}")
    public String update_book(@PathVariable Long id, @RequestBody Book book)
    {
        return bookManager.update_book(id, book, bookRepository);
    }

    @PutMapping("/books/loan/{title}")
    public String loan_book(@PathVariable String title)
    {
        return bookManager.loan_book(title, bookRepository);
    }

    @PutMapping("/books/return/{title}")
    public String return_book(@PathVariable String title)
    {
        return bookManager.return_book(title, bookRepository);
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
