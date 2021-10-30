package model;

import implementations.ConfigUtiles;
import implementations.FileUtiles;
import implementations.KorisnikUtiles;

import java.util.List;
import java.util.Map;

public class LkSkladiste extends Skladiste {
    private KorisnikUtiles kUtiles;
    private ConfigUtiles cUtiles;
    private FileUtiles fUtiles;



    public LkSkladiste(String path) {
        super(path);
        this.kUtiles=new KorisnikUtiles();
        this.cUtiles=new ConfigUtiles();
        this.fUtiles=new FileUtiles();
    }

    protected boolean load(String s) {
        return false;
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

    public void readConfig() {
        //this.configuration= cUtiles.readConfig;

    }

    public void writeConfig() {
        cUtiles.writeConfig();

    }

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
}
