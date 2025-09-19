package com.danielabella.springcomredisv1.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.danielabella.springcomredisv1.entities.Book;
import com.danielabella.springcomredisv1.repositories.BookRepository;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public Book update(Long id, Book bookData) {
        Book book = findById(id);
        book.setTitle(bookData.getTitle());
        book.setAuthor(bookData.getAuthor());
        return repository.save(book);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}