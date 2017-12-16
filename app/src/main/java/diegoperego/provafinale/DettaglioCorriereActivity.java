package diegoperego.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import diegoperego.provafinale.Model.Pacco;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Service.FirebasePush;
import diegoperego.provafinale.Util.FirebaseRest;
import diegoperego.provafinale.Util.InternalStorage;
import diegoperego.provafinale.Util.JsonParser;
import diegoperego.provafinale.Util.TaskDelegate;

public class DettaglioCorriereActivity extends AppCompatActivity implements TaskDelegate{

    private EditText deposito;
    private EditText indirizzo;
    private EditText data;
    private Spinner spinner;
    private ProgressDialog dialog;
    private Users userU;
    private DatabaseReference databaseReferenceUtente;
    private DatabaseReference databaseReferenceCorriere;
    private Pacco pacco;
    private Button commissionato;
    private String corriere;
    private EditText numero;
    private Intent notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_corriere);
        deposito = findViewById(R.id.eDesposito);
        indirizzo = findViewById(R.id.eIndirizzoCon);
        data = findViewById(R.id.eDataConsegna);
        spinner = findViewById(R.id.spinnerDim);
        commissionato = findViewById(R.id.bCommissionato);
        numero = findViewById(R.id.eNumeroPacco);

        final TaskDelegate delegate = this;

        userU = (Users) InternalStorage.readObject(getApplicationContext(), "utente");
        corriere = (String) InternalStorage.readObject(getApplicationContext(), "nomeCorriere");
        notification = new Intent(this, FirebasePush.class);

        commissionato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPacco();
                numPacco(delegate);
                databaseReferenceUtente = FirebaseDatabase.getInstance().getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Utente/"+ userU.getUsername()+"/Pacchi");
                databaseReferenceCorriere = FirebaseDatabase.getInstance().getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Corriere/"+ corriere + "/Pacchi");
                startService(notification);
                Intent i = new Intent(getApplicationContext(), UtentiActivity.class);
                startActivity(i);
            }
        });

    }

    public void createPacco(){
        String dep = deposito.getText().toString();
        String ind = indirizzo.getText().toString();
        String date = data.getText().toString();
        if(dep.equals("") || !ind.equals("") || !date.equals("")){
            pacco = new Pacco(dep, ind, userU.getUsername(), spinnerItem(), date, "Commissionato" );
        }
    }

    public void numPacco(final TaskDelegate delegate){
        dialog= new ProgressDialog(DettaglioCorriereActivity.this);
        dialog.setMessage("Caricamento prodotti");
        dialog.show();

        FirebaseRest.get("Users/Utente/" + userU.getUsername()+"/Pacchi", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp = new String(responseBody);
                databaseReferenceUtente.child(numero.getText().toString()).setValue(pacco);
                databaseReferenceCorriere.child(numero.getText().toString()).setValue(pacco);
                databaseReferenceCorriere.child(numero.getText().toString()).child("stato").setValue(pacco.getStato());
                InternalStorage.writeObject(getApplicationContext(),"posizione" ,numero.getText().toString());
                delegate.taskCompleto("Commissionato");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.taskCompleto("Errore");
            }
        });
    }


    private String spinnerItem(){
        String item = (String) spinner.getSelectedItem();
        return item;
    }

    @Override
    public void taskCompleto(String s) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}
