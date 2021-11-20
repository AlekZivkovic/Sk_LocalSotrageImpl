package model;

import implementations.ConfigUtiles;
import implementations.FileUtiles;
import implementations.KorisnikUtiles;


import java.io.File;
import java.util.List;
import java.util.Map;

public class LkSkladiste extends Skladiste {
    private static final KorisnikUtiles kUtiles;
    private static final ConfigUtiles cUtiles;
    private static final FileUtiles fUtiles;
    private static final String cfile="config.json";
    private static final String ufile="users.json";


    static {
        kUtiles=new KorisnikUtiles();
        cUtiles=new ConfigUtiles();
        fUtiles=new FileUtiles();
    }

    public LkSkladiste() {
    }

    @Override
    protected String relativeToAbsoulute(String relative) {
        return koren+File.separator+relative;
    }

    protected boolean load(String filepath) {
         boolean korCheck,configCheck;
        configCheck=new File(filepath+File.separator+getCfile()).exists();
        korCheck= new File(filepath+File.separator+getUfile()).exists();
        return korCheck && configCheck;
    }

    @Override
    protected boolean inicializuj(String koren) {
        File sk=new File(koren);
        if(!sk.exists()){
            //noinspection ResultOfMethodCallIgnored
            sk.mkdirs();
        }

        int fl1,fl=cUtiles.writeConfig(koren+File.separator+getCfile(),new Configuration());
        fl1=kUtiles.writeUsers(koren+File.separator+getUfile(),null);

        if(fl1<0 || fl<0 )return  false;
        this.koren=koren;
        this.configuration=new Configuration();

        return true;
    }

    protected Map<String, Integer> readFiles() {
        return fUtiles.readFiles(koren);
    }
    protected String skGetFileDir(String filepath) {
        return fUtiles.getFileDir(filepath);
    }

    @Override
    protected long skGetFileSize(String file) {
        return fUtiles.getFileSize(file);
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

    protected int readConfig() {
            Pair rc=cUtiles.readConfig(koren);
            if(rc.getValue() == null){
                System.out.println("Error occured with reading Config file");
                return (int) rc.getKey();
            }
            this.configuration= (Configuration) rc.getValue();
            return  1;
    }

    protected void writeConfig() { cUtiles.writeConfig(koren+File.separator+getCfile(),configuration); }


    protected boolean skAuntetifikacija(String user, String pass) {
        return kUtiles.autetifikacija(user,pass,koren);
    }

    protected Pair skReadPrivil(String user, String pass) {
        return kUtiles.readPrivil(user,pass,koren+File.separator+LkSkladiste.getUfile());
    }

    protected boolean skMakeKorisnik(String user, String pass, Map<String,List<Privilegije>> privil,boolean admin) {
        return kUtiles.makeKorisnik(user,pass,privil,koren+File.separator+LkSkladiste.getUfile(),admin);
    }

    @Override
    protected Korisnik skGetKorisnik(String user) {
        return kUtiles.getKorisnik(user,koren);
    }

    protected boolean skDelKorisnik(String s, String s1) {
        return kUtiles.delKornisk(s,s1,koren+File.separator+LkSkladiste.getUfile());
    }

    @Override
    protected int updateForUser(Korisnik pKor) {

        return kUtiles.updateUsers(pKor,koren);
    }

    public static String getCfile() {
        return cfile;
    }

    public static String getUfile() {
        return ufile;
    }
}
