package diegoperego.provafinale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Model.Utente;
import diegoperego.provafinale.Util.FirebaseRest;
import diegoperego.provafinale.Util.InternalStorage;
import diegoperego.provafinale.Util.TaskDelegate;

public class DettaglioPacchiActivity extends FragmentActivity implements OnMapReadyCallback, TaskDelegate{

    private GoogleMap mMap;
    private String destinatario;
    private String indirizzo;
    private String deposito;
    private String stato;
    private TextView destinatarioVal;
    private Button consegnato;
    private Users userC;
    private Users userU;
    private double latitude;
    private double longitude;
    private ProgressDialog dialog;
    private DatabaseReference databaseReferenceUtente;
    private DatabaseReference databaseReferenceCorriere;
    private String url;
    private String pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_pacchi);

        userC = (Users) InternalStorage.readObject(getApplicationContext(), "utente");
        userU = (Users) InternalStorage.readObject(getApplicationContext(), "corriere");
        pos = (String) InternalStorage.readObject(getApplicationContext(), "posizione");

        deposito = (String) InternalStorage.readObject(getApplicationContext(), "deposito");
        indirizzo = (String) InternalStorage.readObject(getApplicationContext(), "indirizzo");
        destinatario = (String) InternalStorage.readObject(getApplicationContext(), "destinatario");
        stato = (String) InternalStorage.readObject(getApplicationContext(), "stato");

        destinatarioVal = findViewById(R.id.tDestMapVal);
        consegnato = findViewById(R.id.bConsegnato);

        destinatarioVal.setText(destinatario);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        if(stato.equalsIgnoreCase("consegnato")){
            consegnato.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng milano = new LatLng(45.4652317, 9.1876072);
        BitmapDescriptor truck = getBitmapDescriptor(R.drawable.truck_delivery);
        BitmapDescriptor man = getBitmapDescriptor(R.drawable.ic_person_pin_circle_black_24dp);

        geocoding(indirizzo);
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(indirizzo).icon(man));
        geocoding(deposito);
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(deposito).icon(truck));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(milano, 11));
    }

    public void geocoding(String indirizzo){

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocationName(indirizzo + "Milano", 1);
            latitude = (float) addresses.get(0).getLatitude();
            longitude = (float) addresses.get(0).getLongitude();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Indirizzo non trovato",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @SuppressLint("ResourceAsColor")
    public BitmapDescriptor getBitmapDescriptor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) getDrawable(id);

            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();

            vectorDrawable.setBounds(0, 0, w, h);

            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);

            if (stato.equalsIgnoreCase("commissionato")){
                vectorDrawable.setTint(Color.BLACK);
            }
            if (stato.equalsIgnoreCase("in consegna")){
                vectorDrawable.setTint(Color.BLUE);
            }
            if (stato.equalsIgnoreCase("consegnato")){
                vectorDrawable.setTint(Color.rgb(0, 179, 0));
            }

            vectorDrawable.draw(canvas);


            return BitmapDescriptorFactory.fromBitmap(bm);

        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }

    public void onConsegnatoClick(View view){
        final TaskDelegate delegate = this;
        restConsegnato(delegate);
        databaseReferenceUtente = FirebaseDatabase.getInstance().getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Utente/"+ userC.getUsername() +"/Pacchi/");
        databaseReferenceCorriere = FirebaseDatabase.getInstance().getReferenceFromUrl("https://provafinale-b1597.firebaseio.com/Users/Corriere/"+ userU.getUsername() + "/Pacchi/");
        Intent intent = new Intent(getApplicationContext(), PacchiActivity.class);
        startActivity(intent);
    }

    @Override
    public void taskCompleto(String s) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void restConsegnato(final TaskDelegate delegate) {
        dialog = new ProgressDialog(DettaglioPacchiActivity.this);
        dialog.setMessage("Consegnato");
        dialog.show();

        if (userU instanceof Utente) {
            url = "Users/Corriere" + userC.getUsername() + "/Pacchi/";
        }
        if (userC instanceof Corriere) {
            url = "Users/Utente" + userU.getUsername() + "/Pacchi/";
        }

        FirebaseRest.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                databaseReferenceUtente.child(pos).child("stato").setValue("Consegnato");
                databaseReferenceCorriere.child(pos).child("stato").setValue("Consegnato");
                delegate.taskCompleto("Consegnato");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.taskCompleto("Errore");
            }
        });
    }

}
