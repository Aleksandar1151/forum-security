package com.forumsecurity.forum.models;

public class MailStructure {
    public String subject;
    public String message;

    public MailStructure(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }
}
