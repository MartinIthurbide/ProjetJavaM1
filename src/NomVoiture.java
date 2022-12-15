public enum NomVoiture {
    FordFocus,
    VolkswagenGolf,
    ToyotaCorolla,
    NissanAltima,
    HondaCivic,
    HyundaiElantra,
    ChevroletImpala,
    KiaOptima,
    Mazda3,
    SubaruOutback;

    public static NomVoiture stringToNomVoiture(String s) {
        String normalizedS = s.toUpperCase();
        switch (normalizedS) {
            case "FORDFOCUS":
                return FordFocus;
            case "VOLKSWAGENGOLF":
                return VolkswagenGolf;
            case "TOYOTACOROLLA":
                return ToyotaCorolla;
            case "NISSANALTIMA":
                return NissanAltima;
            case "HONDACIVIC":
                return HondaCivic;
            case "HYUNDAIELANTRA":
                return HyundaiElantra;
            case "CHEVROLETIMPALA":
                return ChevroletImpala;
            case "KIAOPTIMA":
                return KiaOptima;
            case "MAZDA3":
                return Mazda3;
            case "SUBARUOUTBACK":
                return SubaruOutback;          
            default:
                return null;
        }
    }
}
