package com.example.mareunion.model;

import java.util.Objects;

public class Participant implements Comparable<Participant>{

    private String mail;

    public Participant(String mail){
        this.mail=mail;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public int compareTo(Participant participant) {
        return mail.compareTo(participant.getMail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }
}
