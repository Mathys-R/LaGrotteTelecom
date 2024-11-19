package org.example;

class Salle {
    private String nom;
    private int ameliorationAttaque;
    private int ameliorationDefense;

    public Salle(String nom, int ameliorationAttaque, int ameliorationDefense) {
        this.nom = nom;
        this.ameliorationAttaque = ameliorationAttaque;
        this.ameliorationDefense = ameliorationDefense;
    }

    public String getNom() {
        return nom;
    }

    public int getAmeliorationAttaque() {
        return ameliorationAttaque;
    }

    public int getAmeliorationDefense() {
        return ameliorationDefense;
    }

    @Override
    public String toString() {
        return "Salle: " + nom +
               " | Amélioration Génération: " + ameliorationAttaque +
               " | Amélioration Défense: " + ameliorationDefense;
    }
}
