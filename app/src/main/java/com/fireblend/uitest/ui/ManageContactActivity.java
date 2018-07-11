package com.fireblend.uitest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.R;
import com.fireblend.uitest.Utils.Utils;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.helpers.GestorContactos;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageContactActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.addNumber)
    EditText number;

    @BindView(R.id.addName)
    EditText name;

    @BindView(R.id.addEmail)
    EditText email;

    @BindView(R.id.addAge)
    SeekBar age;

    @BindView(R.id.TextEdad)
    TextView txt;

    private final int HOME = 16908332;
    String item;
    ArrayList<Contact> contacts;
    Utils utils;
    private static final long SPLASH_SCREEN_DELAY = 3000;
    GestorContactos gestorContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_contact);
        ButterKnife.bind(this);
        gestorContactos = GestorContactos.getInstance();
        gestorContactos.startHelper(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.setTitle("Agregar Contacto");
        spinner.setOnItemSelectedListener(this);
        crearProvincias();
        if(contacts == null)
            contacts = new ArrayList<Contact>();
        utils = new Utils(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Log.d("id", String.valueOf(item.getItemId()));

        switch (item.getItemId()) {
            case HOME:
                Intent i = new Intent(this,MainActivity.class);
                //i.putParcelableArrayListExtra("contacts",contacts);
                utils.hideProgress();
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        Log.d("item",item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void crearProvincias(){
        List<String> provincias = new ArrayList<String>();
        provincias.add("San José");
        provincias.add("Alajuela");
        provincias.add("Cartago");
        provincias.add("Heredia");
        provincias.add("Limón");
        provincias.add("Puntarenas");
        provincias.add("Guanacaste");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provincias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.addButton)
    public void addNew(){
        if(valdiateFields()){
            utils.showProgess("Creando Usuario");
            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    Contact c = new Contact();
                    c.name = name.getText().toString();
                    c.email = email.getText().toString();
                    c.phone = number.getText().toString();
                    c.provincia = item;
                    c.age = age.getProgress();
                    gestorContactos.saveContact(c);

                    Intent i = new Intent(ManageContactActivity.this,MainActivity.class);
                    //i.putParcelableArrayListExtra("contacts",contacts);
                    utils.hideProgress();
                    startActivity(i);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }
    }


    private boolean valdiateFields(){
        if(this.number.getText().toString().equals("")){
            Toast.makeText(this,"El Número de telefono no puede ser nulo",Toast.LENGTH_LONG).show();

            return false;
        }
        /*if(this.age.getText().toString().equals("")){
            Toast.makeText(this,"La edad no puede ser nulo",Toast.LENGTH_LONG).show();
            return false;
        }*/
        if(this.email.getText().toString().equals("")){
            Toast.makeText(this,"El correo electrónico no puede ser nulo",Toast.LENGTH_LONG).show();
            return false;
        }
        if(this.name.getText().toString().equals("")){
            Toast.makeText(this,"El Nombre no puede ser nulo",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;


    }
}
