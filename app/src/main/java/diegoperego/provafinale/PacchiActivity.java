package diegoperego.provafinale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import diegoperego.provafinale.Adapter.PacchiAdapter;
import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.Model.Pacco;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Model.Utente;
import diegoperego.provafinale.Service.FirebasePush;
import diegoperego.provafinale.Util.FirebaseRest;
import diegoperego.provafinale.Util.InternalStorage;
import diegoperego.provafinale.Util.JsonParser;
import diegoperego.provafinale.Util.TaskDelegate;

public class PacchiActivity extends AppCompatActivity implements TaskDelegate{

    private TextView benvenuto;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressDialog dialog;
    private Users userC;
    private Users userU;
    private List<Pacco> pacchi;
    private PacchiAdapter pacchiAdapter;
    private TextView noPacchi;
    private RelativeLayout relativeLayout;
    private String url;
    private DatabaseReference databaseReferenceUtente;
    private DatabaseReference databaseReferenceCorriere;
    private Button stato;
    private String pos;
    private SwipeRefreshLayout swipe;
    private Intent notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacchi);

        userC = (Users) InternalStorage.readObject(getApplicationContext(), "corriere");
        userU = (Users) InternalStorage.readObject(getApplicationContext(), "utente");
        pos = (String) InternalStorage.readObject(getApplicationContext(), "posizione");

        benvenuto = findViewById(R.id.tBenvenutoCVal);
        benvenuto.setText(userC.getUsername());
        noPacchi = findViewById(R.id.tNiente);
        relativeLayout = findViewById(R.id.rela);
        swipe = findViewById(R.id.swipePacchi);

        final TaskDelegate delegate = this;

        recyclerView = findViewById(R.id.recyclerPacchi);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        restPacchi(delegate);

        swipe.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onRefresh() {
                restPacchi(delegate);
            }
        });

    }

    public void restPacchi(final TaskDelegate delegate){
        dialog= new ProgressDialog(PacchiActivity.this);
        dialog.setMessage("Caricamento prodotti");
        dialog.show();

        pacchi = new ArrayList<>();

        FirebaseRest.get("Users/Corriere/" + userC.getUsername()+ "/Pacchi/", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp = new String(responseBody);
                pacchi = JsonParser.findPacchi(resp);
                if (pacchi.size()!=0) {
                    noPacchi.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    pacchiAdapter = new PacchiAdapter(getApplicationContext(), pacchi);
                    recyclerView.setAdapter(pacchiAdapter);
                }else{
                    relativeLayout.setVisibility(View.INVISIBLE);
                    noPacchi.setVisibility(View.VISIBLE);
                }
                swipe.setRefreshing(false);
                delegate.taskCompleto("Caricamento effettuato");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.taskCompleto("Errore");
            }
        });
    }

    @Override
    public void taskCompleto(String s) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void cambioStato(View view){
        final TaskDelegate delegate = this;
        stato = findViewById(R.id.bStato);
        restStato(delegate);
        databaseReferenceUtente = FirebaseDatabase.getInstance().getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Utente/"+ userU.getUsername()+"/Pacchi/");
        databaseReferenceCorriere = FirebaseDatabase.getInstance().getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Corriere/"+ userC.getUsername() + "/Pacchi/");
        notification = new Intent(this, FirebasePush.class);
        stato.setVisibility(View.INVISIBLE);
        startService(notification);
    }

    public void restStato(final TaskDelegate delegate){
        dialog= new ProgressDialog(PacchiActivity.this);
        dialog.setMessage("In Consegna");
        dialog.show();

        if(userU instanceof  Utente){
            url = "Users/Utente" + userU.getUsername() + "/Pacchi/";
        }
        if(userC instanceof Corriere){
            url = "Users/Corriere" + userC.getUsername() + "/Pacchi/";
        }

        FirebaseRest.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                databaseReferenceUtente.child(pos).child("stato").setValue("In Consegna");
                databaseReferenceCorriere.child(pos).child("stato").setValue("In Consegna");
                delegate.taskCompleto("In Consegna");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.taskCompleto("Errore");
            }
        });

    }
}
