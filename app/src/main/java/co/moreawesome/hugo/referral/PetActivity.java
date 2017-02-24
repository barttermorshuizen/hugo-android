package co.moreawesome.hugo.referral;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class PetActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG="VetActivity";

    private Referral referral;

    private EditText mEditTextPetName;
    private EditText mEditTextPatientType; // type of patient (e.g. Dog, Cat)
    private EditText mEditTextPatientRace; // race of the patient (e.g. Irish Setter)
    private EditText mEditTextPatientDoB; // Date of birth of the patient
    private RadioButton mRadioButtonGenderM;
    private RadioButton mRadioButtonGenderMG;
    private RadioButton mRadioButtonGenderV;
    private RadioButton mRadioButtonGenderVG;
    private RadioButton mRadioButtonGenderO;
    private TextView lblPatientType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Create");

        setContentView(R.layout.activity_pet);

        // the vet object is stored in preferences - instantiate
        referral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));
        mEditTextPetName = (EditText) findViewById(R.id.patientname);
        mEditTextPatientType = (EditText) findViewById(R.id.patienttype);
        mEditTextPatientRace = (EditText) findViewById(R.id.patientrace);
        mEditTextPatientDoB = (EditText) findViewById(R.id.patientdob);
        mRadioButtonGenderM = (RadioButton) findViewById(R.id.radio_m);
        mRadioButtonGenderMG = (RadioButton) findViewById(R.id.radio_mg);
        mRadioButtonGenderV = (RadioButton) findViewById(R.id.radio_v);
        mRadioButtonGenderVG = (RadioButton) findViewById(R.id.radio_vg);
        mRadioButtonGenderO = (RadioButton) findViewById(R.id.radio_o);
        lblPatientType = (TextView) findViewById(R.id.lbl_patienttype);

        mEditTextPatientType.addTextChangedListener(this);

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

    @Override
    public void afterTextChanged(Editable s) {
        colorLabelsWhenEmpty();
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            clear();
            return true;
        }

        if (id == R.id.action_call) {
            String number = getText(R.string.tel_number).toString();
            Uri call = Uri.parse("tel:" + number);
            Intent surf = new Intent(Intent.ACTION_DIAL, call);
            startActivity(surf);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
    }

    private void colorLabelWhenEmpty(TextView label, EditText editText){
        if (editText.getText().toString().isEmpty()){
            label.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            label.setTextColor(getResources().getColor(R.color.light_gray));
        }
    }

    private void colorLabelsWhenEmpty() {
        colorLabelWhenEmpty(lblPatientType, mEditTextPatientType);
    }

    private void clear(){
        referral.clearPatient();
        referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
        modelToView();
    }

    private void modelToView(){
        // copies the model in the view
        mEditTextPetName.setText(referral.getPatientName());
        mEditTextPatientType.setText(referral.getPatientType());
        mEditTextPatientRace.setText(referral.getPatienRace());
        mEditTextPatientDoB.setText(referral.getPatientDoB());
        switch (referral.getPatientGender()) {
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
        colorLabelsWhenEmpty();
    }

    private void viewToModel() {

        referral.setPatientName(mEditTextPetName.getText().toString());
        referral.setPatientType(mEditTextPatientType.getText().toString());
        referral.setPatienRace(mEditTextPatientRace.getText().toString());
        referral.setPatientDoB(mEditTextPatientDoB.getText().toString());

        String gender="";
        if (mRadioButtonGenderM.isChecked()) gender = "M";
        else if (mRadioButtonGenderMG.isChecked()) gender = "MG";
        else if (mRadioButtonGenderV.isChecked()) gender = "V";
        else if (mRadioButtonGenderVG.isChecked()) gender = "VG";
        else if (mRadioButtonGenderO.isChecked()) gender = "O";
        referral.setPatientGender(gender);

        referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }
}
