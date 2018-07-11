package com.fireblend.uitest.ui.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fireblend.uitest.R;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.ui.DetallesContactoActivity;
import com.fireblend.uitest.ui.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsFragment extends Fragment {
    private DetallesContactoActivity mActivity;
    View rootView;
    int userid;
    Contact c;
    boolean aparecer;
    @BindView(R.id.nameDetail)
    TextView nameDetail;
    @BindView(R.id.emailD)
    TextView emailD;
    @BindView(R.id.ageDET)
    TextView ageDET;
    @BindView(R.id.numDeta)
    TextView numDeta;
    @BindView(R.id.provD)
    TextView provD;

    @BindView(R.id.buttonDel)
    Button buttonDel;

    @BindView(R.id.descargar)
    Button descargar;
    DetailsInterface detailsInterface;
    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this,rootView);
        c = getArguments().getParcelable("Contact");
        aparecer = getArguments().getBoolean("aparecer");
        if(c!= null){
            nameDetail.setText(c.name);
            ageDET.setText(c.age + "");
            numDeta.setText( c.phone);
            emailD.setText(c.email);
            provD.setText(c.provincia);
        }
        buttonDel.setVisibility(aparecer? View.VISIBLE : View.GONE);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (DetallesContactoActivity) getActivity();
        detailsInterface = (DetailsInterface) context;
    }


    public interface DetailsInterface{
        void decargar();
        void eliminar();
    }


    @OnClick(R.id.descargar)
    public void descargar(){
        detailsInterface.decargar();
    }

    @OnClick(R.id.buttonDel)
    public void eliminar(){
        detailsInterface.eliminar();
    }

}
