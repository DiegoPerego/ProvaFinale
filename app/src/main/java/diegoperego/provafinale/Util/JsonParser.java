package diegoperego.provafinale.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.Model.Pacco;

/**
 * Created by utente3.academy on 15-Dec-17.
 */

public class JsonParser {

    public static List<Pacco> findPacchi(String json){

        List<Pacco> pacchi = new ArrayList<>();
        String key = null;
        JSONObject jpacco= null;

        try {
            jpacco = new JSONObject(json);
            Iterator iteratorP = jpacco.keys();

            while (iteratorP.hasNext()){
                key = (String) iteratorP.next();
                JSONObject paccoJ = jpacco.getJSONObject(key);
                Iterator iteratorJp = paccoJ.keys();
                Pacco pacco = new Pacco();
                while (iteratorJp.hasNext()) {
                    String keys = (String) iteratorJp.next();
                    String s = paccoJ.getString(keys);
                    if (keys.equals("dataConsegna")) {
                        pacco.setDataConsegna(s);
                    }
                    if (keys.equals("deposito")) {
                        pacco.setDeposito(s);
                    }
                    if (keys.equals("destinatario")) {
                        pacco.setDestinatario(s);
                    }
                    if (keys.equals("dimensioni")) {
                        pacco.setDimensioni(s);
                    }
                    if (keys.equals("indirizzo")) {
                        pacco.setIndirizzo(s);
                    }
                    if (keys.equals("stato")) {
                        pacco.setStato(s);
                    }
                }
                pacchi.add(pacco);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pacchi;
    }

    public static List<Corriere> findCorriere(String json){

        List<Corriere> corrieri = new ArrayList<>();
        String key = null;
        JSONObject jCorriere = null;

        try {
            jCorriere = new JSONObject(json);
            Iterator iteratorC = jCorriere.keys();
            while (iteratorC.hasNext()){
                Corriere c = new Corriere();
                key = (String) iteratorC.next();
                c.setUsername(key);
                corrieri.add(c);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return corrieri;
    }

}
