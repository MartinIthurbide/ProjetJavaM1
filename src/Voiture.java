public class Voiture {
    private NomVoiture nom;
    private float prix;
    private Ville ville;

    public Voiture(NomVoiture nom, float prix, Ville ville) {
        this.nom = nom;
        this.prix = prix;
        this.ville = ville;
    }

    public float getPrix() {
        return prix;
    }

    public Ville getVille() {
        return ville;
    }

    public NomVoiture getNom() {
        return nom;
    }
}
