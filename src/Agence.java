import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// AGGREGATE
public class Agence {
    private String nom;
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private int poolTickets;
    private boolean[][] tabOfDestination = new boolean[][]{
            {false,true,true,true,true},
            {true,false,false,true,true},
            {true,false,false,true,false},
            {false,true,true,false,false},
            {true,true,true,false,false}
    };
    
    

    public void listerDestinations(Ville depart, boolean[][] tab){
        int positionDepart = depart.ordinal();
        for (int i = 0; i < Ville.values().length; i++) {
            if(tab[positionDepart][i] == true){
                System.out.println(Ville.values()[i]);
            }
        }
    }


    public ArrayList<String> proposerDates(int nbDates){
        ArrayList<String> dates = new ArrayList<>();

        for (int i = 0; i < nbDates; i++) {
            String randomDate = String.format("%02d/%02d/%04d", (int)(Math.random() * 28 + 1), (int)(Math.random() * 12 + 1), (int)(Math.random() * 2 + 2023));
            dates.add(randomDate);
        }
        return dates;

    }

    public ArrayList<Hotel> proposerHotel(int nb, Ville destination){
        ArrayList<Hotel> hotels = new ArrayList<>();
        Random r = new Random();
        int low = 40;
        int high = 120;


        for (int i = 0; i < nb; i++) {

            NomHotel nomHotel = NomHotel.values()[new Random().nextInt(NomHotel.values().length)];
            while (hotels.toString().contains(nomHotel.name())){
                nomHotel = NomHotel.values()[new Random().nextInt(NomHotel.values().length)];
            }

            int prix = r.nextInt(high-low) + low;
            Hotel hotel = new Hotel(nomHotel,prix,destination,false);
            hotels.add(hotel);
        }

        return hotels;

    }

    public ArrayList<Voiture> proposerVoitures(int nb, Ville destination){
        ArrayList<Voiture> voitures = new ArrayList<>();
        Random r = new Random();
        int low = 50;
        int high = 120;


        for (int i = 0; i < nb; i++) {
            NomVoiture nomVoiture = NomVoiture.values()[new Random().nextInt(NomVoiture.values().length)];            ;
             while (voitures.toString().contains(nomVoiture.name())){
                 nomVoiture = NomVoiture.values()[new Random().nextInt(NomVoiture.values().length)];
             }
            int prix = r.nextInt(high-low) + low;
            Voiture voiture = new Voiture(nomVoiture,prix,destination);
            voitures.add(voiture);
        }

        return voitures;

    }

    public void setPoolTickets(int poolTickets) {
        this.poolTickets = poolTickets;
    }

    public void ajouterReservation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Quel est votre nom et votre prénom ?");
        String nom = sc.next();
        String prenom = sc.next();
        String id = UUID.randomUUID().toString();

        // creer le client
        Client client = new Client(id,nom,prenom);

