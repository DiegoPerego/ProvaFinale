package diegoperego.provafinale.Model;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by utente3.academy on 14-Dec-17.
 */

public class Pacco implements Serializable{

    private String deposito;
    private String indirizzo;
    private String destinatario;
    private String dimensioni;
    private String dataConsegna;
    private String stato;

    public Pacco( String deposito, String indirizzo, String destinatario, String dimensioni, String dataConsegna, String stato) {
        this.deposito = deposito;
        this.indirizzo = indirizzo;
        this.destinatario = destinatario;
        this.dimensioni = dimensioni;
        this.dataConsegna = dataConsegna;
        this.stato = stato;
    }

    public Pacco() {
        this.deposito = null;
        this.indirizzo = null;
        this.destinatario = null;
        this.dimensioni = null;
        this.dataConsegna = null;
        this.stato = null;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
