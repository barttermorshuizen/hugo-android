package co.moreawesome.hugo.referral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class PetActivity extends AppCompatActivity {

    private static final String TAG="VetActivity";

    private Referral mReferral;

    private EditText mEditTextPetName;
    private EditText mEditTextPatientType; // type of patient (e.g. Dog, Cat)
    private EditText mEditTextPatientRace; // race of the patient (e.g. Irish Setter)
    private EditText mEditTextPatientDoB; // Date of birth of the patient
    private RadioButton mRadioButtonGenderM;
    private RadioButton mRadioButtonGenderMG;
    private RadioButton mRadioButtonGenderV;
    private RadioButton mRadioButtonGenderVG;
    private RadioButton mRadioButtonGenderO;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Create");

        setContentView(R.layout.activity_pet);

        // the vet object is stored in preferences - instantiate
        mReferral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));
        mEditTextPetName = (EditText) findViewById(R.id.patientname);
        mEditTextPatientType = (EditText) findViewById(R.id.patienttype);
        mEditTextPatientRace = (EditText) findViewById(R.id.patientrace);
        mEditTextPatientDoB = (EditText) findViewById(R.id.patientdob);
        mRadioButtonGenderM = (RadioButton) findViewById(R.id.radio_m);
        mRadioButtonGenderMG = (RadioButton) findViewById(R.id.radio_mg);
        mRadioButtonGenderV = (RadioButton) findViewById(R.id.radio_v);
        mRadioButtonGenderVG = (RadioButton) findViewById(R.id.radio_vg);
        mRadioButtonGenderO = (RadioButton) findViewById(R.id.radio_o);

        modelToView();

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Pause");

        // put the model back in the preferences
        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stop");
    }

    public void onRadioButtonClicked(View view) {
    }


    private void modelToView(){
        // copies the model in the view
        mEditTextPetName.setText(mReferral.getPatientName());
        mEditTextPatientType.setText(mReferral.getPatientType());
        mEditTextPatientRace.setText(mReferral.getPatienRace());
        mEditTextPatientDoB.setText(mReferral.getPatientDoB());
        switch (mReferral.getPatientGender()) {
            case "M":
                mRadioButtonGenderM.setChecked(true);
                break;
            case "MG":
                mRadioButtonGenderMG.setChecked(true);
                break;
            case "V":
                mRadioButtonGenderV.setChecked(true);
                break;
            case "VG":
                mRadioButtonGenderVG.setChecked(true);
                break;
            case "O":
                mRadioButtonGenderO.setChecked(true);
                break;
        }
    }

    private void viewToModel() {

        mReferral.setPatientName(mEditTextPetName.getText().toString());
        mReferral.setPatientType(mEditTextPatientType.getText().toString());
        mReferral.setPatienRace(mEditTextPatientRace.getText().toString());
        mReferral.setPatientDoB(mEditTextPatientDoB.getText().toString());

        String gender="";
        if (mRadioButtonGenderM.isChecked()) gender = "M";
        else if (mRadioButtonGenderMG.isChecked()) gender = "MG";
        else if (mRadioButtonGenderV.isChecked()) gender = "V";
        else if (mRadioButtonGenderVG.isChecked()) gender = "VG";
        else if (mRadioButtonGenderO.isChecked()) gender = "O";
        mReferral.setPatientGender(gender);

        mReferral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }
}
