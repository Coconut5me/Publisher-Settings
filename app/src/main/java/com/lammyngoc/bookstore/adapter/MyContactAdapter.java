package com.lammyngoc.bookstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lammyngoc.bookstore.MyContactAdvancedActivity;
import com.lammyngoc.bookstore.R;
import com.lammyngoc.bookstore.model.MyContact;

public class MyContactAdapter extends ArrayAdapter<MyContact> {
    Activity context;
    int resource;
    public MyContactAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View mycontact_item=inflater.inflate(resource,null);
        TextView txtContactName=mycontact_item.findViewById(R.id.txtContactName);
        TextView txtPhoneNumbe=mycontact_item.findViewById(R.id.txtPhoneNumber);
        ImageView imgCall=mycontact_item.findViewById(R.id.imgCall);
        ImageView imgSms=mycontact_item.findViewById(R.id.imgSms);

        MyContact contact=getItem(position);
        txtContactName.setText(contact.getContactName());
        txtPhoneNumbe.setText(contact.getPhoneNumber());

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyContactAdvancedActivity)context).makeDirectCall(contact);
            }
        });

        imgSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyContactAdvancedActivity)context).makeSms(contact);
            }
        });

        return mycontact_item;
    }
}
