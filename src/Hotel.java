public class Hotel {
    private final NomHotel nom;
    private final float prix;
    private final Ville ville;
    private final boolean majoration;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(Hotel:" + nom + ";" + prix + ";" + ville.toString() + ";");
        if(majoration) {
            sb.append("O");
        }
        else {
            sb.append("N");
        }
        sb.append(")");
        return sb.toString();
    }
}
