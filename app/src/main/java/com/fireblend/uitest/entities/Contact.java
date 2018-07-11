package com.fireblend.uitest.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Sergio on 8/20/2017.
 */

@DatabaseTable(tableName = "contacts")
public class Contact implements Parcelable {

    @DatabaseField(generatedId = true, columnName = "user_id", canBeNull = false)
    public int userId;
    @DatabaseField(columnName = "name", canBeNull = false)
    public String name;
    @DatabaseField(columnName = "age", canBeNull = false)
    public int age;
    @DatabaseField(columnName = "email", canBeNull = false)
    public String email;
    @DatabaseField(columnName = "phone", canBeNull = false)
    public String phone;
    @DatabaseField(columnName = "provincia", canBeNull = false)
    public String provincia;

    public Contact(){}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
