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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        if(premierHotel != null && deuxiemeHotel != null)
            cnt += 2;
        if(premiereVoiture != null)
            cnt++;
        if(deuxiemeVoiture != null)
            cnt++;
        sb.append("ServiceHauteGamme" + cnt + "(");
        if (cnt>=2) {
            sb.append(premierHotel.toString());
            sb.append(deuxiemeHotel.toString());
        }
        if(cnt >= 3) {
            sb.append(premiereVoiture.toString());
        }
        if(cnt == 4) {
            sb.append(deuxiemeVoiture.toString());
        }

        sb.append(")");
        return sb.toString();
    }
}
