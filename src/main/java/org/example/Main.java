package org.example;

import java.util.Scanner;
import static java.lang.Math.abs;
import java.util.logging.Logger;

/**
 * Classe principale qui gère l'exécution du jeu, les actions du joueur et des NPCs.
 * Utilise un logger pour enregistrer des informations importantes et des événements du jeu.
 */
public class Main {

    // Création d'un objet logger pour l'enregistrement des logs du jeu
    private static final Logger logger = LogControler.getLogger();

    /**
     * Récupère une entrée utilisateur et la convertit en minuscules.
     *
     * @param scanner L'objet scanner utilisé pour lire les entrées de l'utilisateur
     * @return La chaîne de caractères entrée par l'utilisateur
     */
    public static String getInput(Scanner scanner) {
        String input = scanner.nextLine().toLowerCase();
        logger.info("Entrée de l'utilisateur : " + input);  // Enregistrement du choix de l'utilisateur
        return input;
    }

    /**
     * Ferme le programme et remercie l'utilisateur.
     *
     * @param scanner L'objet scanner utilisé dans le programme
     */
    public static void closeProgram(Scanner scanner) {
        System.out.println("Merci d'avoir joué, si vous souhaitez revenir sur le jeu, il va falloir relancer le programme.");
        scanner.close();
        logger.info("Le joueur a quitté le programme.");
        System.exit(0);
    }

    /**
     * Active ou désactive le mode triche dans le jeu.
     */
    public static void cheat() {
        CheatMode.setCheatMode(!CheatMode.isCheatMode());
        System.out.println("Mode de triche : " + CheatMode.isCheatMode());
        logger.info("Mode de triche activé : " + CheatMode.isCheatMode());
    }

