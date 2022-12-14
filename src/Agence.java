import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// AGGREGATE
public class Agence {
    private String nom;
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private ArrayList<Vol> vols = new ArrayList<>();
    private boolean[][] tabOfDestination = new boolean[][]{
            {false,true,true,true,true},
            {true,false,false,true,true},
            {true,false,false,true,false},
            {false,true,true,false,false},
            {true,true,true,false,false}
    };
    
    public Agence(String nom){
        this.nom = nom;
    }

    public void listerDestinations(Ville depart){
        for (int i = 0; i < Ville.values().length; i++) {
            if(!Ville.values()[i].equals(depart))
                System.out.println(Ville.values()[i]);
        }
    }

    public void genererVols(){
        for(Ville depart : Ville.values()){
            for(Ville destination : Ville.values())
                if(!depart.equals(destination)) {
                    vols.add(new Vol(depart, destination,
                            proposerDates(5),
                            proposerHotel(5, destination),
                            proposerVoitures(5, destination),
                            5));
                }
        }
    }

    Vol choisirVol(Ville depart, Ville destination) throws Exception{
        for (Vol vol: vols) {
            if(vol.getDestination().equals(destination) && vol.getDepart().equals(depart))
                return vol;
        }
        {
            throw new Exception("Probleme : pas les bonnes villes");
        }
    }

