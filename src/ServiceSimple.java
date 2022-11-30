public class ServiceSimple extends Service{

    private Hotel hotel;
    private Voiture voiture;
    private float prix;

    public ServiceSimple(Hotel hotel, Voiture voiture){
        this.hotel = hotel;
        this.voiture = voiture;
    }

    public float getPrix() {
        prix = hotel.getPrix()+ voiture.getPrix();
        return prix;
    }
}
