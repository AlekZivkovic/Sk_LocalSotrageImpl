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
        try {
            gson.toJson(config,new FileWriter(filepath));
            //System.out.println("upisao na "+ filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  1;
    }

    public Pair readConfig(String koren) {
        Configuration config;
        File cfile=new File(koren +"\\"+LkSkladiste.getCfile());

        try {
            BufferedReader br= new BufferedReader(new FileReader(cfile));
            config=gson.fromJson(br,Configuration.class);

        } catch (FileNotFoundException e) {
            return  new Pair(-1,null);
        }


        return new Pair(1,config);
    }
}
