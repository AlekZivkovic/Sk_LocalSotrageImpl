package model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LKorisnik {
    private  String user;
    private  String password;
    private Map<String,List<Privilegije>> privl;
    private boolean admin;

    public LKorisnik() {
        privl=new HashMap<>();
    }

    public LKorisnik(String user, String pass) {
        this.user=user;
        this.password=pass;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String,List<Privilegije>> getPrivl() {
        return privl;
    }

    public void setPrivl(Map<String,List<Privilegije>> privl) {
        this.privl = privl;
    }
    public void nadodajPrivil(Map<String,List<Privilegije>> nove){
        for (Map.Entry<String,List<Privilegije> > entry : nove.entrySet()) {
            String key = entry.getKey();
            privl.put(key, entry.getValue());

        }

    }

    @Override
    public String toString() {
        return "LKorisnik{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  LKorisnik){
            LKorisnik k=(LKorisnik)obj;
            return  this.password.equals(k.getPassword()) && this.user.equals(k.getUser());
        }
        return  false;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }
}
