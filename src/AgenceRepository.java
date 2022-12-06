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

            
            // Reservations
            while(!(line = br.readLine()).equals("|")) {
                
            }
        }

        br.close();
        fr.close();
        System.out.println("Récupération terminée");
        return 0;
    }
}
