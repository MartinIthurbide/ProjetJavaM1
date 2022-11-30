public class Reservation {
    private Vol vol;
    private Client client;
    private Service service;
    private boolean premiereClasse;
    private float montant;

    public Reservation(Vol vol, Client client, Service service) {
        this.vol = vol;
        this.client = client;
        this.service = service;
        if (isPremiereClasse())
            this.montant = (float) ((vol.getPrix()*1.30)+ service.getPrix());
        else
            this.montant = vol.getPrix()+ service.getPrix();
    }

    public float getMontant() {
        return montant;
    }

    public boolean isPremiereClasse() {
        return premiereClasse;
    }
}
