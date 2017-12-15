package diegoperego.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.PointerIcon;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import diegoperego.provafinale.Util.TaskDelegate;

public class DettaglioCorriereActivity extends AppCompatActivity implements TaskDelegate{

    private EditText deposito;
    private EditText indirizzo;
    private EditText data;
    private Spinner spinner;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_corriere);
        deposito = findViewById(R.id.eDesposito);
        indirizzo = findViewById(R.id.eIndirizzoCon);
        data = findViewById(R.id.eDataConsegna);
        spinner = findViewById(R.id.spinnerDim);

        final TaskDelegate delegate = this;

    }

    public void pacco(final TaskDelegate delegate){

    }


    public void onConsegnaClick(View view){
        Intent i = new Intent(getApplicationContext(), UtentiActivity.class);
        startActivity(i);
    }

    @Override
    public void taskCompleto(String s) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
