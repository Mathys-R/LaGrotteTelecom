package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private List<Salle> salles;

    public Map() {
        salles = new ArrayList<>();
        genererSalles();
    }

    private void genererSalles() {
        Random random = new Random();
        int nombreDeSalles = 4 + random.nextInt(4); // Générer entre 4 et 7 salles
        String[] nomsPossibles = {"Forêt Enchantée", "Caverne Sombre", "Temple Abandonné", "Lac Mystique", "Forteresse Ancienne", "Colline Venteuse", "Mine Oubliée"};

        for (int i = 0; i < nombreDeSalles; i++) {
            String nom = nomsPossibles[random.nextInt(nomsPossibles.length)];
            int ameliorationAttaque = random.nextInt(5);
            int ameliorationDefense = random.nextInt(5);
            salles.add(new Salle(nom, ameliorationAttaque, ameliorationDefense));
        }
    }

    public void afficherSalles() {
        for (int i = 0; i < salles.size(); i++) {
            System.out.println("Salle " + (i + 1) + ": " + salles.get(i));
        }
    }
}
