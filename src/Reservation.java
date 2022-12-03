public class Reservation {
    private Vol vol;
    private Client client;
    private Service service;
    private boolean premiereClasse;
    private float montant;

    public Reservation(Vol vol, Client client, boolean premiereClasse){
        this.vol = vol;
        this.client = client;
        this.premiereClasse = premiereClasse;
        if (this.premiereClasse == true){
            this.montant = (float) ((vol.getPrix()*1.30));
        }
        else
            this.montant = vol.getPrix();
    }

    public Reservation(Vol vol, Client client, Service service,boolean premiereClasse) {
        this.vol = vol;
        this.client = client;
        this.service = service;
        this.premiereClasse = premiereClasse;
        if (this.premiereClasse == true){
            this.montant = (float) ((vol.getPrix()*1.30)+ service.getPrix());
        }
        else
            this.montant = vol.getPrix()+ service.getPrix();
    }

    public float getMontant() {
        return montant;
    }

}
