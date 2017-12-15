package diegoperego.provafinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.msebera.android.httpclient.extras.PRNGFixes;
import diegoperego.provafinale.Model.Utente;
import diegoperego.provafinale.Util.InternalStorage;

public class RegistratiActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText username;
    private EditText password;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrati);

        username = findViewById(R.id.eUsernameR);
        password = findViewById(R.id.ePasswordR);
        spinner = findViewById(R.id.spinner);
        database = FirebaseDatabase.getInstance();

    }

    public void onRegistratiCL(View view){

        String user = username.getText().toString();
        String pass = password.getText().toString();
        if (spinner.getSelectedItem().equals("Utente")){
            reference = database.getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Utente/"+ user + "/");
            reference.child("pass").setValue(pass);
        }else {
            reference = database.getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Corriere/"+ user + "/");
            reference.child("pass").setValue(pass);
        }
        Toast.makeText(getApplicationContext(), "Utente registrato", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
