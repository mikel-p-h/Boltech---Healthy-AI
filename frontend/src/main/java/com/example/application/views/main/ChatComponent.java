package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.NotificationVariant;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;

public class ChatComponent extends VerticalLayout {

    private MessageList list;
    private TextArea input;
    private Button sendButton;

    public ChatComponent() {
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
        warningIconLeft.setSize("3em");

        //Aviso herramienta complementaria
        Span avisoTexto = new Span("Esto es una herramienta complementaria y no sustitutiva de la atención médica profesional");
        avisoTexto.getStyle().set("font-size", "2.4em");

        // Icono de advertencia a la derecha
        Icon warningIconRight = VaadinIcon.WARNING.create();
        warningIconRight.setColor("red");
        warningIconRight.setSize("3em");

        //Layout horizontal para el aviso herramienta complementaria
        HorizontalLayout avisoLayoutHerramienta = new HorizontalLayout();
        avisoLayoutHerramienta.setWidthFull();
        avisoLayoutHerramienta.setHeight("50%");
        avisoLayoutHerramienta.add(warningIconLeft, avisoTexto, warningIconRight);
        avisoLayoutHerramienta.setJustifyContentMode(JustifyContentMode.CENTER);

        // Icono de advertencia a la izquierda
        Icon doctorIconLeft = VaadinIcon.DOCTOR.create();
        doctorIconLeft.setSize("2.5em");

        //Aviso herramienta complementaria
        Span consejoTexto = new Span("En caso de emergencia grave, busca ayuda médica profesional inmediatamente, llama al 112");
        consejoTexto.getStyle().set("font-size", "1.8em");

        // Icono de advertencia a la derecha
        Icon doctorIconRight = VaadinIcon.DOCTOR.create();
        doctorIconRight.setSize("2.5em");

        //Layout horizontal para el aviso herramienta complementaria
        HorizontalLayout consejoLayout = new HorizontalLayout();
        consejoLayout.setWidthFull();
        consejoLayout.setHeight("50%");
        consejoLayout.add(doctorIconLeft, consejoTexto, doctorIconRight);
        consejoLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        //Layout horizontal para aviso
        VerticalLayout avisoLayout = new VerticalLayout();
        avisoLayout.setWidthFull();
        avisoLayout.setHeight("10%");
        // avisoLayout.getStyle().set("background-color", "whitesmoke");
        avisoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        avisoLayout.add(avisoLayoutHerramienta, consejoLayout);

        //Layout vertical para el chat
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(list, chatLayout);
        verticalLayout.setHeight("90%");

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

        MessageListItem newMessage = new MessageListItem(message, Instant.now(), "YO");
        newMessage.setUserColorIndex(3);
        List<MessageListItem> items = new ArrayList<>(list.getItems());
        items.add(newMessage);
        list.setItems(items);
        input.clear();
    }
}