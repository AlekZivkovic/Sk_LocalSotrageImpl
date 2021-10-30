package model;

import implementations.ConfigUtiles;
import implementations.FileUtiles;
import implementations.KorisnikUtiles;
import model.Pair;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LkSkladiste extends Skladiste {
    private static KorisnikUtiles kUtiles;
    private static ConfigUtiles cUtiles;
    private static FileUtiles fUtiles;
    private static  String cfile="config.json";
    private static  String ufile="users.json";


    static {
        kUtiles=new KorisnikUtiles();
        cUtiles=new ConfigUtiles();
        fUtiles=new FileUtiles();
    }

    public LkSkladiste(String path) {
        super(path);
    }

    protected boolean load(String filepath) {
         boolean korCheck,configCheck;
        configCheck=new File(filepath+"\\"+getCfile()).exists();
        korCheck= new File(filepath+"\\"+getUfile()).exists();
        return korCheck && configCheck;
    }

    public boolean inicializuj(String s) {
        return false;
    }

    protected String skGetFileDir(String s) {
        return null;
    }

    protected boolean skAddFile(String s, String s1) {
        return false;
    }

    protected boolean skDeleteFile(String s) {
        return false;
    }

    protected List<FAFile> skListSkaldiste() {
        return null;
    }

    protected boolean skMoveFile(String s, String s1) {
        return false;
    }

    protected boolean skDownloadFile(String s) {
        return false;
    }

    protected boolean skCreateFile(String s, String s1, boolean b) {
        return false;
    }

    public int readConfig() {
            Pair rc=cUtiles.readConfig(koren);
            if(rc.getValue() == null){
                System.out.println("Error occured with raeding Config file");
                return (int) rc.getKey();
            }
            this.configuration= (Configuration) rc.getValue();
            return  1;
    }

    public void writeConfig() { cUtiles.writeConfig(koren+"\\"+getCfile(),configuration); }

    public Map<String, Integer> readFiles() {
        return fUtiles.readFiles();
    }

    protected boolean skAuntetifikacija(String user, String pass) {
        return kUtiles.autetifikacija(user,pass);
    }

    protected List<Privilegije> skReadPrivil(String s, String s1) {
        return kUtiles.readPrivil(s,s1);
    }

    protected boolean skMakeKorisnik(String s, String s1, List<Privilegije> list) {
        return kUtiles.makeKorisnik(s,s1,list);
    }

    protected boolean skDelKorisnik(String s, String s1) {
        return kUtiles.delKornisk(s,s1);
    }

    public static String getCfile() {
        return cfile;
    }

    public static String getUfile() {
        return ufile;
    }
}
