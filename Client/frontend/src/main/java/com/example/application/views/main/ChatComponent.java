package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.icon.Icon;

public class ChatComponent extends VerticalLayout {

    private MessageList list;
    private TextArea input;
    private Button sendButton;

    public ChatComponent() {
        setSpacing(true);
        //Cuadro para los chats
        list = new MessageList();
        list.setWidthFull();
        list.setHeightFull();
        list.getStyle().set("background-color", "whitesmoke");
        list.addClassName(LumoUtility.BorderRadius.LARGE);
        
        //Textfield para el mensaje del usuario
        input = new TextArea();
        input.setHeight("100%");

        //Botón para enviar el mensaje
        sendButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
        sendButton.addClickListener(clickEvent -> sendMessage(input.getValue()));
        sendButton.setHeight("100%");
        
        //Layout para el textfield y su botón
        HorizontalLayout chatLayout = new HorizontalLayout();
        chatLayout.setWidthFull();
        chatLayout.setFlexGrow(1, input);
        chatLayout.setFlexGrow(0, sendButton);
        chatLayout.add(input, sendButton);

        // Icono de advertencia a la izquierda
        Icon warningIconLeft = VaadinIcon.WARNING.create();
        warningIconLeft.setColor("red");
        warningIconLeft.setSize("5vw");

        //Aviso herramienta complementaria
        Span avisoTexto = new Span("Esto es una herramienta complementaria");

    
        // Icono de advertencia a la derecha
        Icon warningIconRight = VaadinIcon.WARNING.create();
        warningIconRight.setColor("red");
        warningIconRight.setSize("5vw");

        //Layout horizontal para el aviso herramienta complementaria
        HorizontalLayout avisoLayoutHerramienta = new HorizontalLayout();
        avisoLayoutHerramienta.setWidthFull();
        avisoLayoutHerramienta.setHeight("50%");
        //avisoLayoutHerramienta.add(warningIconLeft, avisoTexto, warningIconRight);
        avisoLayoutHerramienta.setAlignItems(Alignment.CENTER);
        avisoLayoutHerramienta.setJustifyContentMode(JustifyContentMode.CENTER);
        avisoLayoutHerramienta.setClassName("warning");

        // Icono de advertencia a la izquierda
        Icon doctorIconLeft = VaadinIcon.DOCTOR.create();
        doctorIconLeft.setSize("20px");

        //Aviso herramienta complementaria
        Span consejoTexto = new Span("En caso de emergencia grave, llame al 112");
        VerticalLayout consejoLayout2 = new VerticalLayout(avisoTexto, consejoTexto);
        
        // Icono de advertencia a la derecha
        Icon doctorIconRight = VaadinIcon.DOCTOR.create();
        doctorIconRight.setSize("1.5em");

        //Layout horizontal para el aviso herramienta complementaria
        HorizontalLayout consejoLayout = new HorizontalLayout();
        consejoLayout.setWidthFull();
        consejoLayout2.setWidthFull();
        consejoLayout.add(warningIconLeft, consejoLayout2, warningIconRight);

        consejoLayout2.setAlignItems(Alignment.CENTER);
        consejoLayout.setAlignItems(Alignment.CENTER);
        consejoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        consejoLayout2.setJustifyContentMode(JustifyContentMode.CENTER);

        /*
         * Temporal
         */
        consejoLayout.addClassName("consejo");
        consejoLayout.setPadding(true);
        avisoTexto.addClassName("textoArriba");
        consejoTexto.addClassName("textoAbajo");


        //Layout horizontal para aviso
        VerticalLayout avisoLayout = new VerticalLayout();
        avisoLayout.setWidthFull();
        avisoLayout.setHeight("15%");
        // avisoLayout.getStyle().set("background-color", "whitesmoke");
        avisoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        avisoLayout.add(consejoLayout);

        //Layout vertical para el chat
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(list, chatLayout);
        verticalLayout.setHeight("85%");

        //Layout vertical para el chat y el aviso
        VerticalLayout verticalLayout2 = new VerticalLayout();
        verticalLayout2.add(avisoLayout, verticalLayout);
        verticalLayout2.setHeightFull();
        verticalLayout2.setWidthFull();
        verticalLayout2.setAlignItems(Alignment.END);
        add(verticalLayout2);
    }

    private void sendMessage(String message) {
        if (message.trim().isEmpty()) {
            return;
        }

        addMessageToList(message, "YO");
        input.clear();

        // Crear un objeto Message con el mensaje recibido
        Message messageObj = new Message(message);

        try {
            // Enviar el mensaje al backend utilizando DataService
            String responseMessage = DataService.sendMessageToBackend(messageObj);

            // Agregar el mensaje de respuesta a la lista
            addMessageToListGPT(responseMessage, "ChatGPT");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            
            // Agregar un mensaje de error a la lista en caso de error
            addMessageToList("Error al enviar el mensaje: " + e.getMessage(), "Error");
        }
    }
    
    private void addMessageToList(String message, String sender) {
        MessageListItem newMessage = new MessageListItem(message, Instant.now(), sender);
        newMessage.setUserColorIndex(3);
        List<MessageListItem> items = new ArrayList<>(list.getItems());
        items.add(newMessage);
        list.setItems(items);
    }

    private void addMessageToListGPT(String message, String sender) {
        MessageListItem newMessage = new MessageListItem(message, Instant.now(), sender);
        newMessage.setUserColorIndex(1);
        List<MessageListItem> items = new ArrayList<>(list.getItems());
        items.add(newMessage);
        list.setItems(items);
    }

    public void configHorizontalLayout() {
       
    }
    
}