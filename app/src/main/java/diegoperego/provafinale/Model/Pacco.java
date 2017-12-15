package diegoperego.provafinale.Model;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by utente3.academy on 14-Dec-17.
 */

public class Pacco implements Serializable{

    private String id;
    private String deposito;
    private String indirizzoConsegna;
    private String destinatario;
    private String dimensioni;
    private Date dataConsegna;
    private String stato;

    public Pacco(String id, String deposito, String indirizzo, String destinatario, String dimensioni, Date dataConsegna, String stato) {
        this.id = id;
        this.deposito = deposito;
        this.indirizzoConsegna = indirizzo;
        this.destinatario = destinatario;
        this.dimensioni = dimensioni;
        this.dataConsegna = dataConsegna;
        this.stato = stato;
    }

    public Pacco() {
        this.id = null;
        this.deposito = null;
        this.indirizzoConsegna = null;
        this.destinatario = null;
        this.dimensioni = null;
        this.dataConsegna = null;
        this.stato = null;
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

    public Date getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
