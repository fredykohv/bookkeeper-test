package com.fredy.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredy.app.rest.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerTests
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookManager bookManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void create_save_book()
    {
        Book book = new Book();
        book.setTitle("Default");
        book.setAuthor("Default author");
        book.setRelease_year(1234);

        Book saved_book = bookRepository.save(book);

        assertNotNull(saved_book);
    }

    @Test
    public void find_book_by_title()
    {
        String title = "Default";
        List<Book> books = bookRepository.findByTitle(title);

        assertThat(books.get(0).getTitle().equals(title));
    }

    @Test
    public void find_book_by_author()
    {
        String author = "Default author";
        List<Book> books = bookRepository.findByAuthor(author);

        assertThat(books.get(0).getAuthor().equals(author));
    }

    @Test
    public void find_book_by_release()
    {
        int release_year = 1234;
        List<Book> books = bookRepository.findByReleaseyear(release_year);

        assertThat(books.get(0).getRelease_year() == release_year);
    }

    @Test
    public void change_book_title()
    {
        String current_title = "Default";
        String new_title = "New";

        List<Book> books = bookRepository.findByTitle(current_title);
        books.get(0).setTitle(new_title);
        bookRepository.save(books.get(0));

        List<Book> new_books = bookRepository.findByTitle(new_title);

        assertThat(new_books.size() == 1);
        assertThat(new_books.get(0).getAmount() == 1);
    }

    @Test
    public void loan_book()
    {
        bookManager.loan_book("New", bookRepository);
        List<Book> books = bookRepository.findByTitle("New");

        assertThat(books.get(0).getOn_loan_days() == 7);
        assertThat(books.get(0).isAvailability() == false);
    }

    @Test
    public void return_book()
    {
        bookManager.return_book("New", bookRepository);
        List<Book> books = bookRepository.findByTitle("New");

        assertThat(books.get(0).getOn_loan_days() == 0);
        assertThat(books.get(0).isAvailability() == true);
    }
}
