package co.moreawesome.hugo.hugo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Created by bart on 13/02/2017.
 */

public class Veterinarian implements Serializable {
    private String mName;
    private String mPractice;
    private String mAddressStreet;
    private String mAddressNumber;
    private String mAddressPostalCode;
    private String mAddressPlace;
    private String mTelephone;
    private String mEmail;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPractice() {
        return mPractice;
    }

    public void setPractice(String practice) {
        mPractice = practice;
    }

    public String getAddressStreet() {
        return mAddressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        mAddressStreet = addressStreet;
    }

    public String getAddressNumber() {
        return mAddressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        mAddressNumber = addressNumber;
    }

    public String getAddressPostalCode() {
        return mAddressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        mAddressPostalCode = addressPostalCode;
    }

    public String getAddressPlace() {
        return mAddressPlace;
    }

    public void setAddressPlace(String addressPlace) {
        mAddressPlace = addressPlace;
    }

    public String getTelephone() {
        return mTelephone;
    }

    public void setTelephone(String telephone) {
        mTelephone = telephone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Veterinarian(Context c) {
        if (c != null) {
            SharedPreferences settings = c.getSharedPreferences(MainActivity.PREFS_NAME, 0);
            mName = settings.getString("vet_name", "");
            mPractice = settings.getString("vet_practice", "");
            mAddressStreet = settings.getString("vet_address_street", "");
            mAddressNumber = settings.getString("vet_address_number", "");
            mAddressPostalCode = settings.getString("vet_address_postalcode", "");
            mAddressPlace = settings.getString("vet_address_place", "");
            mTelephone = settings.getString("vet_telephone", "");
            mEmail = settings.getString("vet_email", "");
        }
        // temp code
        mName = "Herbert Blankensteijn";
        mPractice = "De Vries";
        mAddressStreet = "Rozengracht";
        mAddressNumber = "184";
        mAddressPostalCode = "1014 XR";
        mAddressPlace = "Amsterdam";
        mTelephone ="020-7865432";
        mEmail = "herbert@devries-dierenziekenhuizen.nl";
    }

    public String getSummary(){
        // returns the name and practice name
        return mName + " - " + mPractice;
    }

    public void store(SharedPreferences.Editor editor){
        if (editor != null) {
            // stores in the editor - does NOT commit!
            editor.putString("vet_name", mName);
            editor.putString("vet_practice", mPractice);
            editor.putString("vet_address_street", mAddressStreet);
            editor.putString("vet_address_number", mAddressNumber);
            editor.putString("vet_address_postalcode", mAddressPostalCode);
            editor.putString("vet_address_place", mAddressPlace);
            editor.putString("vet_telephone", mTelephone);
            editor.putString("vvet_email", mEmail);
        }
        return;
    }


}
