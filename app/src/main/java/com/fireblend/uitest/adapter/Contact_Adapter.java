package com.fireblend.uitest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fireblend.uitest.R;
import com.fireblend.uitest.Utils.Utils;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.ui.DetallesContactoActivity;
import com.fireblend.uitest.ui.MainActivity;
import com.fireblend.uitest.ui.ManageContactActivity;

import java.util.List;

/**
 * Created by acamacho on 4/6/2018.
 */

public class Contact_Adapter extends RecyclerView.Adapter<Contact_Adapter.ContatcsViewHolder> {

    List<Contact> myContacts;
    RecyclerView recyclerView;
    Context mContext;
    Button btn;
    private int textSize;
    private String color = "#ffffff";
    Activity activity;
    public Contact_Adapter(List<Contact> contacts, RecyclerView recyclerView, Context context, Activity activity){
        myContacts = contacts;
        this.recyclerView = recyclerView;
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    public ContatcsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        ContatcsViewHolder pvh = new ContatcsViewHolder(v);
        btn = (Button) v.findViewById(R.id.row_btn);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ContatcsViewHolder holder, final int position) {
        holder.name.setText(myContacts.get(position).name);
        holder.age.setText(String.valueOf(myContacts.get(position).age));
        holder.phone.setText(myContacts.get(position).phone);
        holder.email.setText(myContacts.get(position).email);
        holder.provincia.setText(myContacts.get(position).provincia);

        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        holder.age.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        holder.phone.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        holder.email.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        holder.provincia.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);


        holder.name.setBackgroundColor(Color.parseColor(color));
        holder.age.setBackgroundColor(Color.parseColor(color));
        holder.phone.setBackgroundColor(Color.parseColor(color));
        holder.email.setBackgroundColor(Color.parseColor(color));
        holder.provincia.setBackgroundColor(Color.parseColor(color));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("val",position+"");
                Intent i = new Intent(((MainActivity)activity),DetallesContactoActivity.class);

                i.putExtra("name",myContacts.get(position).name);
                i.putExtra("age",String.valueOf(myContacts.get(position).age));
                i.putExtra("phone",myContacts.get(position).phone);
                i.putExtra("email",myContacts.get(position).email);
                i.putExtra("provincia",myContacts.get(position).provincia);
                i.putExtra("userId",myContacts.get(position).userId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                ((MainActivity)activity).finish();
            }
        });

    }


    public void setTextSizes(int textSize) {
        this.textSize = textSize;
        notifyDataSetChanged();
    }

    public void setColor(String COLOR) {
        this.color = COLOR;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return myContacts.size();
    }

    public static class ContatcsViewHolder extends RecyclerView.ViewHolder {


        TextView name, age, phone, email, provincia;
        Button btn;
        ContatcsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            age = (TextView) itemView.findViewById(R.id.age);
            phone = (TextView) itemView.findViewById(R.id.phone);
            email = (TextView) itemView.findViewById(R.id.email);
            provincia = (TextView) itemView.findViewById(R.id.provincia);
            btn = (Button) itemView.findViewById(R.id.row_btn);
        }
    }
}
