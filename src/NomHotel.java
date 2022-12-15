public enum NomHotel {
    Mercure,
    VoileBlanche,
    CapHotel,
    AlliaPark,
    Splendid,
    Grandeur,
    Grandview,
    Royalty,
    Regal,
    Oasis,
    Havenwood,
    Reflection;

    public static NomHotel stringToNomHotel(String s) {
        String normalizedS = s.toUpperCase();
        switch (normalizedS) {
            case "MERCURE":
                return Mercure;
            case "VOILEBLANCHE":
                return VoileBlanche;
            case "CAPHOTEL":
                return CapHotel;
            case "ALLIAPARK":
                return AlliaPark;
            case "SPLENDID":
                return Splendid;
            case "GRANDEUR":
                return Grandeur;
            case "GRANDVIEW":
                return Grandview;
            case "ROYALTY":
                return Royalty;
            case "REGAL":
                return Regal;
            case "OASIS":
                return Oasis;          
            case "HAVENWOOD":
                return Havenwood;
            case "REFLECTION":
                return Reflection;
            default:
                return null;
        }
    }
}

