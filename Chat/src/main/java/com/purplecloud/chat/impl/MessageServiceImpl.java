package com.purplecloud.chat.impl;


import com.purplecloud.chat.bean.Message;
import com.purplecloud.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class MessageServiceImpl implements MessageService {

    private final RedisTemplate<String, Message> redisTemplate;

    @Autowired
    public MessageServiceImpl(RedisTemplate<String, Message> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveMessage(Message message) {
        String key = message.getType().name() + ":" + message.getTargetId();
        redisTemplate.opsForList().rightPush(key, message);
        redisTemplate.expire(key, 24, TimeUnit.HOURS);
    }

    @Override
    public List<Message> getMessages(Message message) {
        String key = message.getType().name() + ":" + message.getTargetId();
        List<Message> messages = redisTemplate.opsForList().range(key, 0, -1);
        redisTemplate.delete(key);
        return messages;
}
}