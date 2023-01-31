package com.fredy.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredy.app.rest.repository.BookRepository;
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
    private ObjectMapper objectMapper;

    @Test
    public void create_save_book()
    {
        Book book = new Book();
        book.setTitle("Default");
        book.setAuthor("Default");
        book.setRelease_year(1234);

        Book saved_book = bookRepository.save(book);

        assertNotNull(saved_book);
    }

    @Test
    public void find_book_by_title()
    {
        String title = "Default";
        List<Book> book = bookRepository.findByTitle(title);

        assertThat(book.get(0).getTitle().equals(title));
    }


}
