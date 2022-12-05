import java.util.ArrayList;
import java.util.Random;

public class Vol {

    //Aggregate
    private Ville depart;
    private Ville destination;
    private ArrayList<String> dates;
    private ArrayList<Hotel> hotels;
    private ArrayList<Voiture> voitures;
    private int prix;


    public Vol(Ville depart, Ville destination, ArrayList<String> dates,ArrayList<Hotel> hotels,ArrayList<Voiture> voitures) {
        this.depart = depart;
        this.destination = destination;
        this.dates = dates;
        this.hotels = hotels;
        this.voitures = voitures;
        this.prix = setPrixRandom();

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
        for (int i = 0; i < hotels.size(); i++) {
            if (nomHotel.equals(hotels.get(i).getNom().toString()))
                return hotels.get(i);
        }
        {
            throw new Exception("Aucun hotel ne correspond");
        }
    }

    public Voiture choisirVoiture(String nomVoiture) throws Exception{
        for (int i = 0; i < voitures.size(); i++) {
            if (nomVoiture.equals(voitures.get(i).getNom().toString())) {
                return voitures.get(i);
            }
        }
        {
            throw new Exception("Aucune voiture ne correspond");
        }
    }
}
