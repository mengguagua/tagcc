package com.gcc.tagcc.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// @ConfigurationProperties 用来取 application.properties 里的参数值，按同名默认赋值
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Slf4j
public class EmailConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;

    @Bean
    public MailSender javaMailSender() {
        log.info("javaMailSender注入成功！！");
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        return javaMailSender;
    }

    public void setHost(String host) {
        this.host = host;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
