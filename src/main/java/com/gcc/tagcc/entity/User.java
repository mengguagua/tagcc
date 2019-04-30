package com.gcc.tagcc.entity;

import org.springframework.stereotype.Component;

/**
 * @author gaoccc
 * @create 2019-04-30 23:09
 */
@Component
public class User {
    private String id;
    private String name;
    private String password;
    private int level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}