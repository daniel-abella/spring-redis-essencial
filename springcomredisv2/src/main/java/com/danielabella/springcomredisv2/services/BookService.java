package com.danielabella.springcomredisv2.services;

import java.util.List;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import com.danielabella.springcomredisv2.entities.Book;
import com.danielabella.springcomredisv2.repositories.BookRepository;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    /*
     * Armazena o resultado da chamada do método no cache books.
     * Na primeira chamada, o método é executado e o resultado é salvo no Redis.
     * Nas chamadas seguintes, o valor é retornado direto do cache, sem acessar o banco de dados.
     * 
     * No Redis, a chave gerada é do tipo: books::SimpleKey[] (porque não tem parâmetros).
     */
    @Cacheable(value = "books")
    public List<Book> findAll() {
        return repository.findAll();
    }

    /*
     * Armazena no cache book o resultado de buscar um livro por id.
     * A key = "#id" diz ao Spring para usar o valor do parâmetro id como chave.
     * Desta maneira, gera-se chaves do tipo book::1, book::2, e sucessivamente.
     */
    @Cacheable(value = "book", key = "#id")
    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
    }

    /*
     * Remove todas as entradas do cache books.
     * Quando você cria, atualiza ou deleta um livro, os dados da lista total ficam desatualizados.
     * Por isso, é necessário limpar o cache da lista completa.
     */
    @CacheEvict(value = "books", allEntries = true)
    public Book create(Book book) {
        return repository.save(book);
    }

    /*
     * Atualiza o valor no cache, executando o método normalmente.
     * Ou seja, forçamos a atualização do cache após uma modificação de dados (como update).
     * Neste caso, obviamente, sempre será chamado, mas garante o cache estar atualziado.
     */
    @CachePut(value = "book", key = "#id")
    @CacheEvict(value = "books", allEntries = true)
    public Book update(Long id, Book bookData) {
        Book book = findById(id);
        book.setTitle(bookData.getTitle());
        book.setAuthor(bookData.getAuthor());
        return repository.save(book);
    }

    /*
     * Remove múltiplos caches ao mesmo tempo. Ou seja, limpamos:
     * a) cache book (com todos os livros individuais).
     * b) cache books (lista completa).
     */
    @CacheEvict(value = { "book", "books" }, allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

