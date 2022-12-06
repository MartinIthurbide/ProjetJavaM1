import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Vol {

    //Aggregate
    private Ville depart;
    private Ville destination;
    private ArrayList<String> dates;
    private ArrayList<Hotel> hotels;
    private ArrayList<Voiture> voitures;
    private int prix;
    private int poolTicket;


    public Vol(Ville depart, Ville destination, ArrayList<String> dates,ArrayList<Hotel> hotels,ArrayList<Voiture> voitures, int poolTicket) {
        this.depart = depart;
        this.destination = destination;
        this.dates = dates;
        this.hotels = hotels;
        this.voitures = voitures;
        this.prix = setPrixRandom();
        this.poolTicket = poolTicket;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrixReduction(){
        prix = (int) (prix*0.80);
    }

    public int setPrixRandom(){
        Random r = new Random();
        int low = 50;
        int high = 250;
        return r.nextInt(high-low) + low;
    }

    public void setPrixEscale(){
        prix += setPrixRandom()/2;
    }

    public Ville getDestination() {
        return destination;
    }

    public Ville getDepart() {return depart;}

    public ArrayList<String> getDates() {
        return dates;
    }

    public ArrayList<Hotel> getHotels() {
        return hotels;
    }

    public ArrayList<Voiture> getVoitures() {
        return voitures;
    }

    public void afficherListeHotels(){
        for (int i = 0; i < hotels.size(); i++) {
            // TODO: Majoration à ne pas oublier
            String hotel = "Nom : "+hotels.get(i).getNom()+",Prix : "+hotels.get(i).getPrix()+",Ville : "+hotels.get(i).getVille();
            System.out.println(hotel);
        }
    }

    public void afficherListeVoitures(){
        for (int i = 0; i < voitures.size(); i++) {
            // TODO: Majoration à ne pas oublier
            String voiture = "Nom : "+voitures.get(i).getNom()+",Prix : "+voitures.get(i).getPrix()+",Ville : "+voitures.get(i).getVille();
            System.out.println(voiture);
        }
    }

    public Hotel choisirHotel(String nomHotel) throws Exception{
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < hotels.size(); i++) {
            if (nomHotel.toUpperCase().equals(hotels.get(i).getNom().toString().toUpperCase()))
                return hotels.get(i);
        }
        {
            System.out.println("Pas d'hotel correspondant, veuillez ressaisir : ");
            String nvHotel = sc.next();
            return choisirHotel(nvHotel);
        }
    }

    public Voiture choisirVoiture(String nomVoiture) throws Exception{
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < voitures.size(); i++) {
            if (nomVoiture.equals(voitures.get(i).getNom().toString())) {
                return voitures.get(i);
            }
        }
        {
            System.out.println("Pas d'hotel correspondant, veuillez ressaisir : ");
            String nvVoiture = sc.next();
            return choisirVoiture(nvVoiture);
        }
    }

    public void reducePoolTicket() {
        this.poolTicket--;
    }

    public int getPoolTicket() {
        return poolTicket;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        sb.append("Vol:" + this.depart + "," + this.destination + ",");
        sb.append(this.poolTicket + "," + this.prix + ",");
        sb.append("[");
        for (String s : dates) {
            sb.append(s);
            if(dates.indexOf((String)s) != dates.size() - 1) {
                sb.append("#");
            } 
        }
        sb.append("],");

        sb.append("[");
        for (Hotel h : hotels) {
            sb.append(h.toString());
            if(hotels.indexOf((Hotel)h) != hotels.size() - 1) {
                sb.append("#");
            } 
        }
        sb.append("],");
        
        sb.append("[");
        for (Voiture v : voitures) {
            sb.append(v.toString());
            if(voitures.indexOf((Voiture)v) != voitures.size() - 1) {
                sb.append("#");
            } 
        }
        sb.append("]");

        return sb.toString();
    }
}
