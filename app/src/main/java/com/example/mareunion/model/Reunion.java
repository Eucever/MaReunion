package com.example.mareunion.model;

import java.util.List;

public class Reunion {

    private String nomReu;

    private String heure;

    private String sujet;

    private List<Participant> participants;

    private Lieu lieu;

    public Reunion(String nomReu, String heure, String sujet, Lieu lieu, List<Participant>participants){
        this.nomReu = nomReu;
        this.heure = heure;
        this.sujet = sujet;
        this.lieu = lieu;
        this.participants = participants;
    }

    public String getNomReu() {
        return nomReu;
    }

    public String getHeure() {
        return heure;
    }

    public String getSujet() {
        return sujet;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setNomReu(String nomReu) {
        this.nomReu = nomReu;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }
    public void addParticipants(Participant participant){
        this.participants.add(participant);
    }
}
