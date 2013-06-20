package com.zemiak.books.phone.vaadin.view;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Link;
import com.zemiak.books.phone.boundary.Collection;
import com.zemiak.books.phone.domain.Author;
import com.zemiak.books.phone.domain.Book;
import com.zemiak.books.phone.domain.Tag;
import com.zemiak.books.phone.domain.WebPage;
import com.zemiak.books.phone.vaadin.NavManager;
import com.zemiak.books.phone.vaadin.NavToolbar;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author vasko
 */
class AuthorPage extends NavigationView implements Component {
    CssLayout content = null;
    List<String> tags;
    List<Book> books;
    NavManager manager;
    Collection col;
    Author author;

    public AuthorPage(Author author, NavManager manager) {
        super(author.getName());

        col = manager.getCollection();
        tags = author.getTags();
        books = author.getBooks();
        this.manager = manager;

        this.author = author;

        this.setToolbar(new NavToolbar(manager));
        refresh();
    }

    @Override
    protected void onBecomingVisible() {
        super.onBecomingVisible();
    }

    private void refresh() {
        content = new CssLayout();
        setContent(content);

        if (author.getWebPages() != null && !author.getWebPages().isEmpty()) {
            VerticalComponentGroup group2 = new VerticalComponentGroup("Links");

            for (WebPage page: author.getWebPages()) {
                Link link = new Link(page.getName(), new ExternalResource(page.getUrl().toString()));
                group2.addComponent(link);
            }

            content.addComponents(group2);
        }

        VerticalComponentGroup group1 = new VerticalComponentGroup("Books");

        for (Book book: books) {
            NavigationButton button = new NavigationButton(book.getName());
            group1.addComponent(button);

            final Book finalBook = book;

            button.addClickListener(new NavigationButton.NavigationButtonClickListener() {
                @Override
                public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {
                    getNavigationManager().navigateTo(new BookPage(finalBook, manager));
                }
            });
        }

        content.addComponents(group1);

        if (null != tags && !tags.isEmpty()) {
            Collections.sort(tags);
            VerticalComponentGroup group = new VerticalComponentGroup("Tags");

            for (String tag: tags) {
                NavigationButton button = new NavigationButton(tag);
                group.addComponent(button);

                final String finalTag = tag;

                button.addClickListener(new NavigationButton.NavigationButtonClickListener() {
                    @Override
                    public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {
                        getNavigationManager().navigateTo(new TagPage(finalTag, manager));
                    }
                });
            }

            content.addComponents(group);
        }
    }
}