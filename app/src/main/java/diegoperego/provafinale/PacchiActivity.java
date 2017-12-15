package diegoperego.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import diegoperego.provafinale.Adapter.PacchiAdapter;
import diegoperego.provafinale.Model.Pacco;
import diegoperego.provafinale.Model.Users;
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
    private List<Pacco> pacchi;
    private PacchiAdapter pacchiAdapter;
    private TextView noPacchi;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacchi);

        userC = (Users) InternalStorage.readObject(getApplicationContext(), "corriere");

        benvenuto = findViewById(R.id.tBenvenutoCVal);
        benvenuto.setText(userC.getUsername());
        noPacchi = findViewById(R.id.tNiente);
        relativeLayout = findViewById(R.id.rela);

        final TaskDelegate delegate = this;

        recyclerView = findViewById(R.id.recyclerPacchi);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        restPacchi(delegate);

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
}
