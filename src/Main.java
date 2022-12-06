import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AgenceRepository agRep = new AgenceRepository();
        Agence agence = new Agence();
        agence.genererVols();
        agRep.addAgence(agence);
        System.out.println("Bonjour et Bienvenue sur notre site de reservation de voyage !");
        loop : while(true){
            // TODO: faire les menus
            // - Faire une reserv / - Les lister / - Quitter
            System.out.println("Choisissez ce que vous voulez : ");
            System.out.println("1 - Faire une reservation");
            System.out.println("2 - Consulter une reservation");
            System.out.println("3 - Lister les reservations");
            System.out.println("4 - Sauvegarder");
            System.out.println("5 - Quitter");
            int reponse = sc.nextInt();
            switch (reponse){
                case 1:
                    agence.ajouterReservation();
                    break;
                case 2:
                    System.out.println("Pas encore implémenté");
                    break;
                case 3:
                    agence.listerReservations();
                    break;
                case 4:
                    try {
                        agRep.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    break loop;
                default:
                    System.out.println("Choix incorrect");
                    break;
            }
        }
        System.out.println("Merci ! Au revoir.");

    }
}
