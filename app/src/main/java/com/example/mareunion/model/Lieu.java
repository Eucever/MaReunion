package com.example.mareunion.model;

import java.util.Objects;

public class Lieu implements Comparable<Lieu>{
    private String endroit;

    public Lieu(String endroit){
        this.endroit=endroit;
    }

    public String getEndroit() {
        return endroit;
    }

    public void setEndroit(String endroit) {
        this.endroit = endroit;
    }

    @Override
    public int compareTo(Lieu lieu) {
        return endroit.compareTo(lieu.getEndroit());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lieu lieu = (Lieu) o;
        return Objects.equals(endroit, lieu.endroit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endroit);
    }
}
