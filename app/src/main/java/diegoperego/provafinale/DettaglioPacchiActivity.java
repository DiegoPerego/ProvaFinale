package diegoperego.provafinale;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Util.FirebaseRest;
import diegoperego.provafinale.Util.InternalStorage;

public class DettaglioPacchiActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String destinatario;
    private String indirizzo;
    private String deposito;
    private TextView destinatarioVal;
    private Users userC;
    private Users userU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_pacchi);

        userC = (Users) InternalStorage.readObject(getApplicationContext(), "utente");
        userU = (Users) InternalStorage.readObject(getApplicationContext(), "corriere");

        deposito = (String) InternalStorage.readObject(getApplicationContext(), "destinazione");
        indirizzo = (String) InternalStorage.readObject(getApplicationContext(), "indirizzo");
        destinatario = (String) InternalStorage.readObject(getApplicationContext(), "destinatario");

        destinatarioVal = findViewById(R.id.tDestMapVal);

        destinatarioVal.setText(destinatario);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onConsegnatoClick(View view){

    }
}
