package com.bookrecs.bookrecs;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("MainView")
public class MainView extends VerticalLayout{

    public MainView(){
        MenuBar barmenu = new MenuBar();
        MenuItem browse = barmenu.addItem("Browse");
        MenuItem library = barmenu.addItem("Library");
        MenuItem recommendations = barmenu.addItem("Recommendations");
        MenuItem random = barmenu.addItem("Random");

        Div div1 = new Div();
        div1.add(barmenu);

        add(div1);
    }


}
