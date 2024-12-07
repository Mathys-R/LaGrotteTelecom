package org.example;

import javax.sound.midi.SysexMessage;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static String getInput(Scanner scanner) {
        return scanner.nextLine().toLowerCase();
    }

    public static void closeProgram(Scanner scanner) {
        System.out.println(
                "Merci d'avoir joué, si vous souhaitez revenir sur le jeu, il va falloir relancer le programme \uD83D\uDE00 ");
        scanner.close();
        System.exit(0);
    }

    public static void cheat() {
        CheatMode.setCheatMode(!CheatMode.isCheatMode());
        System.out.println("Mode de triche : " + CheatMode.isCheatMode());
    }

    public static int getIntValue(Scanner scanner) {
        int newIntValue = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Entrez votre valeur : ");
            String input = scanner.nextLine();

            try {
                newIntValue = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Valeur invalide, soyez sûr de bien rentrer un chiffre !");
            }
        }
        return newIntValue;
    }

    public static void deplacementJoueur(Scanner scanner, Salle salle, Player player) {
        Boolean hasMoved = false;
        while (!hasMoved) {
            System.out.println("Veuillez indiquer la nouvelle coordonnée X de votre personnage :");
            int newPosX = getIntValue(scanner);
            System.out.println("Veuillez indiquer la nouvelle coordonnée Y de votre personnage :");
            int newPosY = getIntValue(scanner);
            if (salle.deplacementCharacter(player, newPosX, newPosY)) {
                hasMoved = true;
            } else {
                System.out.println("Les coordonnées que vous avez saisies ne peuvent être atteintes !\n");
            }
        }
    }

    public static void showInformations(Salle salle, Player player) {
        salle.afficherMatriceAvecCouleurs();
        System.out.println(player);
        salle.showNPC();
    }

    public static void designTarget(Salle salle, Player player, Scanner scanner) {
        System.out.println("Voici la liste des ennemis encore sur la carte :");
        salle.showNPC();
        System.out.println("Qui souhaitez vous cibler ? Veuillez désigner la cible par son ID :");
        player.attack(100, salle.getNpcByID(getIntValue(scanner)));
    }

    public static void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("Vous êtes dans le Menu Principal\n" +
                    "Nous vous invitons à sélectionner ce que vous souhaitez faire :\n" +
                    "\t - Jouer\n" +
                    "\t - Quitter");

            String input = getInput(scanner);

            switch (input) {
                case "quitter":
                    closeProgram(scanner);
                    break;
                case "jouer":
                    startParty(scanner);
                    break;
                case "cheatmode":
                    cheat();
                    break;
                default:
                    System.out.println("Erreur dans la Saisie, veuillez réessayer");
            }
        }
    }

    public static void startParty(Scanner scanner) {
        System.out.println("Veuillez renseigner le nom que vous souhaitez porter en tant que joueur :");
        String playerName = scanner.nextLine();

        Player player = new Player(playerName);
        Map carte = new Map(player);
        carte.afficherSalles();
        int currentSalleID = 0;

        while (currentSalleID < carte.getSalleCount()) {
            System.out.println("Voulez-vous poursuivre et entrer dans la Salle " + (currentSalleID + 1) + " : "
                    + carte.getSalle(currentSalleID).getNom() + " ?\n" +
                    "\t - Oui\n" +
                    "\t - Non");

            String input = getInput(scanner);

            switch (input) {
                case "oui":
                    if (currentSalleID < carte.getSalleCount()) {
                        playSalle(scanner, carte.getSalle(currentSalleID), player);
                        ++currentSalleID;
                        System.out.println(currentSalleID);
                    } else
                        System.out.println("Condition Else");
                    break;
                case "non":
                    return;
                default:
                    System.out.println("Erreur dans la Saisie, veuillez réessayer");
            }
        }

        System.out.println("Félicitations ! Vous êtes venus à bout de ces " + currentSalleID + " salles !");
    }

    public static void playSalle(Scanner scanner, Salle salle, Player player) {
        while (salle.contientNPC()) {
            salle.afficherMatriceAvecCouleurs();
            playerTurn(scanner, salle, player);
            // npcTurn

            System.out.println("Sortie");
            closeProgram(scanner);
        }
        System.out.println("Sortie");
    }

    public static void playerTurn(Scanner scanner, Salle salle, Player player) {
        Boolean hasPlayed = false;

        while (!hasPlayed) {
            System.out.println("C'est à votre tour ! Que souhaitez vous faire ?\n" +
                    "\t - Attaquer\n" +
                    "\t - Heal\n" +
                    "\t - Shield\n" +
                    "\t - Déplacement\n" +
                    "\t - Informations");
            String input = getInput(scanner);

            switch (input) {
                case "attaquer":
                    designTarget(salle, player, scanner);
                    hasPlayed = true;
                    break;
                case "heal":
                    player.heal(10);
                    hasPlayed = true;
                    break;
                case "shield":
                    player.shield();
                    hasPlayed = true;
                    break;
                case "déplacement":
                    deplacementJoueur(scanner, salle, player);
                    break;
                case "informations":
                    showInformations(salle, player);
                    break;
                default:
                    System.out.println("Erreur dans la Saisie, veuillez réessayer");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Students : Louison Prudhomme and Mathys Rosinski");

        CheatMode.setCheatMode(false);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue à la Grotte Télécom !");
        System.out.println(
                "Disclaimer ! Vos inputs ne sont pas case sensitive, mais faites attention à l'orthographe \\uD83D\\uDE00");
        mainMenu(scanner);

    }
}