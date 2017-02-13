package co.moreawesome.hugo.hugo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bart on 13/02/2017.
 */

public class Referral {
    private Veterinarian mVet;
    private String mReason;
    //private Patient mPatient;
    //private Owner mOwner;
    private Boolean mContactByEmail;

    public Veterinarian getVet() {
        return mVet;
    }

    public void setVet(Veterinarian vet) {
        mVet = vet;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }

    public Boolean getContactByEmail() {
        return mContactByEmail;
    }

    public void setContactByEmail(Boolean contactByEmail) {
        mContactByEmail = contactByEmail;
    }

    public Referral(Context c) {
       // instantiate from shared preferences
        SharedPreferences settings = c.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        mVet = new Veterinarian(c);
        mReason = settings.getString("reason","");
        //mPatient = new Patient(c);
        //mOwner = new Owner(c);
        mContactByEmail = settings.getBoolean("contact_by_email",true);
    }

    public void store(SharedPreferences.Editor editor){
        if (editor!=null) {
            // store the referral object in the shared preferences and
            mVet.store(editor);
            editor.putString("reason", mReason);
            //mPatient.store(editor);
            //mOwner.store(editor);
            editor.putBoolean("contact_by_email", mContactByEmail);
            editor.commit();
        }
    }
}

