import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AgenceRepository agRep = new AgenceRepository();
        Agence agence = new Agence("TravellingAdventure");
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
            int reponse;
            try {
                reponse = sc.nextInt();
            }catch (InputMismatchException e){
                    System.out.println("Mauvaise réponse, veuillez saisir de nouveau : ");
                    reponse = sc.nextInt();
                }
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
                        agence = agRep.load();
                        
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
