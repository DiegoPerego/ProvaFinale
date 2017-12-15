package diegoperego.provafinale.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by utente3.academy on 14-Dec-17.
 */

public class Corriere extends Users implements Serializable{

    public Corriere(String username, String password, List<Pacco> pacchi) {
        super(username, password, pacchi);
    }

    public Corriere(String username, String password) {
        super(username, password);
    }

    public Corriere() {
        super();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public List<Pacco> getPacchi() {
        return super.getPacchi();
    }

    @Override
    public void setPacchi(List<Pacco> pacchi) {
        super.setPacchi(pacchi);
    }
}
