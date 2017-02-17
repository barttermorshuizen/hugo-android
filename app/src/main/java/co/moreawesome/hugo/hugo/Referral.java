package co.moreawesome.hugo.hugo;

import android.content.SharedPreferences;


/**
 * Created by bart on 13/02/2017.
 */

public class Referral {

    private String mName; // name of the referrer
    private String mVetPractice; // name of the practice
    private String mPatientName; // name of the patient
    private String mPatientType; // type of patient (e.g. Dog, Cat)
    private String mPatienRace; // race of the patient (e.g. Irish Setter)
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

    public String getPatientType() {
        return mPatientType;
    }

    public void setPatientType(String patientType) {
        mPatientType = patientType;
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
        mPatientType = prefs.getString("mPatientType","");
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
        editor.putString("mPatientType", mPatientType);
        editor.putString("mPatienRace", mPatienRace);
        editor.putString("mPatientDoB", mPatientDoB);
        editor.putString("mPatientGender", mPatientGender);
        editor.putString("mOwnerName", mOwnerName);
        editor.putString("mOwnerTel", mOwnerTel);
        editor.putString("mOwnerEmail", mOwnerEmail);
        editor.putString("mReason", mReason);
        editor.putBoolean("mContactByEmail", mContactByEmail);

        editor.commit();

    }

    public void clear(){
        mPatientName="";
        mPatientType="";
        mPatienRace="";
        mPatientDoB="";
        mPatientGender="";
        mOwnerName="";
        mOwnerTel="";
        mOwnerEmail="";
        mReason="";
        mContactByEmail=true;
    }

    String validateComplete(){
        // vet data
        if (mVetPractice.isEmpty()){
            return "De praktijk is niet ingevuld";
        }
        // patient data
        if (mPatientType.isEmpty()){
            return "Diersoort is niet ingevuld";
        }
        //  owner has either mail or tel number
        if (mOwnerEmail.isEmpty() && mOwnerTel.isEmpty())
            return "Eigenaar heeft geen contactinformatie";
        // reason is empty
        if (mReason.isEmpty())
            return "Reden verwijzing is niet ingevuld";

        // if we got here, all is cool
        return "Ok";
    }

    String toMessage(){
        /*
            Beste Hugo,
            Hierbij verwijs ik door
            <naam patient>, een <type dier - ras> van geslacht <gender>

            Het gaat om het volgende: <reason>

            Kun je contact opnemen met de eigenaar - naam eigenaar - via email/tel.
            Email: <owner email>.
            Telefoonnummer: <owner tel>.

            Vriendelijke groet,

            <naam>
            */
        String[] lines = new String[13];

        lines[0] = "Beste Hugo, \n";
        lines[1] = "Hierbij verwijs ik door:";
        lines[2] = mPatientName + ", een " + mPatientType + "  (" + mPatienRace + ") van geslacht: " + mPatientGender;
        lines[3] = "";
        lines[4] = "Het gaat om het volgende: " + mReason;
        lines[5] = "";
        lines[6] = "Kun je contact opnemen met de eigenaar - " + mOwnerName + " - via ";
        if (mContactByEmail) lines[6] += "email?";
        else lines[6] += "de telefoon?";
        lines[7] = "Email: " + mOwnerEmail;
        lines[8] = "Tel:" + mOwnerTel;
        lines[9] = "";
        lines[10] = "Vriendelijke groet,";
        lines[11] = "";
        lines[12] = mName;

        String result = lines[0] + "\n"; // initialize with the first line

        for (int i = 1; i<lines.length; i++){
            result += lines[i] + "\n";
        }
        return result;
    }
}

