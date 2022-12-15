import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AgenceRepository agRep = new AgenceRepository();
        int idAgence = 1;
        Agence agence = new Agence(idAgence,"TravellingAdventure");
        agence.genererVols();
        agRep.addAgence(agence);
        System.out.println("Bonjour et Bienvenue sur notre site de reservation de voyage !");
        loop : while(true){
            System.out.println("Choisissez ce que vous voulez : ");
            System.out.println("1 - Faire une reservation");
            System.out.println("2 - Consulter une reservation");
            System.out.println("3 - Lister les reservations");
            System.out.println("4 - Sauvegarder");
            System.out.println("5 - Load une sauvegarde");
            System.out.println("6 - Quitter");
            String reponseString = "";
            do {
                System.out.println("Veuillez saisir le numéro correspondant à une action :");
                reponseString = sc.nextLine();
            } while(!(reponseString.matches("[1-6]") && reponseString.length() > 0));
            int reponse = Integer.parseInt(reponseString);
            switch (reponse){
                case 1:
                    agence.ajouterReservation();
                    break;
                case 2:
                    agence.consulterReservation();
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
                    try {
                        Agence newAgence = agRep.load();
                        agRep.removeAgence(agence);
                        agence = newAgence;
                        agRep.addAgence(agence);
                        // agence.listerReservations();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    break loop;
                default:
                    System.out.println("Choix incorrect");
                    break;
            }
        }
        System.out.println("Merci ! Au revoir.");

    }
}
