package com.fireblend.uitest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.fireblend.uitest.R;
import com.fireblend.uitest.adapter.Contact_Adapter;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.helpers.GestorContactos;
import com.fireblend.uitest.helpers.PreferencesManager;
import com.j256.ormlite.stmt.query.In;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity {

    private MainActivity mActivity;
    RecyclerView list;
    Contact_Adapter adapter;
    ArrayList<Contact> contacts;
    GestorContactos gestorContactos;
    PreferencesManager preferencesManager;
    //@BindView(R.id.lvError)
    RelativeLayout relativeLayout;

    int textSize = 1;
    String textSizeString = "";
    String color;
    int cols;
    String colsString;

    @BindView(R.id.pa)
    RelativeLayout pa;

    @Override
    @Optional
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gestorContactos = GestorContactos.getInstance();
        gestorContactos.startHelper(getApplicationContext());
        contacts = gestorContactos.getContacts();
        preferencesManager = PreferencesManager.getInstance();

        textSizeString = preferencesManager.getStringValue(this,preferencesManager.ARG_FONTSIZE);
        textSize = textSizeString.equals("") ? 12 : Integer.parseInt(textSizeString);
        color = preferencesManager.getStringValue(this,preferencesManager.ARG_BACKGROUNDCOLOR);
        colsString = preferencesManager.getStringValue(this,preferencesManager.ARG_COLUMNS);
        cols = colsString.equals("Lista") ? 1 : 2;

        if(color.equals("")){
            color = "#ffffff";
        }


        pa.setBackgroundColor(Color.parseColor(color));

        relativeLayout = (RelativeLayout) findViewById(R.id.lvError);
        list = (RecyclerView)findViewById(R.id.lista_contactos);
        list.setHasFixedSize(true);
        list.setItemViewCacheSize(5);
        list.setDrawingCacheEnabled(true);
        list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        setupList(contacts == null || contacts.size() == 0 ? true: false);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManageContactActivity.class );
                startActivity(intent);
                finish();
            }
        });

    }




    private void setupList(boolean itsEmpty) {
        if(itsEmpty){
            contacts = new ArrayList<Contact>();
            relativeLayout.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }else{
            relativeLayout.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }

        //poner el valor de las preferencias
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, cols);

        list.setLayoutManager(mLayoutManager);
        adapter = new Contact_Adapter(contacts,list,getApplicationContext(),this);
        adapter.setTextSizes(this.textSize);
        adapter.setColor(this.color);
        list.setAdapter(adapter);


    }




    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
       // saveState.putParcelableArrayList("contacts", (ArrayList<? extends Parcelable>) contacts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class );
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
