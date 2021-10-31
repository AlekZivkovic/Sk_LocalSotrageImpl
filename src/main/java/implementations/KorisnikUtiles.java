package implementations;

import com.google.gson.*;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class KorisnikUtiles {
    private Gson gson;

    public KorisnikUtiles() {
        gson=new Gson();
    }





    public  boolean autetifikacija(String user, String pass,String koren) {
        Gson gson=new Gson();
        File fuser=new File(koren+"/users.json");
        if(!fuser.exists())return  false;

        List<LKorisnik> klist=readUser(fuser.getPath());
        JsonArray lk=new JsonArray();

        if(!klist.contains(new LKorisnik(user,pass))){
            return  false;
        }

        return true;
    }


    public boolean makeKorisnik(String user, String pass, List<Privilegije> privil,String filepath) {
        LKorisnik korisnik=new LKorisnik();
        korisnik.setPassword(pass);
        korisnik.setUser(user);
        korisnik.setPrivl(privil);

        if(writeUsers(filepath,korisnik) == 1){
            return true;
        }

        return  false;
    }
    ///potrebno test
    public boolean delKornisk(String user, String pass, String filepath) {
        List<LKorisnik> lk=readUser(filepath);
        ListIterator iterator=lk.listIterator();

        writeUsers(filepath,null);
        boolean flag=false;
        while (iterator.hasNext()){
            LKorisnik ktren= (LKorisnik) iterator.next();

            if(ktren.getUser().equals(user) && ktren.getPassword().equals(pass)){
                iterator.remove();
                flag=true;
                continue;
            }
            writeUsers(filepath,ktren);
        }




        return  false;
    }
    public List<Privilegije> readPrivil(String user, String pass,String filepath){
        List<Privilegije> privl=new ArrayList<>();

        List<LKorisnik> lk=readUser(filepath);
        for(LKorisnik kor: lk){
            if(kor.getUser().equals(user) && kor.getPassword().equals(pass)){
                privl=kor.getPrivl();
                break;
            }
        }


        return  privl;
    }

    /**
     * @apiNote Ukoliko je var user null onda pravi novi jason file,u suprotnom append na postojeci json file
     *  dodati da hvata kolekciju an ne jednog samo
     * @return -1 ako je fail, -2 ako vec user postoji
     * @return  1 ako je gud
     * */
    public int writeUsers(String filepath, LKorisnik user) {

        Writer writer;

        //System.out.println("user je "+ user);
        if(user != null){
            List<LKorisnik> lk=readUser(filepath);
            if(lk.contains(user)){
                return -2;
            }else{
                lk.add(user);
            }
            try {
                writer = new FileWriter(filepath, true);
                gson.toJson(lk,writer);
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

    private List<LKorisnik> readUser(String filepath){
        List<LKorisnik> lk=new ArrayList<>();
        LKorisnik[] ak;
        File fuser=new File(filepath);

        BufferedReader br;
        try {
            br= new BufferedReader(new FileReader(fuser));
            ak=gson.fromJson(br,LKorisnik[].class);
            br.close();

            for(int i=0;i<ak.length;i++){
                lk.add(ak[i]);
            }


        } catch (FileNotFoundException e) {
            System.out.println("Nastao problem pri ucitavanju userfile: KU:readUser");
            return  null;
        } catch (IOException e) {
            System.out.println("Nastao problem pri ucitavanju userfile: KU:readUser");
            // e.printStackTrace();
            return  null;
        }

        return lk;
    }
}
