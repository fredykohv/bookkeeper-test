package com.fredy.app.rest.repository;

import com.fredy.app.rest.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>
{
    public List<Book> findByTitle(String title);
}
