package com.zemiak.books.boundary;

import com.zemiak.books.domain.Author;
import com.zemiak.books.domain.Book;
import com.zemiak.books.domain.Letter;
import com.zemiak.books.domain.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Collection {
    @PersistenceContext
    private EntityManager em;

    public List<Author> getAuthors() {
        return em.createNamedQuery("Author.findAll").getResultList();
    }

    public Author getAuthor(int id) {
        return em.find(Author.class, id);
    }

    public List<Book> getBooks() {
        return em.createNamedQuery("Book.findAll").getResultList();
    }

    public Book getBook(int id) {
        return em.find(Book.class, id);
    }

    public List<Letter> getLetters() {
        return em.createNamedQuery("Letter.findAll").getResultList();
    }

    public Letter getLetter(String letter) {
        return em.find(Letter.class, letter);
    }

    public List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        for (Object e: em.createNamedQuery("Tag.findAll").getResultList()) {
            Tag tag = (Tag) e;

            if (! tags.contains(tag.getName())) {
                tags.add(tag);
            }
        }

        return tags;
    }

    public List<Author> getAuthorsByTag(String tagName) {
        List<Author> authors = new ArrayList<>();

        for (Author author: getAuthors()) {
            for(Tag tag: author.getTags()) {
                if (tagName.equals(tag.getName())) {
                    authors.add(author);
                    break;
                }
            }
        }

        return authors;
    }

    public List<Tag> getDistinctTags() {
        return em.createNamedQuery("Tag.findDistinct").getResultList();
    }

    public Tag getTag(int id) {
        return em.find(Tag.class, id);
    }

    public void persist(Object entity) {
        em.persist(entity);
    }
}