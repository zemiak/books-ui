package com.zemiak.books.boundary;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zemiak.books.domain.Book;
import java.util.List;

public class Books {
    private com.sun.jersey.api.client.WebResource webResource;
    private com.sun.jersey.api.client.Client client;

    public Books() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = com.sun.jersey.api.client.Client.create(config);
        webResource = client.resource(RestData.BASE_URI).path("books");
    }

    public List<Book> findByAuthor(String author) throws com.sun.jersey.api.client.UniformInterfaceException {
        WebResource resource = webResource;
        
        resource = resource.path(java.text.MessageFormat.format("author/{0}", new Object[]{author}));
        return resource.get(new GenericType<List<Book>>(){});
    }

    public List<Book> findByExpression(String expr) throws com.sun.jersey.api.client.UniformInterfaceException {
        WebResource resource = webResource;
        
        resource = resource.path(java.text.MessageFormat.format("search/{0}", new Object[]{expr}));
        return resource.get(new GenericType<List<Book>>(){});
    }

    public Book find(String id) throws com.sun.jersey.api.client.UniformInterfaceException {
        WebResource resource = webResource;
        
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.get(Book.class);
    }

    public List<Book> all() throws com.sun.jersey.api.client.UniformInterfaceException {
        WebResource resource = webResource;
        
        return resource.get(new GenericType<List<Book>>(){});
    }

    public void close() {
        client.destroy();
    }
    
}