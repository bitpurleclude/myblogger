package com.purplecloud.userinfro.bean;


public class UserIfor {
    private int id;
    private String username;
    private String headPictureUrl;
    private String email;
    private String phone;
    private String address;

    public UserIfor() {
    }

    public UserIfor(int id, String username, String headPictureUrl, String email, String phone, String address) {
        this.id = id;
        this.username = username;
        this.headPictureUrl = headPictureUrl;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return headPictureUrl
     */
    public String getHeadPictureUrl() {
        return headPictureUrl;
    }

    /**
     * 设置
     * @param headPictureUrl
     */
    public void setHeadPictureUrl(String headPictureUrl) {
        this.headPictureUrl = headPictureUrl;
    }

    /**
     * 获取
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return "UserIfor{id = " + id + ", username = " + username + ", headPictureUrl = " + headPictureUrl + ", email = " + email + ", phone = " + phone + ", address = " + address + "}";
    }
}
