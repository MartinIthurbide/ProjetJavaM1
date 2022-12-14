public enum Ville {
    PARIS,
    BORDEAUX,
    CANBERRA,
    TOKYO,
    DELHI;

    public static Ville stringToVille(String s) {
        String normalizedS = s.toUpperCase();
        switch (normalizedS) {
            case "BORDEAUX":
                return BORDEAUX;
            case "PARIS":
                return PARIS;
            case "CANBERRA":
                return CANBERRA;
            case "TOKYO":
                return TOKYO;
            case "DELHI":
                return DELHI;
            default:
                return null;
        }
    }
}
