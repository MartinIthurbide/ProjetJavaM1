public class ServiceSimple extends Service{

    private Hotel hotel;
    private Voiture voiture;
    private float prix;

    public ServiceSimple(Hotel hotel, Voiture voiture){
        this.hotel = hotel;
        this.voiture = voiture;
    }

    public ServiceSimple(Hotel hotel){
        this.hotel = hotel;
    }

    public float getPrix() {
        prix = hotel.getPrix();
        if(voiture != null){
            prix += voiture.getPrix();
        }
        return prix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServiceSimple:" + hotel.toString());
        if(voiture != null) {
            sb.append("," + voiture.toString());
        }
        return sb.toString();
    }
}