    /**
     * Demande à l'utilisateur de saisir un entier et valide l'entrée.
     *
     * @param scanner L'objet scanner utilisé pour lire l'entrée de l'utilisateur
     * @return L'entier saisi par l'utilisateur
     */
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
                logger.warning("Entrée invalide pour un entier : " + input);  // Log de l'erreur d'entrée
            }
        }
        return newIntValue;
    }

    /**
     * Permet au joueur de se déplacer sur la carte en entrant de nouvelles coordonnées.
     *
     * @param scanner L'objet scanner utilisé pour lire les entrées de l'utilisateur
     * @param salle   La salle actuelle où le joueur se trouve
     * @param player  L'objet représentant le joueur
     */
    public static void deplacementJoueur(Scanner scanner, Salle salle, Player player) {
        boolean hasMoved = false;

        while (!hasMoved) {
            System.out.println("Veuillez indiquer la nouvelle coordonnée X de votre personnage :");
            int newPosX = getIntValue(scanner);
            System.out.println("Veuillez indiquer la nouvelle coordonnée Y de votre personnage :");
            int newPosY = getIntValue(scanner);

            if (salle.deplacementCharacter(player, newPosX, newPosY)) {
                hasMoved = true;
                logger.info("Le joueur s'est déplacé vers (" + newPosX + ", " + newPosY + ").");  // Log du déplacement
            } else {
                System.out.println("Les coordonnées que vous avez saisies ne peuvent être atteintes !\n");
                logger.warning("Déplacement échoué pour le joueur vers (" + newPosX + ", " + newPosY + ").");
            }
        }
    }

    /**
     * Affiche les informations concernant la salle et le joueur.
     *
     * @param salle  La salle actuelle
     * @param player L'objet représentant le joueur
     */
    public static void showInformations(Salle salle, Player player) {
        salle.afficherMatrice(player);
        System.out.println(player);
        salle.showNPC();
        logger.info("Affichage des informations de la salle et du joueur.");
    }

    /**
     * Permet au joueur de cibler un NPC et d'attaquer ce dernier.
     *
     * @param salle   La salle actuelle
     * @param player  L'objet représentant le joueur
     * @param scanner L'objet scanner utilisé pour lire les entrées de l'utilisateur
     */
    public static void designTarget(Salle salle, Player player, Scanner scanner) {
        System.out.println("Voici la liste des ennemis encore sur la carte :");
        salle.showNPC();

        NPC target = null;
        while (target == null) {
            System.out.println("Qui souhaitez-vous cibler ? Veuillez désigner la cible par son ID :");
            int targetID = getIntValue(scanner);

            target = salle.getNpcByID(targetID);

            if (target == null) {
                System.out.println("L'ID choisi n'est pas correct. Veuillez choisir un ID valide parmi ceux affichés.");
                logger.warning("Ciblage échoué : ID invalide " + targetID);
            }
        }
        player.attack(player.getATK(), target);
        logger.info("Le joueur attaque le NPC avec ID " + target.getID() + " (" + target.getName() + ").");
    }

    /**
     * Gère le tour d'un NPC, y compris les actions de déplacement, d'attaque ou de soin.
     *
     * @param salle   La salle actuelle
     * @param player  L'objet représentant le joueur
     */
    private static void moveNPCTowardsPlayer(Salle salle, NPC mob, Player player) {
        int deltaX = player.getPosX() - mob.getPosX();
        int deltaY = player.getPosY() - mob.getPosY();

        int newX = mob.getPosX();
        int newY = mob.getPosY();

        // Priorité au mouvement horizontal (X), puis vertical (Y)
        if (abs(deltaX) > abs(deltaY)) {
            newX += (deltaX > 0) ? 1 : -1;
        } else {
            newY += (deltaY > 0) ? 1 : -1;
        }

        // Vérification et application du déplacement
        if (salle.deplacementCharacter(mob, newX, newY)) {
            System.out.println(mob.getName() + " s'est déplacé vers (" + newX + ", " + newY + ")");
        } else {
            System.out.println(mob.getName() + " n'a pas pu se déplacer !");
            logger.warning(mob.getName() + " n'a pas pu se déplacer vers (" + newX + ", " + newY + ")");
        }
    }

    /**
     * Exécute le tour des NPCs, où chaque NPC peut attaquer, se déplacer ou se soigner.
     *
     * @param salle   La salle actuelle
     * @param player  L'objet représentant le joueur
     */
    public static void npcTurn(Salle salle, Player player) {
        for (NPC mob : salle.getListNPC()) {
            int distance = abs(player.getPosX() - mob.getPosX()) + abs(player.getPosY() - mob.getPosY());

            if (distance <= mob.getRange()) {
                // PNJ à portée d'attaque
                System.out.println(mob.getName() + " attaque le joueur !");
                mob.attack(mob.getATK(), player);
                logger.info(mob.getName() + " attaque le joueur.");
            } else if (mob.getHp() < mob.getHP_MAX() / 2 && Math.random() > 0.25) {
                // PNJ se soigne si sa santé est inférieure à 50%
                System.out.println(mob.getName() + " se soigne !");
                mob.heal(10);
                logger.info(mob.getName() + " se soigne.");
            } else {
                // PNJ se déplace vers le joueur
                System.out.println(mob.getName() + " se déplace vers le joueur !");
                moveNPCTowardsPlayer(salle, mob, player);
            }

            if (!player.isAlive()) {
                System.out.println("Le joueur a été tué par " + mob.getName() + " !");
                logger.info("Le joueur a été tué par " + mob.getName() + ".");
                return; // Fin de la partie
            }
        }
    }

    /**
     * Gère le déroulement d'une salle du jeu, en alternant entre le tour du joueur et des NPCs.
     *
     * @param scanner L'objet scanner utilisé pour lire les entrées de l'utilisateur
     * @param salle   La salle actuelle
     * @param player  L'objet représentant le joueur
     */
    public static void playSalle(Scanner scanner, Salle salle, Player player) {
        while (salle.contientNPC(player) && player.isAlive()) {
            playerTurn(scanner, salle, player);
            salle.refreshGrille(player);
            npcTurn(salle, player);
            salle.refreshGrille(player);
        }
        if (player.isAlive()) {
            System.out.println("Félicitations ! Vous êtes venus à bout de la salle " + salle.getNom() + " !");
            logger.info("Le joueur a terminé la salle " + salle.getNom());
        } else {
            System.out.println("Dommage, vous êtes morts. Votre aventure s'arrête là pour cette fois !");
            logger.info("Le joueur a perdu dans la salle " + salle.getNom());
        }
    }

    /**
     * Gère le tour du joueur, lui permettant de choisir une action (attaquer, se déplacer, etc.).
     *
     * @param scanner L'objet scanner utilisé pour lire les entrées de l'utilisateur
     * @param salle   La salle actuelle
     * @param player  L'objet représentant le joueur
     */
    public static void playerTurn(Scanner scanner, Salle salle, Player player) {
        Boolean hasPlayed = false;

        while (!hasPlayed) {
            salle.afficherMatrice(player);
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
                case "clear":
                    salle.killAll();
                    hasPlayed = true;
                    break;
                default:
                    System.out.println("Erreur dans la Saisie, veuillez réessayer");
            }
        }
    }

    /**
     * Le menu principal qui permet au joueur de commencer une partie ou de quitter.
     *
     * @param scanner L'objet scanner utilisé pour lire les entrées de l'utilisateur
     */
    public static void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nVous êtes dans le Menu Principal\n" +
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
                    logger.warning("Entrée invalide dans le menu principal : " + input);
            }
        }
    }

    /**
     * Méthode principale pour lancer la partie, choisir le nom du joueur et gérer les salles.
     *
     * @param scanner L'objet scanner utilisé pour lire les entrées de l'utilisateur
     */
    public static void startParty(Scanner scanner) {
        System.out.println("Veuillez renseigner le nom que vous souhaitez porter en tant que joueur :");
        String playerName = scanner.nextLine();

        Player player = new Player(playerName);
        Map carte = new Map(player);
        carte.afficherSalles();
        int currentSalleID = 0;

        while (currentSalleID < carte.getSalleCount() && player.isAlive()) {
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
                    }
                    break;
                case "non":
                    return;
                default:
                    System.out.println("Erreur dans la Saisie, veuillez réessayer");
                    logger.warning("Entrée invalide pour démarrer une nouvelle salle.");
            }
        }
        if (player.isAlive()) {
            System.out.println("Félicitations ! Vous êtes venus à bout de ces " + currentSalleID + " salles ! \n" +
                    "Vous avez GAGNÉ !!!");
            logger.info("Le joueur a terminé avec succès " + currentSalleID + " salles.");
        }
    }

    /**
     * Fonction principale exécutée au démarrage du programme.
     *
     * @param args Arguments de la ligne de commande (non utilisés ici)
     */
    public static void main(String[] args) {
        System.out.println("\nStudents : Louison Prudhome and Mathys Rosinski");
        System.out.println("Disclaimer ! Vos inputs ne sont pas case sensitive, mais faites attention à l'orthographe.\n");

        CheatMode.setCheatMode(false);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue à la Grotte Télécom !");
        mainMenu(scanner);
    }
}
