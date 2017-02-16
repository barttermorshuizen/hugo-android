package co.moreawesome.hugo.hugo;

import android.content.SharedPreferences;


/**
 * Created by bart on 13/02/2017.
 */

public class Referral {

    private String mName; // name of the referrer
    private String mVetPractice; // name of the practice
    private String mPatientName; // name of the patient
    private String mPatienRace; // race of the patient
    private String mPatientDoB; // Date of birth of the patient
    private String mPatientGender; // Gender of patient
    private String mOwnerName; // name of the owner
    private String mOwnerTel; // telephone number of the owner
    private String mOwnerEmail; // email pof the owner
    private String mReason;
    private Boolean mContactByEmail;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getVetPractice() {
        return mVetPractice;
    }

    public void setVetPractice(String vetPractice) {
        mVetPractice = vetPractice;
    }

    public String getPatientName() {
        return mPatientName;
    }

    public void setPatientName(String patientName) {
        mPatientName = patientName;
    }

    public String getPatienRace() {
        return mPatienRace;
    }

    public void setPatienRace(String patienRace) {
        mPatienRace = patienRace;
    }

    public String getPatientDoB() {
        return mPatientDoB;
    }

    public void setPatientDoB(String patientDoB) {
        mPatientDoB = patientDoB;
    }

    public String getPatientGender() {
        return mPatientGender;
    }

    public void setPatientGender(String patientGender) {
        mPatientGender = patientGender;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public void setOwnerName(String ownerName) {
        mOwnerName = ownerName;
    }

    public String getOwnerTel() {
        return mOwnerTel;
    }

    public void setOwnerTel(String ownerTel) {
        mOwnerTel = ownerTel;
    }

    public String getOwnerEmail() {
        return mOwnerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        mOwnerEmail = ownerEmail;
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

    public Referral(SharedPreferences prefs) {
       // instantiate from shared preferences
        mName = prefs.getString("mName","");
        mVetPractice = prefs.getString("mVetPractice","");
        mPatientName = prefs.getString("mPatientName","");
        mPatienRace = prefs.getString("mPatienRace","");
        mPatientDoB = prefs.getString("mPatientDoB","");
        mPatientGender = prefs.getString("mPatientGender","");
        mOwnerName = prefs.getString("mOwnerName","");
        mOwnerTel = prefs.getString("mOwnerTel","");
        mOwnerEmail = prefs.getString("mOwnerEmail","");
        mReason = prefs.getString("mReason","");
        mContactByEmail = prefs.getBoolean("mContactByEmail",true);
    }

    public void store(SharedPreferences prefs){

        SharedPreferences.Editor editor = prefs.edit();

        if (editor==null)
            return;

        editor.putString("mName", mName);
        editor.putString("mVetPractice", mVetPractice);
        editor.putString("mPatientName", mPatientName);
        editor.putString("mPatienRace", mPatienRace);
        editor.putString("mPatientDoB", mPatientDoB);
        editor.putString("mPatientGender", mPatientGender);
        editor.putString("mOwnerName", mOwnerName);
        editor.putString("mOwnerTel", mOwnerTel);
        editor.putString("mOwnerEmail", mOwnerEmail);
        editor.putString("reason", mReason);
        editor.putBoolean("contact_by_email", mContactByEmail);

        editor.commit();

    }
}