        System.out.println("De quel Aéroport partez vous ?");
        String aeroDep = sc.next();
        Ville depart = trouverVille(aeroDep);
        System.out.println("Voici les destinations possibles :");
        listerDestinations(depart,tabOfDestination);
        if(poolTickets > 0)
            System.out.println("Vous disposez d'une réduction de 20% sur le prix du vol");
        System.out.println("Quelle est la destination ?");
        String aeroArr = sc.next();
        Ville destination = trouverVille(aeroArr);
        Vol vol = creerVol(depart,destination);
        System.out.println("Le prix pour ce vol est de "+vol.getPrix()+" euros");
        System.out.println("Voici les dates proposées pour cette destination :");
        System.out.println(vol.getDates());
        System.out.println("Quelle date choisissez vous ?");
        String date = sc.next();
        System.out.println("Date validée");
        System.out.println("Desirez vous voyager en 1ere classe ? Oui ou Non");
        String premiereClasse = sc.next();
        boolean boolPremClasse = false;
        if(premiereClasse.equals("Oui"))
            boolPremClasse = true;
        System.out.println("Prenez vous la formule avec service ? Oui ou Non");
        String reponse = sc.next();
        System.out.println(reponse);
        if(reponse.equals("Oui")){
            System.out.println("Quel Service choisissez vous ?");
            System.out.println("1 - Service simple: l’hotel et ´eventuellement location de voiture mais dans un seul lieu");
            System.out.println("2 - Service de haute gamme: hotel et ´eventuellement une location de voiture pour rejoindre une\n" +
                    "autre destination puis hotel et voiture `a cette nouvelle destination");
            int service = sc.nextInt();
            if(service == 1){
                System.out.println("Quel Hotel choisissez vous ?");
                vol.afficherListeHotels();
                String hotelChoisi = sc.next();
                Hotel monHotel = vol.choisirHotel(hotelChoisi);
                System.out.println("Prenez vous une voiture ? Oui ou Non");
                String reponseVoiture = sc.next();
                Service monService = new ServiceSimple(monHotel);

                if(reponseVoiture.equals("Oui")){
                    System.out.println("Quel Voiture choisissez vous ?");
                    vol.afficherListeVoitures();
                    String voitureChoisi = sc.next();
                    Voiture maVoiture = vol.choisirVoiture(voitureChoisi);
                    monService = new ServiceSimple(monHotel,maVoiture);
                }
                Reservation reservation = new Reservation(vol,client,monService,boolPremClasse);
                if(premiereClasse.equals("Oui"))

                reservations.add(reservation);
                System.out.println("Votre réservation a aboutit ! ");
                System.out.println("Voici le prix de votre réservation : ");
                System.out.println(reservation.getMontant());
            }
            if(service == 2){
                boolean boolVoit = false;
                Voiture maPremiereVoiture = null;
                System.out.println("Quel Hotel choisissez vous pour la premiere destination ?");
                vol.afficherListeHotels();
                String premierHotelChoisi = sc.next();
                Hotel monPremierHotel = vol.choisirHotel(premierHotelChoisi);
                System.out.println("Prenez vous une voiture pour rejoindre l'autre destination ? Oui ou Non");
                String reponseVoiture = sc.next();

                if(reponseVoiture.equals("Oui")){
                    System.out.println("Quel Voiture choisissez vous ?");
                    vol.afficherListeVoitures();
                    String voitureChoisi = sc.next();
                    maPremiereVoiture = vol.choisirVoiture(voitureChoisi);
                    boolVoit = true;
                }

                System.out.println("Quel Hotel choisissez vous pour la deuxième destination ?");
                vol.afficherListeHotels();
                String deuxiemeHotelChoisi = sc.next();
                Hotel monDeuxiemeHotel = vol.choisirHotel(deuxiemeHotelChoisi);
                System.out.println("Prenez vous une voiture dans votre deuxième destination ? Oui ou Non");
                reponseVoiture = sc.next();

                Service monService = new ServiceHauteGamme(monPremierHotel,monDeuxiemeHotel);
                if(boolVoit)
                    monService = new ServiceHauteGamme(monPremierHotel,monDeuxiemeHotel,maPremiereVoiture);

                if(reponseVoiture.equals("Oui")){
                    System.out.println("Quel Voiture choisissez vous ?");
                    vol.afficherListeVoitures();
                    String voitureChoisi = sc.next();
                    Voiture maDeuxiemeVoiture = vol.choisirVoiture(voitureChoisi);
                    if(boolVoit)
                        monService = new ServiceHauteGamme(monPremierHotel,monDeuxiemeHotel,maPremiereVoiture,maDeuxiemeVoiture);
                    else
                        monService = new ServiceHauteGamme(monPremierHotel,monDeuxiemeHotel,maDeuxiemeVoiture);
                }
                Reservation reservation = new Reservation(vol,client,monService,boolPremClasse);
                reservations.add(reservation);
                System.out.println("Votre réservation a aboutit ! ");
                System.out.println("Voici le prix de votre réservation : ");
                System.out.println(reservation.getMontant());
            }

        }
        if(reponse.equals("Non")){
            Reservation reservation = new Reservation(vol,client,boolPremClasse);
            reservations.add(reservation);
            System.out.println("Votre réservation a aboutit ! ");
            System.out.println("Voici le prix de votre réservation : ");
            System.out.println(reservation.getMontant());
        }

    }

    public Ville trouverVille(String ville){
        Ville laVille;
        switch (ville.toUpperCase()){
            case "BORDEAUX":
                laVille =  Ville.BORDEAUX;
                break;
            case "PARIS":
                laVille = Ville.PARIS;
                break;
            case "DELHI":
                laVille = Ville.DELHI;
                break;
            case "CANBERRA":
                laVille = Ville.CANBERRA;
                break;
            case "TOKYO":
                laVille = Ville.TOKYO;
                break;
            default:
                throw new IllegalStateException("Cette ville n'est pas présente dans notre agence : " + ville);
        }
        return laVille;
    }

    public Vol creerVol(Ville depart, Ville arrivee){
        ArrayList<String> dates = proposerDates(5);
        ArrayList<Hotel> hotels =  proposerHotel(5,arrivee);
        ArrayList<Voiture> voitures = proposerVoitures(5,arrivee);


        Vol vol = new Vol(depart,arrivee,dates,hotels,voitures);
        System.out.println("prix avant reduc : "+vol.getPrix());
        if(poolTickets > 0){
            vol.setPrixReduction();
            poolTickets--;
            System.out.println("prix apres reduc : "+vol.getPrix());
        }
        return vol;
    }

    public int getNbReservations(){
        return reservations.size();
    }
}
