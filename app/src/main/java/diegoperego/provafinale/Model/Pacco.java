package diegoperego.provafinale.Model;

import android.content.SharedPreferences;

/**
 * Created by utente3.academy on 14-Dec-17.
 */

public class Pacco {

    private String id;
    private String deposito;
    private String indirizzoConsegna;
    private String destinatario;
    private String dimensioni;
    private String dataConsegna;

    public Pacco(String id, String deposito, String indirizzo, String destinatario, String dimensioni, String dataConsegna) {
        this.id = id;
        this.deposito = deposito;
        this.indirizzoConsegna = indirizzo;
        this.destinatario = destinatario;
        this.dimensioni = dimensioni;
        this.dataConsegna = dataConsegna;
    }

    public Pacco() {
        this.id = null;
        this.deposito = null;
        this.indirizzoConsegna = null;
        this.destinatario = null;
        this.dimensioni = null;
        this.dataConsegna = null;
    }

    public String getIndirizzoConsegna() {
        return indirizzoConsegna;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIndirizzoConsegna(String indirizzoConsegna) {
        this.indirizzoConsegna = indirizzoConsegna;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getIndirizzo() {
        return indirizzoConsegna;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzoConsegna = indirizzo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public String getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(String dataConsegna) {
        this.dataConsegna = dataConsegna;
    }
}
