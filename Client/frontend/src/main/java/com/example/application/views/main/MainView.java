package com.example.application.views.main;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;

@PageTitle("Boltech First Aid")
@Route("")
public class MainView extends AppLayout {
    
    private ChatComponent chat;

    public MainView() {
        //Menu de hamburguesa
        DrawerToggle toggle = new DrawerToggle();
 
        H1 title = new H1("Boltech - Asistente - ChatGPT");
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
        chat.setWidth("80%");

        //Añadimos a un horizontal layout el chat y los margenes
        HorizontalLayout mainLayout = new HorizontalLayout(chat);
        mainLayout.setPadding(true);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        setContent(mainLayout);
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Asistente - ChatGPT", "/",
                VaadinIcon.AMBULANCE.create()),
                new SideNavItem("Asistente - Llama 2", "/llama", 
                VaadinIcon.NURSE.create())
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
