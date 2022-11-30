public class Hotel {
    private NomHotel nom;
    private float prix;
    private Ville ville;
    private boolean majoration;

    public Hotel(NomHotel nom, float prix,Ville ville, boolean majoration) {
        this.nom = nom;
        this.prix = prix;
        this.ville = ville;
        this.majoration = majoration;
    }

    public float getPrix() {
        if (majoration == true)
            return (float) (prix*1.20);
        return prix;
    }

    public NomHotel getNom() {
        return nom;
    }

    public Ville getVille() {
        return ville;
    }
}
