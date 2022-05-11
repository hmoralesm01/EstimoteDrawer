package com.example.estimotedrawer;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.example.estimotedrawer.databinding.ActivityMainBinding;
import com.example.estimotedrawer.login.log_in;
import com.example.estimotedrawer.models.Booking;
import com.example.estimotedrawer.models.Local;
import com.example.estimotedrawer.ui.gallery.MyAdapterBooking;
import com.example.estimotedrawer.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements Booking.onDeleteBooking, Local.onLocalSeleccionad, Local.onLocalNumberPhone, HomeFragment.onPersistenciaDatos {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    int FINE_LOCATION_REQUEST_CODE = 101;
    private BluetoothAdapter bluetoothAdapter;
    private BeaconManager beaconManager;
    private BeaconRegion region;
    private FirebaseAuth mAuth;
    public static ArrayList<Local> listLocales;
    private DatabaseReference mDatabase;
    private final static String UUID_ID = "d6714228-7bbb-41fc-91e3-24e6aadd3703";

    //bbdd
    private SQLiteDatabase db;
    public static ArrayList<Booking> listaBookings;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //estimote

        //preguntamos por permisos ubicacion
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION },
                    FINE_LOCATION_REQUEST_CODE);
        }

        //bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(this, "Dispositivo sin bluetooth", Toast.LENGTH_LONG).show();
        }else if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        startActivity(new Intent(this, log_in.class));// falta logOut

        //
        createBBDD();

        nameChanged();

    }//fin on create


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void conectar(){
        beaconManager = new BeaconManager(getApplicationContext());
        region = new BeaconRegion("ranged region",
                UUID.fromString(UUID_ID), null, null);

        // add this below:
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener(){

            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                showNotification("hola habitacion","hola habitacion");
                incrementarDecrementarContador(true);
                System.out.println("estamos dentro");
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                showNotification("adios ","adios");
                incrementarDecrementarContador(false);
            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(region);
            }
        });

    }//fin conectar



    public void showNotification(String title, String message) {
        //crear notificacion
        String id_canal = "id_canal";
        NotificationChannel channel = new NotificationChannel(
                id_canal,
                "Canal Cumpleaños",
                NotificationManager.IMPORTANCE_HIGH);

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);
        notificacion.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificacion.setSmallIcon(R.drawable.notification);
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setContentTitle("ALARMA");

        notificacion.setContentText(message);

        NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.createNotificationChannel(channel);
        notificacion.setChannelId(id_canal);
        nm.notify(1,notificacion.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 101:
                if(requestCode==FINE_LOCATION_REQUEST_CODE  &&
                        grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    System.out.println("permisos concedidos");
                }else{
                    System.out.println("permisos denegados");
                }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        getBookings();
        conectar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.stopRanging(region);
    }

    public boolean isFinishing (){
        incrementarDecrementarContador(false);
        signOut();
        return true;
    }

    @Override
    public void onResultadoLocal(String urlWeb) {
        if (!urlWeb.startsWith("http://") && !urlWeb.startsWith("https://"))
            urlWeb = "http://" + urlWeb;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlWeb.trim()));
        startActivity(browserIntent);
    }

    @Override
    public void onPersistencia(ArrayList<Local> lista) {
        this.listLocales = lista;
        System.out.println("listaLocales = " + listLocales.size());
    }

    //aumentar/decrementar contador
    public void incrementarDecrementarContador(Boolean isIncrementar){

        DatabaseReference hopperRef = FirebaseDatabase.getInstance().getReference().child("locales_list").child("0");
        Map<String, Object> hopperUpdates = new HashMap<>();
        int nu = foundCapacityActual()+130;

        if(isIncrementar){
            hopperUpdates.put("capacityActual",foundCapacityActual()+1);
        }else{
            hopperUpdates.put("capacityActual",foundCapacityActual()-1);
        }
        hopperRef.updateChildren(hopperUpdates);

    }

    public int foundCapacityActual(){
        int dev = 0;
        for (Local local: listLocales) {
            if(UUID_ID.equalsIgnoreCase(local.getUUID())){
                dev = local.getCapacityActual();
                break;
            }
        }
        System.out.println("dev = " + dev);
        return dev;
    }

   public void nameChanged(){
       mAuth = FirebaseAuth.getInstance();
       NavigationView mNavigationView = findViewById(R.id.nav_view);
       View headerView = mNavigationView.getHeaderView(0);
       TextView userName = headerView.findViewById(R.id.tEmail);
       if(mAuth !=null){
           userName.setText(String.valueOf(mAuth.getCurrentUser().getEmail()));
       }else{
           userName.setText("no email");
       }


   }
    private void signOut() {
        // Firebase sign out
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

    }

    @Override
    public void onResultadoNumberphone2(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        System.out.println("llamada telefono "+phoneNumber);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);

    }
    public void createBBDD(){
        db=openOrCreateDatabase("gestionReservas", Context. MODE_PRIVATE, null);
        String consulta = "CREATE TABLE IF NOT EXISTS bookings (nombreLocal VARCHAR, phone VARCHAR, dateBooking VARCHAR, dateMadeBooking VARCHAR, people Int, hora VARCHAR);";
        db.execSQL(consulta);
        getBookings();
    }
    public void getBookings(){
       listaBookings = new ArrayList<>();
        Cursor c=db.rawQuery("SELECT rowid,* FROM bookings", null);

        if (c.getCount() == 0) {
            System.out.println("NO HAY REGISTROS");
        }else {
            while (c.moveToNext()) {
                Booking book = null;
                book = new Booking(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), c.getString(6));
                listaBookings.add(book);
            }
        }
                c.close();
    }
    public static void addBooking(Booking b){
       listaBookings.add(b);
    }
    public void saveInBBDD(Booking book){
        String consulta = "INSERT INTO bookings VALUES ("+book.getId()+"'"+book.getLocalName()+"', '"+book.getPhone()+"','"+book.getDateBooking()+"','"+book.getMadeDateBooking()+"',"+book.getPeople()+",'"+book.getDateBooking()+"')";
        db.execSQL(consulta);
    }

    @Override
    public void onResultadoDeletBooking(int idBooking) {
        String consulta = "delete from bookings where rowid ="+idBooking;
        db.execSQL(consulta);
        getBookings();
    }
}