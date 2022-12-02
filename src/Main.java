import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Agence agence = new Agence();

        System.out.println("Bonjour et Bienvenu sur notre site de reservation de voyage !");
        while(true){
            // TODO: faire les menus
            // - Faire une reserv / - Les lister / - Quitter
            agence.ajouterReservation();
            System.out.println(agence.getNbReservations());
        }


    }
}
