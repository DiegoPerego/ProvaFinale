package diegoperego.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import diegoperego.provafinale.Adapter.CorrieriAdapter;
import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Util.FirebaseRest;
import diegoperego.provafinale.Util.InternalStorage;
import diegoperego.provafinale.Util.JsonParser;
import diegoperego.provafinale.Util.TaskDelegate;

public class UtentiActivity extends AppCompatActivity implements TaskDelegate{

    private Users users;
    private TextView benvenuto;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CorrieriAdapter corrieriAdapter;
    private ProgressDialog dialog;
    private List<Corriere> corrieri;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utenti);

        users = (Users) InternalStorage.readObject(getApplicationContext(), "users");

        benvenuto = findViewById(R.id.tBenvenutoUVal);
        benvenuto.setText(users.getUsername());

        final TaskDelegate delegate = this;

        recyclerView = findViewById(R.id.recyclerCorrieri);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        restCorrieri(delegate);

        swipeRefreshLayout = findViewById(R.id.swiperef);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restCorrieri(delegate);
            }
        });

    }

    public void restCorrieri(final TaskDelegate delegate){
        dialog = new ProgressDialog(UtentiActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();

        corrieri = new ArrayList<>();

        FirebaseRest.get("Users/Corriere/", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp = new String(responseBody);
                corrieri = JsonParser.findCorriere(resp);
                corrieriAdapter = new CorrieriAdapter(getApplicationContext(), corrieri);
                recyclerView.setAdapter(corrieriAdapter);
                swipeRefreshLayout.setRefreshing(false);
                delegate.taskCompleto("Caricamento Effettuato");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.taskCompleto("Errore");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void taskCompleto(String s) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
