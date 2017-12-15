package diegoperego.provafinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Util.InternalStorage;

public class PacchiActivity extends AppCompatActivity {

    private Users users;
    private TextView benvenuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacchi);

        users = (Users) InternalStorage.readObject(getApplicationContext(), "users");

        benvenuto = findViewById(R.id.tBenvenutoCVal);
        benvenuto.setText(users.getUsername());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
