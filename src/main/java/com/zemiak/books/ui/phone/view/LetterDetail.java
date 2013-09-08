package com.zemiak.books.ui.phone.view;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CssLayout;
import com.zemiak.books.domain.Author;
import com.zemiak.books.domain.Letter;

class LetterDetail extends ViewAbstract {
    CssLayout content = null;
    
    Letter letter;

    public LetterDetail(Letter letter) {
        this.letter = letter;
    }
    
    @Override
    protected void onBecomingVisible() {
        super.onBecomingVisible();
        setCaption("Letter " + letter.getLetter());
        
        refresh();
    }

    private void refresh() {
        content = new CssLayout();
        setContent(content);

        VerticalComponentGroup group = new VerticalComponentGroup();

        for (Author author: letter.getAuthors()) {
            NavigationButton button = new NavigationButton(author.getName());
            group.addComponent(button);

            final Author finalAuthor = author;

            button.addClickListener(new NavigationButton.NavigationButtonClickListener() {
                @Override
                public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {
                    AuthorDetail view = new AuthorDetail(finalAuthor);
                    getNavManager().navigateTo(view);
                }
            });
        }

        content.addComponents(group);
    }
}
