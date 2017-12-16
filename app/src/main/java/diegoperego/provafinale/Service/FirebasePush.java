package diegoperego.provafinale.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import diegoperego.provafinale.LoginActivity;
import diegoperego.provafinale.Model.Corriere;
import diegoperego.provafinale.Model.Users;
import diegoperego.provafinale.Model.Utente;
import diegoperego.provafinale.PacchiActivity;
import diegoperego.provafinale.R;
import diegoperego.provafinale.UtentiActivity;
import diegoperego.provafinale.Util.InternalStorage;

/**
 * Created by utente3.academy on 15-Dec-17.
 */

public class FirebasePush extends Service{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String url = "https://provafinale-b1597.firebaseio.com/Users";
    private DatabaseReference usersReference = database.getReferenceFromUrl(url);
    private ChildEventListener handler;
    private Users userC;
    private Users userU;
    private String stato;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("FIREBASE_SERVICE", "STOP DAY SERVICE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("FIREBASE_SERVICE", "START DAY SERVICE");
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        userU = (Users) InternalStorage.readObject(getApplicationContext(), "utente");
        userC = (Users) InternalStorage.readObject(getApplicationContext(), "corriere");
        stato = (String) InternalStorage.readObject(getApplicationContext(), "stato");

        if(userC instanceof Corriere){
            url = url+ "/Corriere/"+ userC.getUsername() + "/Pacchi/";
        }else if(userU instanceof Utente){
            url = url+ "/Utente/"+ userU.getUsername() + "/Pacchi/";
        }

        usersReference = database.getReferenceFromUrl(url);

        handler = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("FIREBASE_SERVICE", "ADD: " + dataSnapshot.getKey());
                if (url.contains("Corriere") && dataSnapshot.exists()){
                    activePushValidation(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("FIREBASE_SERVICE", "CHANGE: " + dataSnapshot.getKey());
                if (url.contains("Utente") && dataSnapshot.exists()){
                    activePushValidation(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE_SERVICE", "REMOVE: " + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("FIREBASE_SERVICE", "MOVED: " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        usersReference.addChildEventListener(handler);
    }

    public void activePushValidation(String commListener) {

        Intent intent = new Intent(this, UtentiActivity.class);
        sendNotification(intent, "Notifica", commListener);
    }

    public void sendNotification(Intent intent, String title, String body) {

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri dsUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setAutoCancel(true);
        builder.setSound(dsUri);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);
        builder.setShowWhen(true);
        builder.setContentIntent(activity);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}
