package com.zemiak.books.ui.tablet.view;

import com.vaadin.addon.touchkit.extensions.Html5InputSettings;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.zemiak.books.client.boundary.CachedCollection;
import javax.inject.Inject;

@CDIView("searchTablet")
public class Search extends ViewAbstract {
    Layout form = null;
    final TextField searchField = new TextField();
    boolean initialized = false;
    
    @Inject
    CachedCollection col;
    
    public Search() {
    }
    
    @Override
    protected void onBecomingVisible() {
        super.onBecomingVisible();
        setCaption("Search");
        
        if (initialized) {
            searchField.focus();
            return;
        }
        
        refresh();
        initialized = true;
    }

    private void refresh() {
        form = new CssLayout();
        setContent(form);
        
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<>(""));
        
        VerticalComponentGroup group = new VerticalComponentGroup("Search");
        
        final Html5InputSettings html5InputSettings = new Html5InputSettings(
                searchField);
        html5InputSettings.setProperty("type", "search");
        html5InputSettings.setProperty("autocomplete", "off");
        html5InputSettings.setProperty("autocorrect", "off");
        html5InputSettings.setProperty("autocapitalize", "off");
        html5InputSettings.setProperty("placeholder", "Search...");
        
        
        searchField.setImmediate(true);
        searchField.focus();
        group.addComponent(searchField);

        Button button = new Button();
        button.setCaption("Search");
        button.setVisible(false);
        button.setClickShortcut(KeyCode.ENTER);
        group.addComponent(button);
        
        NavigationButton navButton = new NavigationButton();
        navButton.setCaption("Search");
        navButton.addClickListener(new NavigationButton.NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {
                SearchResults view = new SearchResults(searchField.getValue(), col);
                getNavManager().navigateTo(view);
            }
        });
        group.addComponent(navButton);
        
        FieldGroup binder = new FieldGroup(item);
        binder.bind(searchField, "name");
        
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                SearchResults view = new SearchResults(searchField.getValue(), col);
                getNavManager().navigateTo(view);
            }
        });

        form.addComponent(group);
    }
}
