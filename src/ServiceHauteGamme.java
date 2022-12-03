public class ServiceHauteGamme extends Service{
    private Hotel premierHotel,deuxiemeHotel;
    private Voiture premiereVoiture,deuxiemeVoiture;
    private float prix;

    public ServiceHauteGamme(Hotel premierHotel, Hotel deuxiemeHotel){
        this.premierHotel = premierHotel;
        this.deuxiemeHotel = deuxiemeHotel;
    }

    public ServiceHauteGamme(Hotel premierHotel, Hotel deuxiemeHotel,Voiture premiereVoiture){
        this.premierHotel = premierHotel;
        this.deuxiemeHotel = deuxiemeHotel;
        this.premiereVoiture = premiereVoiture;
    }

    public ServiceHauteGamme(Hotel premierHotel, Hotel deuxiemeHotel,Voiture premiereVoiture,Voiture deuxiemeVoiture){
        this.premierHotel = premierHotel;
        this.deuxiemeHotel = deuxiemeHotel;
        this.premiereVoiture = premiereVoiture;
        this.deuxiemeVoiture = deuxiemeVoiture;
    }

    @Override
    public float getPrix() {
        prix = premierHotel.getPrix()+deuxiemeHotel.getPrix();
        if(premiereVoiture != null)
            prix += premiereVoiture.getPrix();
        if((premiereVoiture != null) && (deuxiemeVoiture != null))
            prix += premiereVoiture.getPrix()+ deuxiemeVoiture.getPrix();
        if(deuxiemeVoiture != null)
            prix += deuxiemeVoiture.getPrix();;
        return prix;
    }
}
