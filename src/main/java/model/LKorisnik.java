package model;

import java.util.ArrayList;
import java.util.List;

public class LKorisnik {
    private  String user;
    private  String password;
    private List<Privilegije> privl;

    public LKorisnik() {
        privl=new ArrayList<>();
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

    public List<Privilegije> getPrivl() {
        return privl;
    }

    public void setPrivl(List<Privilegije> privl) {
        this.privl = privl;
    }
}