    public Ville choisirEscale(Ville depart, Ville destination, boolean[][] tab) throws Exception {
        Scanner sc = new Scanner(System.in);
        Ville escale;
        int positionDepart = depart.ordinal();
        int positionDest = destination.ordinal();
        if (tab[positionDepart][positionDest] == true){
            System.out.println("Voyage sans escale");
            escale = destination;
        }

        else{
            System.out.println("Possibilité d'avoir une escale par : ");
            for (int i = 0; i < Ville.values().length; i++) {
                if(tab[positionDepart][i] == true){
                    if(tab[i][positionDepart] == true){
                        System.out.println(Ville.values()[i]);
                    }
                }
            }
            System.out.println("Choisissez une escale");
            String reponse = sc.next();
            escale = trouverVille(reponse);
        }
        return escale;
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

    public void ajouterReservation(){
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Quel est votre nom et votre prénom ?");
            String nom = sc.next();
            String prenom = sc.next();
            String id = UUID.randomUUID().toString();

            // creer le client
            Client client = new Client(id, nom, prenom);

            System.out.println("De quel Aéroport partez vous ?");
            String aeroDep = sc.next();
            Ville depart = trouverVille(aeroDep);
            System.out.println("Voici les destinations possibles :");
            listerDestinations(depart);
            System.out.println("Quelle est la destination ?");
            String aeroArr = sc.next();
            Ville destination = trouverVille(aeroArr);
            Ville escale = choisirEscale(depart, destination, tabOfDestination);
            Vol vol = creerVol(depart,destination,escale);
            System.out.println("Le prix pour ce vol est de " + vol.getPrix() + " euros");
            System.out.println("Voici les dates proposées pour cette destination :");
            System.out.println(vol.getDates());
            System.out.println("Quelle date choisissez vous ?");
            String date = sc.next();
            while (!vol.getDates().contains(date)){
                System.err.println("Erreur, cette date n'est pas disponible, veuillez saisir de nouveau :");
                date = sc.next();
            }
            System.out.println("Date validée");
            System.out.println("Desirez vous voyager en 1ere classe ? Oui ou Non");
            String premiereClasse = sc.next();
            boolean boolPremClasse = false;
            while (!premiereClasse.equals("Oui") && !premiereClasse.equals("Non")){
                System.err.println("Reponse invalide, veuillez saisir de nouveau :");
                premiereClasse = sc.next();
            }
            if (premiereClasse.equals("Oui"))
                boolPremClasse = true;
            System.out.println("Prenez vous la formule avec service ? Oui ou Non");
            String reponse = sc.next();
            while (!reponse.equals("Oui") && !reponse.equals("Non")){
                System.err.println("Reponse invalide, veuillez saisir de nouveau :");
                reponse = sc.next();
            }
            if (reponse.equals("Oui")) {
                System.out.println("Quel Service choisissez vous ?");
                System.out.println("1 - Service simple: l’hotel et ´eventuellement location de voiture mais dans un seul lieu");
                System.out.println("2 - Service de haute gamme: hotel et ´eventuellement une location de voiture pour rejoindre une\n" +
                        "autre destination puis hotel et voiture `a cette nouvelle destination");
                int service = sc.nextInt();
                while (service != 1 && service != 2){
                    System.err.println("Reponse invalide, veuillez saisir de nouveau :");
                    service = sc.nextInt();
                }
                if (service == 1) {
                    System.out.println("Quel Hotel choisissez vous ?");
                    vol.afficherListeHotels();
                    String hotelChoisi = sc.next();
                    Hotel monHotel = vol.choisirHotel(hotelChoisi);
                    System.out.println("Prenez vous une voiture ? Oui ou Non");
                    String reponseVoiture = sc.next();
                    while (!reponseVoiture.equals("Oui") && !reponseVoiture.equals("Non")){
                        System.err.println("Reponse invalide, veuillez saisir de nouveau :");
                        reponseVoiture = sc.next();
                    }
                    Service monService = new ServiceSimple(monHotel);

                    if (reponseVoiture.equals("Oui")) {
                        System.out.println("Quel Voiture choisissez vous ?");
                        vol.afficherListeVoitures();
                        String voitureChoisi = sc.next();
                        Voiture maVoiture = vol.choisirVoiture(voitureChoisi);
                        monService = new ServiceSimple(monHotel, maVoiture);
                    }
                    Reservation reservation = new Reservation(vol,date, client, monService, boolPremClasse,escale);
                    reservations.add(reservation);
                    System.out.println("Votre réservation a aboutit ! ");
                    System.out.println("Voici le prix de votre réservation : ");
                    System.out.println(reservation.getMontant());
                }
                if (service == 2) {
                    boolean boolVoit = false;
                    Voiture maPremiereVoiture = null;
                    System.out.println("Quel Hotel choisissez vous pour la premiere destination ?");
                    vol.afficherListeHotels();
                    String premierHotelChoisi = sc.next();
                    Hotel monPremierHotel = vol.choisirHotel(premierHotelChoisi);
                    System.out.println("Prenez vous une voiture pour rejoindre l'autre destination ? Oui ou Non");
                    String reponseVoiture = sc.next();
                    while (!reponseVoiture.equals("Oui") && !reponseVoiture.equals("Non")){
                        System.err.println("Reponse invalide, veuillez saisir de nouveau :");
                        reponseVoiture = sc.next();
                    }

                    if (reponseVoiture.equals("Oui")) {
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
                    while (!reponseVoiture.equals("Oui") && !reponseVoiture.equals("Non")){
                        System.err.println("Reponse invalide, veuillez saisir de nouveau :");
                        reponseVoiture = sc.next();
                    }

                    Service monService = new ServiceHauteGamme(monPremierHotel, monDeuxiemeHotel);
                    if (boolVoit)
                        monService = new ServiceHauteGamme(monPremierHotel, monDeuxiemeHotel, maPremiereVoiture);

                    if (reponseVoiture.equals("Oui")) {
                        System.out.println("Quel Voiture choisissez vous ?");
                        vol.afficherListeVoitures();
                        String voitureChoisi = sc.next();
                        Voiture maDeuxiemeVoiture = vol.choisirVoiture(voitureChoisi);
                        if (boolVoit)
                            monService = new ServiceHauteGamme(monPremierHotel, monDeuxiemeHotel, maPremiereVoiture, maDeuxiemeVoiture);
                        else
                            monService = new ServiceHauteGamme(monPremierHotel, monDeuxiemeHotel, maDeuxiemeVoiture);
                    }
                    Reservation reservation = new Reservation(vol,date, client, monService, boolPremClasse,escale);
                    reservations.add(reservation);
                    System.out.println("Votre réservation a aboutit ! ");
                    System.out.println("Voici le prix de votre réservation : ");
                    System.out.println(reservation.getMontant());
                }

            }
            if (reponse.equals("Non")) {
                Reservation reservation = new Reservation(vol,date, client, boolPremClasse,escale);
                reservations.add(reservation);
                System.out.println("Votre réservation a aboutit ! ");
                System.out.println("Voici le prix de votre réservation : ");
                System.out.println(reservation.getMontant());
            }
        }catch (InterruptedException ex){
            throw new IllegalStateException("caca");
        }

        catch (InputMismatchException ex) {
            throw new IllegalStateException("Erreur, cette saisie n'est pas valide: " + ex.toString());
        }
        catch (ArithmeticException ex){
            throw new IllegalStateException("Erreur, cette saisie n'est pas valide: " + ex.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ville trouverVille(String ville){
        Scanner sc = new Scanner(System.in);
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
                System.out.println("Cette ville n'est pas disponible, veuillez en choisir une autre : ");
                String nvVille = sc.next();
                return trouverVille(nvVille);

        }
        return laVille;
    }

    public Vol creerVol(Ville depart, Ville arrivee, Ville escale) throws Exception {
        Vol vol = choisirVol(depart,arrivee);
        if(escale != arrivee)
            vol.setPrixEscale();
        System.out.println("prix avant reduc : "+vol.getPrix()+"€");
        int myPoolTicket = vol.getPoolTicket();
        if(myPoolTicket > 0){
            System.out.println("Vous disposez d'une réduction de 20% sur le prix du vol");
            vol.setPrixReduction();
            vol.reducePoolTicket();
            System.out.println("prix apres reduc : "+vol.getPrix()+"€");
        }
        return vol;
    }

    public int getNbReservations(){
        return reservations.size();
    }

    public void listerReservations(){
        if (getNbReservations() > 0) {
            for (Reservation res : reservations) {
                afficherReservation(res);
            }
        }
        else
            System.out.println("Pas encore de reservation");
    }



    public void consulterReservation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez votre nom et votre prénom :");
        String nom = sc.next();
        String prenom = sc.next();
        boolean client = false;

        for (Reservation res:reservations) {
            if(res.getClient().getPrenom().equals(prenom) && res.getClient().getNom().equals(nom)) {
                client = true;
                afficherReservation(res);
            }
        }
        if(!client)
            System.out.println("Vous n'existez pas !!");


    }

    public void afficherReservation(Reservation res){
        System.out.println("##################");
        System.out.println("Client : ID : " + res.getClient().getId());
        System.out.println("Prenom : " + res.getClient().getPrenom());
        System.out.println("Nom : " + res.getClient().getNom());

        System.out.println(" ");

        System.out.println("Vol : Depart : "+res.getVol().getDepart());
        System.out.println("Destination : " + res.getVol().getDestination());
        if(!res.getEscale().equals(res.getVol().getDestination()))
            System.out.println("Escale : "+res.getEscale());
        System.out.println("Premiere classe : "+res.isPremiereClasse());
        System.out.println("Date : "+res.getDate());
        System.out.println("Prix : "+res.getMontant()+"€");

        System.out.println(" ");

        if(res.getService().typeService())
            System.out.println("Service : Service Simple");
        if(!res.getService().typeService())
            System.out.println("Service : Service de Haute Gamme");
        System.out.println("##################");
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append(this.nom + ";\n");

        // Reservations
        for (Reservation reservation : reservations) {
            sb.append(reservation.toString() + "\n");
        }

        // Separateur
        sb.append("|\n");

        // Vol
        for (Vol v : vols) {
            sb.append(v.toString()+ "\n");
        }

        sb.append("}\n");
        return sb.toString();
    }
}
