package co.moreawesome.hugo.referral;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bart on 13/02/2017.
 */

public class Referral {

    private String mName; // name of the referrer
    private String mVetPractice; // name of the practice
    private String mVetPlace; // location of the practice
    private String mVetEmail; // location of the practice
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

    public String getVetPlace() {
        return mVetPlace;
    }

    public void setVetPlace(String vetPlace) {
        mVetPlace = vetPlace;
    }

    public String getVetEmail() {
        return mVetEmail;
    }

    public void setVetEmail(String vetEmail) {
        mVetEmail = vetEmail;
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
        mVetPlace = prefs.getString("mVetPlace","");
        mVetEmail = prefs.getString("mVetEmail","");
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

    public Referral(JSONObject json) {
        try {
            mName = json.getString("name");
            mVetPractice = json.getString("vetpractice");
            mVetPlace = json.getString("vetplace");
            mVetEmail = json.getString("vetemail");
            mPatientName = json.getString("patientname");
            mPatientType = json.getString("patienttype");
            mPatienRace = json.getString("patientrace");
            mPatientDoB = json.getString("patientdob");
            mPatientGender = json.getString("patientgender");
            mOwnerName = json.getString("ownername");
            mOwnerTel = json.getString("ownertel");
            mOwnerEmail = json.getString("owneremail");
            mReason = json.getString("reason");
            mContactByEmail = json.getBoolean("contactbyemail");
        }
        catch (Exception e){
            mName = "";
            mVetPractice = "";
            mVetPlace = "";
            mVetEmail = "";
            mPatientName = "";
            mPatientType = "";
            mPatienRace = "";
            mPatientDoB = "";
            mPatientGender = "";
            mOwnerName = "";
            mOwnerTel = "";
            mOwnerEmail = "";
            mReason = "";
            mContactByEmail = true;
            return;
        }
    }

    public JSONObject toJson(){
        try {
            JSONObject json = new JSONObject();
            json.put("name",mName);
            json.put("vetpractice",mVetPractice);
            json.put("vetplace",mVetPlace);
            json.put("vetemail",mVetEmail);
            json.put("patientname",mPatientName);
            json.put("patienttype",mPatientType);
            json.put("patientrace",mPatienRace);
            json.put("patientdob",mPatientDoB);
            json.put("patientgender",mPatientGender);
            json.put("ownername",mOwnerName);
            json.put("ownertel",mOwnerTel);
            json.put("owneremail",mOwnerEmail);
            json.put("reason",mReason);
            json.put("contactbyemail",mContactByEmail);
            return json;

        }
        catch (JSONException e){
            return null;
        }

    }

    public void store(SharedPreferences prefs){

        SharedPreferences.Editor editor = prefs.edit();

        if (editor==null)
            return;

        editor.putString("mName", mName);
        editor.putString("mVetPractice", mVetPractice);
        editor.putString("mVetPlace", mVetPlace);
        editor.putString("mVetEmail", mVetEmail);
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
        clearPatient();
        clearOwner();
        mReason="";
        mContactByEmail=true;
    }

    public void clearOwner(){
        mOwnerName="";
        mOwnerTel="";
        mOwnerEmail="";
    }

    public void clearPatient(){
        mPatientName="";
        mPatientType="";
        mPatienRace="";
        mPatientDoB="";
        mPatientGender="";
    }

    public void clearVet(){
        mName="";
        mVetPractice="";
        mVetPlace="";
        mVetEmail="";
    }

    String validateComplete(){
        // vet data
        if (mVetPractice.isEmpty()){
            return "Dierenarts - praktijk is niet ingevuld";
        }

        if (mVetEmail.isEmpty()){
            return "Dierenarts - email adres is niet ingevuld";
        }

        // reason is empty
        if (mReason.isEmpty())
            return "Reden verwijzing is niet ingevuld";

        // patient data
        if (mPatientType.isEmpty()){
            return "Patient - diersoort is niet ingevuld";
        }

        // Owner has a name
        if (mOwnerName.isEmpty()){
            return "Eigenaar - de naam van de eigenaar is niet ingevuld";
        }

        //  owner has either mail or tel number
        if (mOwnerEmail.isEmpty() && mOwnerTel.isEmpty())
            return "Eigenaar  - contactinformatie ontbreekt";


        // if we got here, all is cool
        return "Ok";
    }

    String toMessage(){

        List<String> lines = new ArrayList<>();

        lines.add("Beste Hugo, \n");
        lines.add("Hierbij verwijs ik door:");
        if (!mPatientName.isEmpty()){
            lines.add("  - patient: " + mPatientName);
        }
        if (!mPatientType.isEmpty()){
            lines.add("  - soort: " + mPatientType);
        }
        if (!mPatienRace.isEmpty()){
            lines.add("  - ras: " + mPatienRace);
        }
        if (!mPatientGender.isEmpty()){
            lines.add("  - geslacht: " + mPatientGender);
        }

        lines.add("");
        lines.add("Het gaat om het volgende: " + mReason);
        lines.add("");

        if (mContactByEmail){
            lines.add("Kun je contact opnemen met de eigenaar via email?");
        }
        else {
            lines.add("Kun je contact opnemen met de eigenaar via de telefoon?");
        }

        if (!mOwnerName.isEmpty()){
            lines.add("  - naam: " + mOwnerName);
        }

        if (!mOwnerEmail.isEmpty()){
            lines.add("  - email: " + mOwnerEmail);
        }

        if (!mOwnerTel.isEmpty()){
            lines.add("  - tel: " + mOwnerTel);
        }

        lines.add("");
        lines.add("Vriendelijke groet,");
        lines.add("");
        if (!mName.isEmpty()){
            lines.add(mName);
        }
        if (!mVetEmail.isEmpty()){
            lines.add(mVetEmail);
        }
        if (!mVetPractice.isEmpty()){
            lines.add(mVetPractice);
        }
        if (!mVetPlace.isEmpty()){
            lines.add(mVetPlace);
        }

        String result = "";

        for (String item : lines){
            result += item + "\n";
        }

        return result;
    }
}

