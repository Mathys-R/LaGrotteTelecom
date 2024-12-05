package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Map {
    private List<Salle> salles;

    public Map(Player player) {
        salles = new ArrayList<>();
        genererSalles(player);
    }

    private void genererSalles(Player player) {
        Random random = new Random();
        int nombreDeSalles = 4 + random.nextInt(4); // Générer entre 4 et 7 salles
        ArrayList<String> nomsPossibles = new ArrayList<>(Arrays.asList("Forêt Enchantée", "Caverne Sombre", "Temple Abandonné", "Lac Mystique", "Forteresse Ancienne", "Colline Venteuse", "Mine Oubliée"));

        for (int i = 0; i < nombreDeSalles; i++) {
            String nom = nomsPossibles.get(random.nextInt(nomsPossibles.size()));
            int ameliorationAttaque = random.nextInt(5);
            int ameliorationDefense = random.nextInt(5);
            salles.add(new Salle(nom, ameliorationAttaque, ameliorationDefense, player));
            nomsPossibles.remove(nom);
        }
    }

    public Salle getSalle(int idSalle) {
        return salles.get(idSalle);
    }

    public int getSalleCount() {
        return salles.size();
    }

    public void afficherSalles() {
        for (int i = 0; i < salles.size(); i++) {
            System.out.println("Salle " + (i + 1) + ": " + salles.get(i));
        }
    }

    public void afficherGrilleSalle() {
        if (salles.isEmpty()) {
            System.out.println("Il n'y a pas de salles générées.");
            return;
        }

        Salle premiereSalle = salles.get(0); // Récupère la première salle
        System.out.println("Grille de la première salle (" + premiereSalle.getNom() + "):");
        //Salle.afficherMatrice(); // Affiche la grille
    }
}
