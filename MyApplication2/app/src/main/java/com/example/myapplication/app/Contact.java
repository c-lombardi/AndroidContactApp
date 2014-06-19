package com.example.myapplication.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Christopher on 6/17/2014.
 */
public class Contact implements Parcelable {
    private static int Id;
    private static String Name;
    private static String PhoneNumber;
    private static String EmailAddress;

    public String GetName() {
        return Name;
    }

    public String GetPhoneNumber() {
        return PhoneNumber;
    }

    public String GetEmailAddress() {
        return EmailAddress;
    }

    public Integer GetId() {
        return Id;
    }

    public void SetId(Integer i) {
        Id = i;
    }
    public void SetName(String n) {
        Name = n;
    }

    public void SetPhoneNumber(String p) {
        PhoneNumber = p;
    }

    public void SetEmailAddress(String e) {
        EmailAddress = e;
    }

    public Contact()
    {

    }
    public Contact (String n, String p, String e)
    {
        SetName(n);
        SetPhoneNumber(p);
        SetEmailAddress(e);
    }
    public Contact(int i, String n, String p, String e)
    {
        SetId(i);
        SetName(n);
        SetPhoneNumber(p);
        SetEmailAddress(e);
    }
    public Contact(Contact c) {
        SetId(c.GetId());
        SetName(c.GetName());
        SetEmailAddress(c.GetEmailAddress());
        SetPhoneNumber(c.GetPhoneNumber());
    }


    @Override
    public int describeContents() {
        // ignore for now
        return 0;
    }
    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeInt(GetId());
        pc.writeString(GetName());
        pc.writeString(GetPhoneNumber());
        pc.writeString(GetEmailAddress());
    }
/** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel pc) {
            return new Contact(pc);
        }
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

/** Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public Contact(Parcel pc)
        {
        SetId(pc.readInt());
        SetName(pc.readString());
        SetPhoneNumber(pc.readString());
        SetEmailAddress(pc.readString());
    }

    @Override
    public String toString()
    {
        return "Name: " + GetName() + "\nPhone Number: " + GetPhoneNumber() + "\nEmail Address: " + GetEmailAddress() + "\n";
    }
}
