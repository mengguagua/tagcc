package com.gcc.tagcc.entity;

import org.springframework.stereotype.Component;

/**
 * @author gaoccc
 * @create 2019-04-30 23:09
 */
@Component
public class User {
    private String id;
    private String username;
    private String nickname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}