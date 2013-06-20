package com.zemiak.books.phone.domain;

import com.sun.jersey.api.client.WebResource;
import com.zemiak.books.phone.boundary.RestData;
import com.zemiak.books.phone.domain.dto.BookDTO;
import java.util.Objects;

public class Book {
    private String mobiFileName;
    private String epubFileName;
    private String name;
    private int id;
    private boolean english;

    private String authorUrl;
    private Author author = null;

    private com.sun.jersey.api.client.WebResource webResource;
    private com.sun.jersey.api.client.Client client;

    public Book() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = com.sun.jersey.api.client.Client.create(config);
        webResource = client.resource(RestData.BASE_URI);
    }

    public Book(BookDTO dto) {
        this();

        this.id = dto.getId();
        this.name = dto.getName();
        this.english = dto.isEnglish();
        this.authorUrl = dto.getAuthorUrl();
        this.mobiFileName = dto.getMobiFileName();
        this.epubFileName = dto.getEpubFileName();
    }

    public Author getAuthor() {
        if (null == author) {
            WebResource resource = webResource;

            resource = resource.path(authorUrl);
            author = resource.get(Author.class);
        }

        return author;
    }

    public void close() {
        client.destroy();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getMobiFileName() {
        return mobiFileName;
    }

    public String getEpubFileName() {
        return epubFileName;
    }

    public boolean isEnglish() {
        return english;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Book{" + "name=" + name + ", id=" + id + ", english=" + english + '}';
    }

    public int compareTo(Object t) {
        Book other = (Book) t;

        return name.compareTo(other.getName());
    }
}