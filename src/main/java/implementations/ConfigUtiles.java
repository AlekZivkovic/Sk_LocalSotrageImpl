package implementations;

import com.google.gson.Gson;
import model.Configuration;
import model.LkSkladiste;
import model.Pair;

import java.io.*;

public class ConfigUtiles {
    private final Gson gson;

    public ConfigUtiles(){
        this.gson=new Gson();

    }

    /**
     *Funcija pise u config.json fajl.
     * Prosledjuje se putanja do file, kao i sama instanca config klase koja treba da se upise
     * @param  filepath putanaja do file
     * @param  config config klasa za upisavanje
     * @return  1 ukoliko se nije desio IOExcepiton u suprotnom -1
     */
    public int writeConfig(String filepath,Configuration config) {
        Writer writer;
        try {
            writer=new FileWriter(filepath);
            gson.toJson(config,writer);
           // System.out.println("upisao na "+ filepath);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Neuspesno ispisivanje configfile: CU: writeConfig");
            return  -1;
        }


        return  1;
    }

    public Pair readConfig(String koren) {
        Configuration config;
        File cfile=new File(koren +File.separator+LkSkladiste.getCfile());

        BufferedReader br;
        try {
             br= new BufferedReader(new FileReader(cfile));
            config=gson.fromJson(br,Configuration.class);
            //U slucaju da je prazan file
            if(config == null){
                config=new Configuration();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Neuspesno ucitavanj config.josn: CU: readConfig");
            return  new Pair(-1,null);
        }


        return new Pair(1,config);
    }
}
