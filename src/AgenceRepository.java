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
        System.out.println("Donner un nom à votre sauvegarde :");
        String saveName = b.readLine();
        File f = new File("../save/" + saveName + ".mysave");
        if(f.exists()) {
            System.out.println("This name already exists for the save, delete old save or change name");
            return -1;
        }

        boolean created = f.createNewFile();
        if(!created) {
            System.err.println("Error during file creation");
            return -1;
        }
        System.out.println("File --> " + f.getName() + " has been created");
        FileWriter fw = new FileWriter(f.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(agences.size() + "\n");
        for (Agence agence : agences) {
            bw.write(agence.toString());
        }
        bw.close();
        fw.close();
        System.out.println("Save complete !");
        return 0;
    }

    public int load() throws IOException {
        // Invite and creation
        System.out.println("Type the name of the file you want to load :");
        String fileToLoad = b.readLine();

        File f = new File("../save/" + fileToLoad);

        // File doesn't exist
        if(!f.exists()) {
            System.err.println("This file doesn't exist");
        }

        // Invalid name or doesn't have right to read
        if(!f.getName().endsWith(".mysave") || !f.canRead()) {
            System.err.println("This file cannot be loaded. The file name needs to end with .mysave or you may not have read rights");
        }

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        if(br == null ||fr == null) {
            System.err.println("Allocation failed");
        }

        // LOAD PART
        int nbAgences = 0;
        String line = br.readLine();
        nbAgences = Integer.parseInt(line);

        //TODO:next

        return 0;
    }
}
