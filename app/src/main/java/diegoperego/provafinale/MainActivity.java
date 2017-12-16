package diegoperego.provafinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Model.Utente;
import diegoperego.provafinale.Util.InternalStorage;

public class MainActivity extends AppCompatActivity {

    private Intent login;
    private Intent pacchi;
    private Intent utenti;
    private Users userU;
    private Users userC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userU = (Users) InternalStorage.readObject(getApplicationContext(), "utente");
        userC = (Users) InternalStorage.readObject(getApplicationContext(), "corriere");

        login = new Intent(getApplicationContext(), LoginActivity.class);
        pacchi = new Intent(getApplicationContext(), PacchiActivity.class);
        utenti = new Intent(getApplicationContext(), UtentiActivity.class);

        if (userC instanceof Corriere){
            startActivity(pacchi);
        }
        if(userU instanceof Utente){
            startActivity(utenti);
        }

        startActivity(login);

    }
}
