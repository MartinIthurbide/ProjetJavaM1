public class Client {
    private final String id;
    private final String nom;
    private final String prenom;

    public Client(String id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        String s = "Client:" + getId() + "," + getNom() + "," + getPrenom(); 
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        Client c = (Client) obj;
        boolean e = (getId() == c.getId() && getNom() == c.getNom() && getPrenom() == c.getPrenom());
        return e;
    }
}
