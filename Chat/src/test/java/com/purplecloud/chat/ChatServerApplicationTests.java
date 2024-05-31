package com.purplecloud.chat;

import com.purplecloud.chat.bean.Message;
import com.purplecloud.chat.bean.MessageType;
import com.purplecloud.chat.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatServerApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private MessageService messageService;
    @Test
    public void testSendMessage() {

        Message message = new Message();
        message.setUserId(1);
        message.setMessage("Hello");
        //message.setSendTime();
        message.setType(MessageType.PRIVATE);
        message.setTargetId(2);
        messageService.saveMessage(message);
        System.out.println("Message sent successfully!");
    }
    @Test
    public void testGetMessages() {
        Message message = new Message();
        message.setUserId(1);
        message.setType(MessageType.PRIVATE);
        message.setTargetId(2);
        System.out.println(messageService.getMessages(message));
    }
}
