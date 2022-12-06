public class Reservation {
    private Vol vol;
    private Client client;
    private Service service;
    private boolean premiereClasse;
    private float montant;

    public Reservation(Vol vol, Client client, boolean premiereClasse){ //TODO: Rajouter la date du vol choisi
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

    public Vol getVol() {
        return vol;
    }

    public Client getClient() {
        return client;
    }

    public Service getService() {
        return service;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        //TODO:Completer
        sb.append("]");
        return sb.toString();
    }
}
