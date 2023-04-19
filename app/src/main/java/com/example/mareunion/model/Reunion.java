package com.example.mareunion.model;


import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reunion implements Comparable<Reunion> {

    private Instant date;

    private String sujet;

    private Set<Participant> participants;

    private Lieu lieu;

    public Reunion(Instant date, String sujet, Lieu lieu) {
        this.date = date;
        this.sujet = sujet;
        this.participants=new TreeSet<>();
        this.lieu = lieu;

    }

    public Reunion(Instant heure, String sujet, Set<Participant> participants, Lieu lieu) {
        this(heure,sujet,lieu);
        this.participants = participants;
    }
    public Reunion(Instant heure, String sujet, Lieu lieu, Participant... participants) {
        this(heure,sujet,lieu);
        addParticipants(participants);
    }
    public void addParticipants(Participant... p){
        this.participants.addAll(Stream.of(p).collect(Collectors.toList()));
    }

    public Instant getDate() {
        return date;
    }

    public String getSujet() {
        return sujet;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    @Override
    public int compareTo(Reunion reunion) {
        return getDate().compareTo(reunion.getDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return Objects.equals(date, reunion.date) && Objects.equals(lieu, reunion.lieu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, lieu);
    }
}
