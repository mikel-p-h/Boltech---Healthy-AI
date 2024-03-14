package com.example.application.views.main;

import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ChatComponent extends VerticalLayout {

    private MessageList list;
    private TextField input;
    private Button sendButton;

    public ChatComponent() {
        //Cuadro para los chats
        list = new MessageList();
        list.setWidthFull();
        list.setHeightFull();
        //Textfield para el mensaje del usuario
        input = new TextField();

        //Botón para enviar el mensaje
        sendButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
        sendButton.addClickListener(clickEvent -> sendMessage(input.getValue()));
        
        //Layout para el textfield y su botón
        HorizontalLayout chatLayout = new HorizontalLayout();
        chatLayout.setWidthFull();
        chatLayout.setFlexGrow(1, input);
        chatLayout.setFlexGrow(0, sendButton);
        chatLayout.add(input, sendButton);

        //Layout de margen inferior
        HorizontalLayout bottomMargin = new HorizontalLayout();
        bottomMargin.setHeight("5%");

        add(list, chatLayout, bottomMargin);
    }

    private void sendMessage(String message) {
        if (message.trim().isEmpty()) {
            return;
        }

        MessageListItem newMessage = new MessageListItem(message, Instant.now(), "Usuario");
        newMessage.setUserColorIndex(3);
        List<MessageListItem> items = new ArrayList<>(list.getItems());
        items.add(newMessage);
        list.setItems(items);
        input.clear();
    }
}