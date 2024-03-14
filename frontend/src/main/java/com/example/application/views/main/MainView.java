package com.example.application.views.main;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Main")
@Route("")
public class MainView extends AppLayout {

    public MainView() {
        DrawerToggle toggle = new DrawerToggle();
 
        H1 title = new H1("Boltech - Primeros auxilios");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav nav = getSideNav();

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        MessageList list = new MessageList();
        MessageInput input = new MessageInput();
        
        input.addSubmitListener(submitEvent -> {
            MessageListItem newMessage = new MessageListItem(
                    submitEvent.getValue(), Instant.now(), "Milla Sting");
            newMessage.setUserColorIndex(3);
            List<MessageListItem> items = new ArrayList<>(list.getItems());
            items.add(newMessage);
            list.setItems(items);
        });

        //Person person = DataService.getPeople(1).get(0);
        MessageListItem message1 = new MessageListItem();
        message1.setUserColorIndex(1);
        MessageListItem message2 = new MessageListItem();
        message2.setUserColorIndex(2);
        list.setItems(message1, message2);

        VerticalLayout chatLayout = new VerticalLayout(list, input);
        chatLayout.setHeight("90%");
        chatLayout.setWidthFull();
        chatLayout.expand(list);
        setContent(chatLayout);

        addToDrawer(scroller);
        addToNavbar(toggle, title);
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Primeros auxilios", "/",
                VaadinIcon.AMBULANCE.create()),
                new SideNavItem("Accesibilidad", "/accesibilidad", 
                VaadinIcon.COG.create())
                // ,    
                // new SideNavItem("Products", "/products",
                // VaadinIcon.PACKAGE.create()),
                // new SideNavItem("Documents", "/documents",
                // VaadinIcon.RECORDS.create()),
                // new SideNavItem("Tasks", "/tasks", 
                // VaadinIcon.LIST.create()),
                // new SideNavItem("Analytics", "/analytics",
                // VaadinIcon.CHART.create())
                );
        return sideNav;
    }
    //Prueba

}
