package implementations;

import com.google.gson.Gson;
import model.Configuration;
import model.LkSkladiste;
import model.Pair;

import java.io.*;

public class ConfigUtiles {
    private Gson gson;

    public ConfigUtiles(){
        this.gson=new Gson();

    }


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
            System.out.println("Neuspesno ispisivanje "+ filepath);
            return  -1;
        }


        return  1;
    }

    public Pair readConfig(String koren) {
        Configuration config;
        File cfile=new File(koren +"\\"+LkSkladiste.getCfile());

        BufferedReader br=null;
        try {
             br= new BufferedReader(new FileReader(cfile));
            config=gson.fromJson(br,Configuration.class);
            //U slucaju da je prazan file
            if(config == null){
                config=new Configuration();
            }
            br.close();
        } catch (FileNotFoundException e) {
            return  new Pair(-1,null);
        } catch (IOException e) {
           // e.printStackTrace();
            return  new Pair(-1,null);
        }


        return new Pair(1,config);
    }
}
