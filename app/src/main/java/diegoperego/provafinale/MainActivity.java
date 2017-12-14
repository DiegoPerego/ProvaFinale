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
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = (Users) InternalStorage.readObject(getApplicationContext(), "users");

        login = new Intent(getApplicationContext(), LoginActivity.class);
        pacchi = new Intent(getApplicationContext(), PacchiActivity.class);
        utenti = new Intent(getApplicationContext(), UtentiActivity.class);

        if (users instanceof Corriere){
            startActivity(pacchi);
        }else if(users instanceof Utente){
            startActivity(utenti);
        }else {
            startActivity(login);
        }

    }
}
