package model;

import implementations.ConfigUtiles;
import implementations.FileUtiles;
import implementations.KorisnikUtiles;


import java.io.File;
import java.io.IOException;
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

    public boolean inicializuj(String koren) {
        File sk=new File(koren);
        if(!sk.exists()){
            sk.mkdirs();
        }

        int fl1,fl=cUtiles.writeConfig(koren+"/"+getCfile(),new Configuration());
        fl1=kUtiles.writeUsers(koren+"/"+getUfile(),null);

        if(fl1<0 || fl<0 )return  false;
        this.koren=koren;

        return true;
    }

    public Map<String, Integer> readFiles() {
        return fUtiles.readFiles(koren);
    }
    protected String skGetFileDir(String filepath) {
        return fUtiles.getFileDir(filepath);
    }

    protected boolean skAddFile(String file,String path) {
        return fUtiles.addFile(file,path,koren);
    }

    protected boolean skDeleteFile(String filepath) {
        return fUtiles.deleteFile(filepath,koren);
    }

    protected List<FAFile> skListSkaldiste() {
        return fUtiles.listSkladiste(koren);
    }

    protected boolean skMoveFile(String file, String path) {
        return fUtiles.moveFile(file,path,koren);
    }

    protected boolean skDownloadFile(String filepath) {
        return fUtiles.downloadFile(filepath,koren);
    }

    protected boolean skCreateFile(String name,String path,boolean dir) {
        return fUtiles.createFiles(name,path,dir,koren);
    }

    public int readConfig() {
            Pair rc=cUtiles.readConfig(koren);
            if(rc.getValue() == null){
                System.out.println("Error occured with reading Config file");
                return (int) rc.getKey();
            }
            this.configuration= (Configuration) rc.getValue();
            return  1;
    }

    public void writeConfig() { cUtiles.writeConfig(koren+"/"+getCfile(),configuration); }


    protected boolean skAuntetifikacija(String user, String pass) {
        return kUtiles.autetifikacija(user,pass,koren);
    }

    protected List<Privilegije> skReadPrivil(String user, String pass) {
        return kUtiles.readPrivil(user,pass,koren+"/"+LkSkladiste.getUfile());
    }

    protected boolean skMakeKorisnik(String user, String pass, List<Privilegije> privil) {
        return kUtiles.makeKorisnik(user,pass,privil,koren+"/"+LkSkladiste.getUfile());
    }

    protected boolean skDelKorisnik(String s, String s1) {
        return kUtiles.delKornisk(s,s1,koren+"/"+LkSkladiste.getUfile());
    }

    public static String getCfile() {
        return cfile;
    }

    public static String getUfile() {
        return ufile;
    }
}
