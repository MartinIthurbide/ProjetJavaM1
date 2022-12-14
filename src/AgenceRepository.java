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

    public int load() throws IOException {
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
            Agence a = new Agence(nom);
            
            // Reservations
            while(!(line = br.readLine()).equals("|")) {
                
                String id = line.substring(8,44);
                String[] splitedLine = line.split(",");
                for (String s : splitedLine) {
                    System.out.println(s);
                }

                String nomClient = splitedLine[1];
                String prenomClient = splitedLine[2];
                // Create Client
                Client c = new Client(id, nomClient, prenomClient);

                boolean premierClasse = false;
                if(splitedLine[3].equals("premiere"))
                    premierClasse = true;
                Ville depart = Ville.stringToVille(splitedLine[4]);
                Ville escale = Ville.stringToVille(splitedLine[5]);
                Ville destination = Ville.stringToVille(splitedLine[6]);

                String date = splitedLine[7];
                float prix = Float.valueOf(splitedLine[8]);
                // FOR SURE now maybe not if no service
                if(splitedLine.length > 9) {
                    if(splitedLine[9].contains("ServiceHauteGamme")) {
                        String type = splitedLine[9].substring(17, 18);
                        if(Integer.valueOf(type) >= 2) {
                            String nomPremierHotel = splitedLine[9].substring(26,splitedLine[9].length());
                            float prixPremierHotel = Float.valueOf(splitedLine[10]);
                            Ville villePremierHotel = Ville.stringToVille(splitedLine[11]);
                            boolean majorationPremierHotel = (splitedLine[12].substring(0,1).equals("O"))?true:false;

                            String nomSecondHotel = splitedLine[12].substring(9,splitedLine[12].length());
                            float prixSecondHotel = Float.valueOf(splitedLine[13]);
                            Ville villeSecondHotel = Ville.stringToVille(splitedLine[14]);
                            // System.out.println("VilleSecondHotel = " + villeSecondHotel);
                            boolean majorationSecondHotel = (splitedLine[15].substring(0,1).equals("O"))?true:false;
                        }
                        // AN ERROR WHERE THE FIRST CAR IS FOR THE SECOND HOTEL AND NOT FOR THE FIRST ONE so check villes
                        if(Integer.valueOf(type) >= 3) {
                            index = splitedLine[15].indexOf(":");
                            String nomPremiereVoiture = splitedLine[15].substring(index,splitedLine[15].length());
                            float prixPremiereVoiture = Float.valueOf(splitedLine[16]);
                            index = splitedLine[17].indexOf(")");
                            Ville villePremiereVoiture = Ville.stringToVille(splitedLine[17].substring(0,index));
                        }
                        if(Integer.valueOf(type) == 4) {
                            index = splitedLine[17].indexOf(":");
                            String nomSecondeVoiture = splitedLine[17].substring(index,splitedLine[17].length());
                            float prixSecondeVoiture = Float.valueOf(splitedLine[18]);
                            index = splitedLine[19].indexOf(")");
                            Ville villePremiereVoiture = Ville.stringToVille(splitedLine[19].substring(0,index));
                        }
                    }
                    else if (splitedLine[9].contains("ServiceSimple")) {
                        index = splitedLine[9].lastIndexOf(":");
                        String nomHotel = splitedLine[9].substring(index,splitedLine[9].length());
                        float prixHotel = Float.valueOf(splitedLine[10]);
                        Ville villeHotel = Ville.stringToVille(splitedLine[11]);
                        boolean majorationHotel = (splitedLine[12].substring(0,1).equals("O"))?true:false;
                        // CREATE HOTEL
                        if(!splitedLine[12].contains("]")) {
                            index = splitedLine[13].indexOf(":");
                            String nomVoiture = splitedLine[13].substring(index,splitedLine[13].length());
                            float prixVoiture = Float.valueOf(splitedLine[14]);
                            index = splitedLine[15].indexOf(")");
                            Ville villeVoiture = Ville.stringToVille(splitedLine[15].substring(0,index));
                        }
                    }
                }
            }
            // HANDLE VOLS
            System.out.println("line"  + line);
            // WE ARE AT |
            break;
        }

        br.close();
        fr.close();
        System.out.println("Récupération terminée");
        return 0;
    }
}
