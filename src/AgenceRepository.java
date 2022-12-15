import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AgenceRepository {
    private ArrayList<Agence> agences;
    private static BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

    public AgenceRepository() {
        agences = new ArrayList<>();
    }
    
    public void addAgence(Agence a) {
        agences.add(a);
    }

    public int save() throws IOException {
        File current = new File("../save");
        boolean folderCreated = current.mkdir();
        if(folderCreated) {
            System.out.println("Dossier de sauvegarde crée");
        }
        System.out.println("Donner un nom à votre sauvegarde :");
        String saveName = b.readLine();
        File f = new File("../save/" + saveName + ".mysave");
        if(f.exists()) {
            System.out.println("Ce nom de sauvegarde existe déjà, veuillez changer ou supprimer l'ancienne sauvegarde !");
            return -1;
        }

        boolean created = f.createNewFile();
        if(!created) {
            System.err.println("Erreur pendant la création du fichier");
            return -1;
        }
        System.out.println("Le fichier --> " + f.getName() + " a bien été crée");
        FileWriter fw = new FileWriter(f.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(agences.size() + "\n");
        for (Agence agence : agences) {
            bw.write(agence.toString());
        }
        bw.close();
        fw.close();
        System.out.println("Sauvegarde terminée !");
        return 0;
    }

    public Agence load() throws IOException {
        // Invite and creation
        System.out.println("Ecris le nom de la sauvegarde que vous voulez récuperer :");
        String fileToLoad = b.readLine();

        File f = new File("../save/" + fileToLoad);

        // File doesn't exist
        if(!f.exists()) {
            System.err.println("Ce fichier de sauvegarde n'existe pas");
        }

        // Invalid name or doesn't have right to read
        if(!f.getName().endsWith(".mysave") || !f.canRead()) {
            System.err.println("Ce fichier ne peut pas être récupéré. Le fichier doit finir par .mysave ou vous n'avez peut être pas les droits de lecture sur ce fichier");
        }

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        if(br == null ||fr == null) {
            System.err.println("Erreur durant l'allocation");
        }

        // LOAD PART
        int nbAgences = 0;
        String line = br.readLine();
        nbAgences = Integer.parseInt(line);

        Agence a = null;
        //TODO:next
        int currentAg = 0;
        loop : while((line = br.readLine()) != null) {
            if(line.equals("}")) {
                currentAg++;
                //TODO: GO NEXT agence
                continue loop; 
            }


            // nom
            int index;
            index = line.indexOf(';');
            String nom = line.substring(1, index);

            // Create Agence
            a = new Agence(nom);
            
            // Vols
            while(!(line = br.readLine()).equals("|")) {
                String[] splitedVol = line.split(",");
                index = splitedVol[0].indexOf(":");
                System.out.println("DEBUG VILLE " + splitedVol[0].substring(index+1, splitedVol[0].length()));
                Ville depart = Ville.stringToVille(splitedVol[0].substring(index+1, splitedVol[0].length()));
                
                Ville destination = Ville.stringToVille(splitedVol[1]); 

                int poolTickets = Integer.parseInt(splitedVol[2]);

                int prix = Integer.parseInt(splitedVol[3]);

                String[] dates = splitedVol[4].split("#");
                ArrayList<String> datesArray = new ArrayList<>();
                for (int i = 0; i < dates.length; i++) {
                    datesArray.add(dates[i]);
                }

                ArrayList<Hotel> hotels = new ArrayList<>();

                String[] hotelsString = splitedVol[5].split("#");
                // System.out.println(splitedVol[5]);
                for (String h : hotelsString) {
                    String[] hotel = h.split(";");
                    index = hotel[0].indexOf(':');
                    String nomHotel = hotel[0].substring(index+1,hotel[0].length());
                    Float prixHotel = Float.parseFloat(hotel[1]);
                    Ville villeHotel = Ville.stringToVille(hotel[2]);
                    boolean majoration = hotel[3].substring(0,1) == "O"?true:false;

                    hotels.add(new Hotel(NomHotel.stringToNomHotel(nomHotel),prixHotel,villeHotel,majoration));
                }

                // VOITURES
                ArrayList<Voiture> voitures = new ArrayList<>();

                String[] voituresString = splitedVol[6].split("#");

                for (String h : voituresString) {
                    String[] voiture = h.split(";");
                    index = voiture[0].indexOf(':');
                    String nomVoiture = voiture[0].substring(index+1,voiture[0].length());
                    Float prixVoiture = Float.parseFloat(voiture[1]);
                    index = voiture[2].indexOf(')');
                    Ville villeVoiture = Ville.stringToVille(voiture[2].substring(0,index));

                    voitures.add(new Voiture(NomVoiture.stringToNomVoiture(nomVoiture),prixVoiture,villeVoiture));
                }

                a.creerVolFile(depart, destination, poolTickets, hotels, voitures, datesArray);
                
                // Creer nouvelle fonction pour creerUnVol en spécifiant tous les paramètres
            }

            a.listerVol();

            // Reservations
            while(!(line = br.readLine()).equals("}")) {
                String id = line.substring(8,44);
                String[] splitedLine = line.split(",");int i = 0;
                // for (String s : splitedLine) {
                //     System.out.println(i++ + " --> " + s);
                // }

                String nomClient = splitedLine[1];
                String prenomClient = splitedLine[2];
                // Create Client
                Client c = new Client(id, nomClient, prenomClient);

                boolean premierClasse = false;
                if(splitedLine[3].equals("premiere"))
                    premierClasse = true;
                Ville departVol = Ville.stringToVille(splitedLine[4]);
                Ville escaleVol = Ville.stringToVille(splitedLine[5]);
                Ville destinationVol = Ville.stringToVille(splitedLine[6]);


                String date = splitedLine[7];
                float prix = Float.valueOf(splitedLine[8]);
                // FOR SURE now maybe not if no service
                Service monService = null;
                if(splitedLine.length > 9) {
                    Hotel hotel1 = null;
                    Hotel hotel2 = null;
                    Voiture voiture1 = null;
                    Voiture voiture2 = null;
                    if(splitedLine[9].contains("ServiceHauteGamme")) {
                        String type = splitedLine[9].substring(17, 18);
                        String[] s = splitedLine[9].split("#");
                        if(Integer.valueOf(type) >= 2) {
                            String[] h = s[0].split(";");
                            String nomPremierHotel = h[0].substring(26,h[0].length());
                            float prixPremierHotel = Float.valueOf(h[1]);
                            Ville villePremierHotel = Ville.stringToVille(h[2]);
                            boolean majorationPremierHotel = (splitedLine[3].substring(0,1).equals("O"))?true:false;
                            hotel1 = new Hotel(NomHotel.stringToNomHotel(nomPremierHotel),prixPremierHotel,villePremierHotel,majorationPremierHotel);
                        
                            String[] h2 = s[1].split(";");
                            String nomSecondHotel = h2[0].substring(7,h2[0].length());
                            float prixSecondHotel = Float.valueOf(h2[1]);
                            Ville villeSecondHotel = Ville.stringToVille(h2[2]);
                            // System.out.println("VilleSecondHotel = " + villeSecondHotel);
                            boolean majorationSecondHotel = (h2[3].substring(0,1).equals("O"))?true:false;
                            hotel2 = new Hotel(NomHotel.stringToNomHotel(nomSecondHotel),prixSecondHotel,villeSecondHotel,majorationSecondHotel);
                            
                            monService = new ServiceHauteGamme(hotel1, hotel2);
                        }
                        // AN ERROR WHERE THE FIRST CAR IS FOR THE SECOND HOTEL AND NOT FOR THE FIRST ONE so check villes
                        if(Integer.valueOf(type) >= 3) {
                            String[] v = s[2].split(";");
                            index = v[0].indexOf(":");
                            String nomPremiereVoiture = v[0].substring(index,v[0].length());
                            float prixPremiereVoiture = Float.valueOf(v[1]);
                            index = v[2].indexOf(")");
                            Ville villePremiereVoiture = Ville.stringToVille(v[2].substring(0,index));
                            voiture1 = new Voiture(NomVoiture.stringToNomVoiture(nomPremiereVoiture), prixPremiereVoiture, villePremiereVoiture);
                            monService = new ServiceHauteGamme(hotel1, hotel2, voiture1);
                        }
                        if(Integer.valueOf(type) == 4) {
                            String[] v2 = s[3].split(";");
                            index = v2[0].indexOf(":");
                            String nomSecondeVoiture = v2[0].substring(index,v2[0].length());
                            float prixSecondeVoiture = Float.valueOf(v2[1]);
                            index = v2[2].indexOf(")");
                            Ville villeSecondeVoiture = Ville.stringToVille(v2[2].substring(0,index));
                            voiture2 = new Voiture(NomVoiture.stringToNomVoiture(nomSecondeVoiture),prixSecondeVoiture,villeSecondeVoiture);
                            monService = new ServiceHauteGamme(hotel1, hotel2, voiture1,voiture2);
                        }
                    }
                    else if (splitedLine[9].contains("ServiceSimple")) {
                        String[] h = splitedLine[9].split(";");
                        index = h[0].lastIndexOf(":");
                        String nomHotel = splitedLine[9].substring(index,h[0].length());
                        float prixHotel = Float.valueOf(h[1]);
                        Ville villeHotel = Ville.stringToVille(h[2]);
                        boolean majorationHotel = (h[3].substring(0,1).equals("O"))?true:false;
                        Hotel hotel = new Hotel(NomHotel.stringToNomHotel(nomHotel),prixHotel,villeHotel,majorationHotel);
                        monService = new ServiceSimple(hotel);
                        // CREATE HOTEL
                        if(!splitedLine[9].contains("]")) {
                            String[] v = splitedLine[10].split(";");
                            index = v[0].indexOf(":");
                            String nomVoiture = v[0].substring(index,v[0].length());
                            float prixVoiture = Float.valueOf(v[1]);
                            index = v[2].indexOf(")");
                            Ville villeVoiture = Ville.stringToVille(v[2].substring(0,index));
                            Voiture voiture = new Voiture(NomVoiture.stringToNomVoiture(nomVoiture),prixVoiture,villeVoiture);
                            monService = new ServiceSimple(hotel, voiture);
                        }
                    }
                }
                try {
                    Vol vol = a.creerVol(departVol,destinationVol,escaleVol);
                    Reservation r = new Reservation(vol, date, c, monService, premierClasse, escaleVol);
                    a.addReservation(r);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            // HANDLE VOLS
            // System.out.println("line"  + line);
            // WE ARE AT |
            break;
        }

        br.close();
        fr.close();
        System.out.println("Récupération terminée");
        return a;
    }
}
