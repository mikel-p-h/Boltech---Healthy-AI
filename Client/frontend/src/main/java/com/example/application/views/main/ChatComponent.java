package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
        input.addClassName("inputChat");
        input.setPlaceholder("Escriba aquí sus sintomas...");
        input.setHeight("100%");

        //Botón para enviar el mensaje
        sendButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickListener(clickEvent -> sendMessage(input.getValue()));
        sendButton.addClassName("sendButton");
        sendButton.setHeight("100%");
        
        //Layout para el textfield y su botón
        HorizontalLayout chatLayout = new HorizontalLayout();
        chatLayout.setWidthFull();
        chatLayout.setFlexGrow(1, input);
        chatLayout.setFlexGrow(0, sendButton);
        chatLayout.addClassName("chatLayout");
        chatLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        chatLayout.add(input, sendButton);
        
        //Layout vertical para el chat
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(list, chatLayout);        
        verticalLayout.addClassName("verticalLayout");
        verticalLayout.setHeight("85%");

        // Icono de advertencia a la izquierda
        Icon warningIconLeft = VaadinIcon.WARNING.create();
        warningIconLeft.setColor("red");
        warningIconLeft.setSize("5vw");
        warningIconLeft.addClassName("warningIconLeft");

        // Icono de advertencia a la izquierda para móviles
        Icon warningIconLeftPhone = VaadinIcon.WARNING.create();
        warningIconLeftPhone.setColor("red");
        warningIconLeftPhone.setSize("10vw");
        warningIconLeftPhone.addClassName("warningIconLeftPhone");

        //Aviso herramienta complementaria
        Span avisoTexto = new Span("Esto es una herramienta complementaria");
        avisoTexto.addClassName("textoArriba");
        Span consejoTexto = new Span("En caso de emergencia grave, llame al 112");
        consejoTexto.addClassName("textoAbajo");
        
        //Layout para el mensaje de aviso
        VerticalLayout consejoLayout = new VerticalLayout(avisoTexto, consejoTexto);
        consejoLayout.setAlignItems(Alignment.CENTER);
        consejoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        consejoLayout.addClassName("mensajeLayout");

        // Icono de advertencia a la derecha
        Icon warningIconRight = VaadinIcon.WARNING.create();
        warningIconRight.setColor("red");
        warningIconRight.setSize("5vw");
        warningIconRight.addClassName("iconRightMediaQuery");

        //Layout horizontal conjunto de los warnings y el aviso
        HorizontalLayout layoutConsejoWarning = new HorizontalLayout();
        layoutConsejoWarning.setWidthFull();
        layoutConsejoWarning.add(warningIconLeft, warningIconLeftPhone, consejoLayout, warningIconRight);
        layoutConsejoWarning.setAlignItems(Alignment.CENTER);
        layoutConsejoWarning.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutConsejoWarning.addClassName("consejo");
        layoutConsejoWarning.setPadding(true);

        //Layout horizontal para aviso
        VerticalLayout avisoLayout = new VerticalLayout();
        avisoLayout.setHeight("15%");
        avisoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        avisoLayout.add(layoutConsejoWarning);
        avisoLayout.addClassName("avisoLayout");

        //Layout vertical para el chat y el aviso
        VerticalLayout layoutVerticalConjunto = new VerticalLayout();
        layoutVerticalConjunto.add(avisoLayout, verticalLayout);
        layoutVerticalConjunto.setHeightFull();
        layoutVerticalConjunto.addClassName("layoutChatYAviso");
        add(layoutVerticalConjunto);
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
}
