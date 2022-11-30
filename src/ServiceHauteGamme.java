public class ServiceHauteGamme extends Service{
    private Hotel premierHotel,deuxiemeHotel;
    private Voiture premiereVoiture,deuxiemeVoiture;
    private float prix;


    @Override
    public float getPrix() {
        prix = premierHotel.getPrix()+deuxiemeHotel.getPrix()+ premiereVoiture.getPrix()+ deuxiemeVoiture.getPrix();
        return prix;
    }
}
