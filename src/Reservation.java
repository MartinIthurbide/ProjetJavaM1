public class Reservation {
    private Vol vol;
    private Client client;
    private String date;
    private Service service;
    private boolean premiereClasse;
    private Ville escale;
    private float montant;

    public Reservation(Vol vol,String date, Client client, boolean premiereClasse,Ville escale){
        this.vol = vol;
        this.date = date;
        this.client = client;
        this.escale = escale;
        this.premiereClasse = premiereClasse;
        if (this.premiereClasse == true){
            this.montant = (float) ((vol.getPrix()*1.30));
        }
        else
            this.montant = vol.getPrix();
    }

    public Reservation(Vol vol,String date, Client client, Service service,boolean premiereClasse,Ville escale) {
        this.vol = vol;
        this.date = date;
        this.client = client;
        this.service = service;
        this.escale = escale;
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

    public String getDate(){
        return date;
    }

    public boolean isPremiereClasse() {return premiereClasse;}

    public Ville getEscale() {return escale;}

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        //TODO:Reste Depart Dest Date Montant Service
        sb.append(this.getClient().toString() + ",");
        sb.append(this.getVol().getDepart().toString() + ",");
        sb.append(this.getVol().getDestination().toString() + ",");
        sb.append(this.getDate().toString() + ",");
        sb.append(this.getMontant() + ",");
        sb.append(this.getService().toString() + ",");

        sb.append("]");
        return sb.toString();
    }
}
