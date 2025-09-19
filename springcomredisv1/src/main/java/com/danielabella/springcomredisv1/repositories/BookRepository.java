package com.danielabella.springcomredisv1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielabella.springcomredisv1.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}