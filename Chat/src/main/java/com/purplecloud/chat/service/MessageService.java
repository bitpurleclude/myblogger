package com.purplecloud.chat.service;

import com.purplecloud.chat.bean.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);
    List<Message> getMessages(Message message);
}
