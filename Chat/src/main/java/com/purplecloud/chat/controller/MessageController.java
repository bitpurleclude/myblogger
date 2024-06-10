package com.purplecloud.chat.controller;

import com.purplecloud.chat.bean.Message;
import com.purplecloud.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @RequestMapping("/send")
    public UUID sendMessage(@RequestBody Message message) {
        UUID messageId = UUID.randomUUID();
        message.setMessageId(messageId);
        messageService.saveMessage(message);
        return messageId;
    }
    @RequestMapping("/receive")
    public List<Message> receiveMessage(@RequestBody Message message) {
        List<Message> messages = messageService.getMessages(message);
        return messages;
    }
}
