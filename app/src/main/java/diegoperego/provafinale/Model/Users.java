package diegoperego.provafinale.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente3.academy on 14-Dec-17.
 */

public class Users {

    public String username;
    public String password;
    public List<Pacco> pacchi;

    public Users(String username, String password, List<Pacco> pacchi) {
        this.username = username;
        this.password = password;
        this.pacchi = pacchi;
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users() {
        this.username = null;
        this.password = null;
        this.pacchi = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pacco> getPacchi() {
        return pacchi;
    }

    public void setPacchi(List<Pacco> pacchi) {
        this.pacchi = pacchi;
    }
}
