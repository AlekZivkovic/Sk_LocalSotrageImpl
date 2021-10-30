package implementations;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import model.Korisnik;
import model.LKorisnik;
import model.Privilegije;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class KorisnikUtiles {


    public KorisnikUtiles() {
    }


    public boolean delKornisk(String s, String s1) {
        return  false;
    }

    public boolean makeKorisnik(String user, String pass, List<Privilegije> privil) {
        return false;
    }

    public List<Privilegije> readPrivil(String user, String pass){

        return  null;
    }

    public boolean autetifikacija(String user, String pass) {
        return false;
    }

    public int writeUsers(String filepath, LKorisnik user) {
        Gson gson=new Gson();
        Writer writer;
        //System.out.println("user je "+ user);
        if(user != null){
            try {
                writer = new FileWriter(filepath, true);
                gson.toJson(user,writer);
                //System.out.println("upisao sam korniska ");
                writer.close();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Neuspesno ispisivanje usera "+ filepath);
                return  -1;
            }

        }else{
            try {
                writer=new FileWriter(filepath);
                // System.out.println("upisao na "+ filepath);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Neuspesno ispisivanje userfile "+ filepath);
                return  -1;
            }
        }


        return  1;
    }
}
