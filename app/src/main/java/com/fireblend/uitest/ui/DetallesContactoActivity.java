package com.fireblend.uitest.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fireblend.uitest.R;
import com.fireblend.uitest.Utils.Utils;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.helpers.GestorContactos;
import com.fireblend.uitest.helpers.PreferencesManager;
import com.fireblend.uitest.ui.fragments.DetailsFragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetallesContactoActivity extends AppCompatActivity implements  DetailsFragment.DetailsInterface{
    private final int HOME = 16908332;
    MaterialDialog materialDialog;
    PreferencesManager preferencesManager;
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private static final int PERM_CODE = 1001;
    GestorContactos gestorContactos;



    Contact c;
    int userid;
    boolean aparecer;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_contacto);
        ButterKnife.bind(this);
        preferencesManager = PreferencesManager.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        utils = new Utils(this);
        gestorContactos = GestorContactos.getInstance();
        gestorContactos.startHelper(getApplicationContext());
        aparecer= preferencesManager.getBoolean(this,preferencesManager.ARG_SHOWHIDE);
        if(savedInstanceState == null){
            userid = getIntent().getExtras().getInt("userId");
            c = new Contact();
            c.age = Integer.parseInt(getIntent().getExtras().getString("age"));
            c.email = getIntent().getExtras().getString("email");
            c.name = getIntent().getExtras().getString("name");
            c.provincia = getIntent().getExtras().getString("provincia");
            c.phone = getIntent().getExtras().getString("phone");
        }
        //buttonDel.setVisibility(aparecer? View.VISIBLE : View.GONE);
        setFragment(new DetailsFragment());
    }




    private void askForPermission() {
        //Se solicita permiso. Esta llamada es asincronica, por lo que tenemos que
        //implementar el metodo callback onRequestPermissionResult para procesar la
        //respuesta del usuario (ver abajo)
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERM_CODE);
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Downloads");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Se ha guardado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Log.d("id", String.valueOf(item.getItemId()));

        switch (item.getItemId()) {
            case HOME:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Si recibimos al menos un permiso y su valor es igual a PERMISSION_GRANTED, tenemos permiso
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
               decargar();
        } else {
            Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
        }
    }


    public void setFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Contact", c);
        bundle.putBoolean("aparecer",aparecer);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void decargar() {
        int permissionCheck = ContextCompat.checkSelfPermission(DetallesContactoActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre: " + c.name + "\n");
            sb.append("Num Tel: " + c.phone + "\n");
            sb.append("Correo: " + c.email + "\n");
            sb.append("Edad: " + c.age + "\n");
            sb.append("Provincia: " + c.provincia + "\n");
            generateNoteOnSD(getApplicationContext(),c.name + c.phone + ".txt", sb.toString() );
        } else {
            //Si no, pedimos permiso
            askForPermission();
        }
    }

    @Override
    public void eliminar() {
        materialDialog = new MaterialDialog.Builder(this)
                .title("Eliminar Contacto")
                .content("Realmente desea eliminar el Contacto " + c.name)
                .positiveText("Eliminar")
                .negativeText("Cancelar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        utils.showProgess("Eliminando Usuario");
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                gestorContactos.deleteContact(userid);
                                Intent i = new Intent(DetallesContactoActivity.this,MainActivity.class);
                                utils.hideProgress();
                                startActivity(i);
                                finish();
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, SPLASH_SCREEN_DELAY);
                    }

                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }
}
