public class Voiture {
    private final NomVoiture nom;
    private final float prix;
    private final Ville ville;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(Voiture:" + nom + ";" + prix + ";" + ville.toString() + ")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Voiture v = (Voiture) obj;
        boolean e = (getNom() == v.getNom() && getPrix() == v.getPrix() && getVille() == v.getVille());
        return e;
    }
}
