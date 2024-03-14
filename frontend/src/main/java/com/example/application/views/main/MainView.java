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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Main")
@Route("")
public class MainView extends AppLayout {

    private ChatComponent chat;

    public MainView() {
        //Margins of the content
        VerticalLayout leftmargin = new VerticalLayout();
        leftmargin.setWidth("10%");
        VerticalLayout rightmargin = new VerticalLayout();
        rightmargin.setWidth("10%");

        //Menu de hamburguesa
        DrawerToggle toggle = new DrawerToggle();
 
        H1 title = new H1("Boltech - Primeros auxilios");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        //Menu desplegado de la hamburguesa
        SideNav nav = getSideNav();
        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);
        
        //Añadimos los menus a la vista
        addToDrawer(scroller);
        addToNavbar(toggle, title);
        
        //Generamos el chat
        chat = new ChatComponent();

        //Añadimos a un horizontal layout el chat y los margenes
        HorizontalLayout mainLayout = new HorizontalLayout(leftmargin, chat, rightmargin);
        mainLayout.expand(chat);
        mainLayout.setSizeFull();

        setContent(mainLayout);
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
}
