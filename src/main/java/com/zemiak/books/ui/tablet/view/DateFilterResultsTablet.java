package com.zemiak.books.ui.tablet.view;

import com.vaadin.addon.responsive.Responsive;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.ui.CssLayout;
import com.zemiak.books.boundary.Collection;
import com.zemiak.books.domain.Book;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DateFilterResultsTablet extends ViewAbstractTablet {

    CssLayout grid = null;
    
    @Inject
    Collection col;
    
    Date from, to;
    private List<Book> books;
    
    @Inject
    BookDetailTablet bookView;

    public DateFilterResultsTablet() {
    }
    
    public void setDateInterval(Date from, Date to) {
        this.from = from;
        this.to = to;

        refreshData();
    }

    private void refreshData() {
        books = col.getBooksByDateInterval(from, to);
    }

    @Override
    protected void onBecomingVisible() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        super.onBecomingVisible();
        setCaption("Dates " + df.format(from) + " - " + df.format(to));

        refresh();
    }

    private void refresh() {
        grid = new CssLayout();
        grid.setWidth("100%");
        grid.addStyleName("grid");
        setContent(grid);

        new Responsive(grid);

        for (Book book : books) {
            NavigationButton button = new NavigationButton();
            button.setSizeUndefined();
            button.setDescription(book.getName());
            grid.addComponent(button);

            final Book finalBook = book;

            button.addClickListener(new NavigationButton.NavigationButtonClickListener() {
                @Override
                public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {
                    bookView.setBook(finalBook);
                    getNavManager().navigateTo(bookView);
                }
            });
        }
    }
}
