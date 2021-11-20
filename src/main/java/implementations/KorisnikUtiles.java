package implementations;

import com.google.gson.*;
import model.*;

import java.io.*;
import java.util.*;

public class KorisnikUtiles {
    private final Gson gson;

    public KorisnikUtiles() {
        gson=new Gson();
    }


    public  boolean autetifikacija(String user, String pass,String koren) {
        File fuser=new File(koren+File.separator+LkSkladiste.getUfile());
        if(!fuser.exists())return  false;

        List<LKorisnik> klist=readUser(fuser.getPath());

        return klist.contains(new LKorisnik(user, pass));
    }


    public boolean makeKorisnik(String user, String pass, Map<String, List<Privilegije>> privil, String filepath, boolean admin) {
        LKorisnik korisnik=new LKorisnik();
        korisnik.setPassword(pass);
        korisnik.setUser(user);
        korisnik.setPrivl(privil);
        korisnik.setAdmin(admin);
        return writeUsers(filepath, korisnik) == 1;
    }
    ///potrebno test
    public boolean delKornisk(String user, String pass, String filepath) {
        List<LKorisnik> lk=readUser(filepath);
        ListIterator<LKorisnik> iterator=lk.listIterator();

        writeUsers(filepath,null);
        boolean flag=false;
        while (iterator.hasNext()){
            LKorisnik ktren=iterator.next();

            if(ktren.getUser().equals(user) && ktren.getPassword().equals(pass)){
                iterator.remove();
                flag=true;
                continue;
            }
            writeUsers(filepath,ktren);
        }

        return flag;
    }
    public Pair readPrivil(String user, String pass, String filepath){
        Map<String,List<Privilegije>> privl;
        Pair p = null;
        List<LKorisnik> lk=readUser(filepath);
        for(LKorisnik kor: lk){
            if(kor.getUser().equals(user) && kor.getPassword().equals(pass)){
                privl=kor.getPrivl();
                p=new Pair(kor.getAdmin(), privl);

                break;
            }
        }


        return  p;
    }

    /**
     * @apiNote Ukoliko je var user null onda pravi novi jason file,u suprotnom append na postojeci json file
     *  dodati da hvata kolekciju an ne jednog samo
     * @return -1 ako je fail, -2 ako vec user postoji, 1 ako je gud
     *
     * */
    public int writeUsers(String filepath, LKorisnik user) {

        Writer writer;

        //System.out.println("user je "+ user);
        if(user != null){
            List<LKorisnik> lk=readUser(filepath);
            if(lk.contains(user)){
                //System.out.println("POSTOJI VEC USER");
                return -2;
            }else{
                lk.add(user);
            }
            try {
                writer = new FileWriter(filepath);
                gson.toJson(lk,writer);
               // System.out.println("upisao sam korniska "+ user.getUser());
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
            if(ak!= null )
                Collections.addAll(lk, ak);


        } catch (IOException e) {
            System.out.println("Nastao problem pri ucitavanju userfile: KU:readUser");
            return  new ArrayList<>();
        }

        return lk;
    }

    public Korisnik getKorisnik(String user, String koren) {
        File fuser=new File(koren+File.separator+LkSkladiste.getUfile());
        List<LKorisnik> klist=readUser(fuser.getPath());

        for (LKorisnik lk: klist){
            if(lk.getUser().equals(user)){
                Korisnik kor=new Korisnik();
                kor.setUsername(user);
                kor.setPrivil(lk.getPrivl());
                kor.setAdmin(lk.getAdmin());
                return kor;
            }
        }

        return null;
    }

    public int updateUsers(Korisnik pKor, String koren){
        File fuser=new File(koren+File.separator+LkSkladiste.getUfile());
        List<LKorisnik> klist=readUser(fuser.getPath());

        for (LKorisnik lk: klist){
            if(lk.getUser().equals(pKor.getUsername())){
                lk.nadodajPrivil(pKor.getPrivil());
                break;
            }
        }
        String  filepath=fuser.getPath();
        Writer writer;
        try {
            writer = new FileWriter(filepath);
            gson.toJson(klist,writer);
            //System.out.println("upisao sam korniska ");
            writer.close();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Neuspesno ispisivanje usera "+ filepath);
            return  -1;
        }


        return 1;
    }
}
