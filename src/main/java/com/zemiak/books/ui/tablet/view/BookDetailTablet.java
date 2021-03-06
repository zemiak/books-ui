package com.zemiak.books.ui.tablet.view;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Link;
import com.zemiak.books.domain.Book;
import java.io.File;
import javax.enterprise.context.Dependent;

@Dependent
public class BookDetailTablet extends ViewAbstractTablet {
    static class BookFileResource extends FileResource {
        private String mimeType;
        
        public BookFileResource(File sourceFile, String mimeType) {
            super(sourceFile);
            
            this.mimeType = mimeType;
        }
        
        @Override
        public String getMIMEType() {
            return mimeType;
        }
    }
    
    CssLayout content = null;
    Book book;
    
    public BookDetailTablet() {
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    @Override
    protected void onBecomingVisible() {
        super.onBecomingVisible();
        setCaption(book.getName());
        
        refresh();
    }
    
    private void refresh() {
        content = new CssLayout();
        setContent(content);

        VerticalComponentGroup group1 = new VerticalComponentGroup();

        if (book.getMobiFileName() != null) {
            Link button = new Link();
            button.setCaption("Kindle Format");
            group1.addComponent(button);
            
            FileDownloader fileDownloader = new FileDownloader(new BookFileResource(new File(book.getMobiFileName()), "application/x-mobipocket-ebook"));
            fileDownloader.extend(button);
        }

        if (book.getEpubFileName() != null) {
            Link button = new Link();
            button.setCaption("iBooks Format");
            group1.addComponent(button);

            FileDownloader fileDownloader = new FileDownloader(new BookFileResource(new File(book.getEpubFileName()), "application/epub+zip"));
            fileDownloader.extend(button);
        }
        
        if (book.getPdfFileName() != null) {
            Link button = new Link();
            button.setCaption("PDF Format");
            group1.addComponent(button);

            FileDownloader fileDownloader = new FileDownloader(new BookFileResource(new File(book.getPdfFileName()), "application/pdf"));
            fileDownloader.extend(button);
        }

        content.addComponent(group1);
    }
}
