package diegoperego.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Util.FirebaseRest;
import diegoperego.provafinale.Util.InternalStorage;
import diegoperego.provafinale.Util.TaskDelegate;

public class PacchiActivity extends AppCompatActivity implements TaskDelegate{

    private TextView benvenuto;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressDialog dialog;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacchi);

        users = (Users) InternalStorage.readObject(getApplicationContext(), "users");

        benvenuto = findViewById(R.id.tBenvenutoCVal);
        benvenuto.setText(users.getUsername());

        final TaskDelegate delegate;

        recyclerView = findViewById(R.id.recyclerPacchi);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }


    public void restPacchi(final TaskDelegate delegate){
        dialog= new ProgressDialog(PacchiActivity.this);
        dialog.setMessage("Caricamento prodotti");
        dialog.show();

        FirebaseRest.get("Users/Utente" + users.getUsername()+ "/Pacchi/", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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
