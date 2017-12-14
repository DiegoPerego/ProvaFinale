package diegoperego.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Model.Utente;
import diegoperego.provafinale.Util.FirebaseRest;
import diegoperego.provafinale.Util.InternalStorage;
import diegoperego.provafinale.Util.TaskDelegate;

public class LoginActivity extends AppCompatActivity implements TaskDelegate{

    private RadioButton utente;
    private RadioButton corriere;
    private String username;
    private String password;
    private ProgressDialog dialog;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        utente = findViewById(R.id.rUtente);
        corriere = findViewById(R.id.rCorriere);

    }

    public void onLoginClick(View v){

        username = ((EditText)findViewById(R.id.eUser)).getText().toString();
        password = ((EditText)findViewById(R.id.ePass)).getText().toString();
        final TaskDelegate delegate = this;
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Controllo");
        dialog.show();

        if (utente.isChecked()){
            url = "Users/" + utente.getText().toString() + "/";
        }
        if (corriere.isChecked()){
            url = "Users/" + corriere.getText().toString() + "/";
        }

        FirebaseRest.get(url+ username + "/pass/", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String  resp = new String(responseBody);
                String s = resp.substring(1, resp.length()-1);
                if (! password.equals("null")){
                    if (password.equals(s)){
                        startIntent();
                    }
                    else {
                        delegate.taskCompleto("Password errata");
                    }
                }else {
                    delegate.taskCompleto("Password non Inserita");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.taskCompleto("Registrati");
            }
        });
    }

    public void startIntent(){
        Intent pacchi = new Intent(getApplicationContext(), PacchiActivity.class);
        Intent utenti = new Intent(getApplicationContext(), UtentiActivity.class);
        if (utente.isChecked()){
            Users u = new Utente(username, password);
            InternalStorage.writeObject(getApplicationContext(), "users", u);
            startActivity(utenti);
        }
        if (corriere.isChecked()) {
            Users c = new Corriere(username, password);
            InternalStorage.writeObject(getApplicationContext(), "users", c);
            startActivity(pacchi);
        }
    }

    public void onRegistratiClick(View v){
        Intent registrati = new Intent(getApplicationContext(), RegistratiActivity.class);
        startActivity(registrati);
    }

    @Override
    public void taskCompleto(String s) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
