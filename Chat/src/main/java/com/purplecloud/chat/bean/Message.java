package com.purplecloud.chat.bean;

import java.time.LocalDateTime;

public class Message {
    private int userId;
    private int targetId;
    private MessageType type;
    private String message;
    private String pictureUrl;
    private String videoUrl;
    private LocalDateTime sendTime;

    public Message() {
    }

    public Message(int userId, int targetId, MessageType type, String message, String pictureUrl, String videoUrl, LocalDateTime sendTime) {
        this.userId = userId;
        this.targetId = targetId;
        this.type = type;
        this.message = message;
        this.pictureUrl = pictureUrl;
        this.videoUrl = videoUrl;
        this.sendTime = sendTime;
    }

    /**
     * 获取
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return targetId
     */
    public int getTargetId() {
        return targetId;
    }

    /**
     * 设置
     * @param targetId
     */
    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    /**
     * 获取
     * @return type
     */
    public MessageType getType() {
        return type;
    }

    /**
     * 设置
     * @param type
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * 获取
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取
     * @return pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * 设置
     * @param pictureUrl
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    /**
     * 获取
     * @return videoUrl
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置
     * @param videoUrl
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * 获取
     * @return sendTime
     */
    public LocalDateTime getSendTime() {
        return sendTime;
    }

    /**
     * 设置
     * @param sendTime
     */
    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String toString() {
        return "Message{userId = " + userId + ", targetId = " + targetId + ", type = " + type + ", message = " + message + ", pictureUrl = " + pictureUrl + ", videoUrl = " + videoUrl + ", sendTime = " + sendTime + "}";
    }
}
