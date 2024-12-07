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

    public static void closeProgram(Scanner scanner){
        System.out.println("Merci d'avoir joué, si vous souhaitez revenir sur le jeu, il va falloir relancer le programme \uD83D\uDE00 ");
        scanner.close();
        System.exit(0);
    }

    public static void cheat(){
        CheatMode.setCheatMode(!CheatMode.isCheatMode());
        System.out.println("Mode de triche : "+CheatMode.isCheatMode());
    }

    public static void mainMenu(Scanner scanner) {
         while(true) {
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
        int salleID = 0;

        while (salleID<carte.getSalleCount()) {
            System.out.println("Voulez-vous poursuivre et entrer dans la Salle " + (salleID + 1) + " : " + carte.getSalle(salleID).getNom() + " ?\n" +
                    "\t - Oui\n" +
                    "\t - Non");

            String input = getInput(scanner);

            switch (input) {
                case "oui":
                    if (salleID < carte.getSalleCount()) {
                        playSalle(scanner, carte.getSalle(salleID),player);
                        ++salleID;
                        System.out.println(salleID);
                    } else System.out.println("Condition Else");
                    break;
                case "non":
                    return;
                default:
                    System.out.println("Erreur dans la Saisie, veuillez réessayer");
            }
        }
        System.out.println("Félicitations ! Vous êtes venus à bout de ces "+salleID+" salles !");
    }

    public static void playSalle(Scanner scanner, Salle salle,Player player) {
        while (salle.contientNPC()){
            salle.afficherMatriceAvecCouleurs();
            playerTurn(scanner,player);
            //npcTurn
            System.out.println("Sortie");
            closeProgram(scanner);
        }
        System.out.println("Sortie");
    }

    public static void playerTurn(Scanner scanner, Player player) {
        Boolean hasPlayed = false;

        while(!hasPlayed) {
            System.out.println("C'est à votre tour ! Que souhaitez vous faire ?\n" +
                    "\t - Attaquer\n" +
                    "\t - Heal\n" +
                    "\t - Shield\n" +
                    "\t - Informations");
            String input = getInput(scanner);

            switch (input) {
                case "attaquer":
                    //Attaque Joueur
                    System.out.println("Attaque");
                    hasPlayed = true;
                    // cible un joueur
                    break;
                case "heal":
                    //Heal Joueur
                    System.out.println("Se Soigne");
                    hasPlayed = true;
                    break;
                case "shield":
                    System.out.println(player.getDEF());
                    player.shield();
                    System.out.println(player.getDEF());
                    hasPlayed = true;
                    break;
                case "déplacement":
                    //Déplacement Joueur
                    System.out.println("Se Déplace");
                    break;
                case "informations":
                    System.out.println(player.getNAME());
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

        mainMenu(scanner);

    }
}