package com.purplecloud.chat;

import com.purplecloud.chat.bean.Message;
import com.purplecloud.chat.bean.MessageType;
import com.purplecloud.chat.service.MessageService;
import com.purplecloud.chat.util.JWTUtil;
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
    @Test
    public void Token() {
        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc0N7E0NjSw0FEqLU4t8kxRsjI1A0rmJmbmADUYmpoamptamJgYORQW6iXn5wI1FqQW5WYWF2fm5ylZRSsF-fu4xiem5GbmAaXcU_NSixJzFMIyU8sz89IVAhBKY2sByzmKsoUAAAA.lBBSEZaBCt_2ju3zTBdmgOWY6Sdc1aE2ukRW1IAX5KKCSGwqX1hSbcQdoWYfnck8gWvl_i5LQPW4mRCd4CO_Og";
        System.out.println(JWTUtil.getEmail(token));
        System.out.println(JWTUtil.getUserId(token));
        System.out.println(JWTUtil.getPermission(token));

    }
}
