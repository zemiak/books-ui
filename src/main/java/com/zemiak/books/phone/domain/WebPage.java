package com.zemiak.books.phone.domain;

import com.sun.jersey.api.client.WebResource;
import com.zemiak.books.phone.boundary.RestData;
import com.zemiak.books.phone.domain.dto.WebPageDTO;
import java.net.URL;
import java.util.Objects;

public class WebPage {
    private String name;
    private URL url;
    private int id;

    private String authorUrl;
    private Author author = null;

    private com.sun.jersey.api.client.WebResource webResource;
    private com.sun.jersey.api.client.Client client;
    public WebPage() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = com.sun.jersey.api.client.Client.create(config);
        webResource = client.resource(RestData.BASE_URI);
    }

    public WebPage(WebPageDTO dto) {
        this();

        this.name = dto.getName();
        this.id = dto.getId();
        this.url = dto.getUrl();
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

    public URL getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.url);
        hash = 79 * hash + this.id;
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
        final WebPage other = (WebPage) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WebPage{" + "name=" + name + ", url=" + url + ", id=" + id + '}';
    }

    public int compareTo(Object t) {
        WebPage other = (WebPage) t;

        return name.compareTo(other.getName());
    }
}