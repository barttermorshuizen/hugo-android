package co.moreawesome.hugo.referral;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    public static final String PREFS_NAME = "HugoPreferences";
    private static final String TAG = "MainActivity";

    private Referral referral; // single reference to the model of the app


    // the view objects
    private ScrollView mScrollView;
    private FloatingActionButton mFab;
    private EditText mVet;
    private EditText mReferralReason;
    private EditText mPatient;
    private EditText mOwner;
    private RadioButton mEmail;
    private RadioButton mTel;
    private TextView lblReferralReason;
    private TextView lblVet;
    private TextView lblPatient;
    private TextView lblOwner;

    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instantiate model based on preferences
        referral = new Referral(getSharedPreferences(PREFS_NAME, 0));

       setContentView(R.layout.activity_main);

        ab = getSupportActionBar();


        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewToModel();
                // check before mail
                String msg = referral.validateComplete();
                if (msg.equals("Ok")) {
                    Intent intent = new Intent(MainActivity.this,TransferActivity.class);
                    startActivity(intent);
                }
                else {
                    // msg contains an error
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mVet = (EditText) findViewById(R.id.vet);
        mVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,VetActivity.class);
                startActivity(intent);
            }
        });
        lblVet = (TextView) findViewById(R.id.lbl_vet);
        mVet.addTextChangedListener(this);

        mReferralReason = (EditText) findViewById(R.id.referral_reason);
        lblReferralReason = (TextView) findViewById(R.id.lbl_referral_reason);
        mReferralReason.addTextChangedListener(this);

        mPatient = (EditText) findViewById(R.id.patient);
        mPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                Intent intent = new Intent(MainActivity.this,PetActivity.class);
                startActivity(intent);
            }
        });
        lblPatient = (TextView) findViewById(R.id.lbl_patient);
        mPatient.addTextChangedListener(this);


        mOwner = (EditText) findViewById(R.id.owner);
        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                Intent intent  = new Intent(MainActivity.this,OwnerActivity.class);
                startActivity(intent);
            }
        });
        lblOwner = (TextView) findViewById(R.id.lbl_owner);
        mOwner.addTextChangedListener(this);


        mEmail = (RadioButton) findViewById(R.id.radio_email);
        mTel = (RadioButton) findViewById(R.id.radio_tel);


        modelToView();

        // set correct fonts

    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        referral = new Referral(getSharedPreferences(PREFS_NAME, 0));
        modelToView();

    }

    @Override
    protected void onPause(){
        super.onPause();
        viewToModel();
    }



    @Override
    protected void onStop(){
        super.onStop();

        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        hideKeyboard();
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_email:
                if (checked)
                    //
                    break;
            case R.id.radio_tel:
                if (checked)
                    //
                    break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        hideKeyboard();
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            clear();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Het gehele verwijsformulier wordt gewist. Weet je het zeker?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee", dialogClickListener).show();
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

    @Override
    public void afterTextChanged(Editable s) {
        colorLabelsWhenEmpty();
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    private void clear(){
        referral.clear();
        referral.store(getSharedPreferences(PREFS_NAME, 0));
        modelToView();
    }

    private void colorLabelWhenEmpty(TextView label, EditText editText){
        String v = editText.getText().toString();
        v = v.trim().replaceAll("\n ", "");
        if (v.isEmpty()){
            label.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            label.setTextColor(getResources().getColor(R.color.light_gray));
        }
    }

    private void colorLabelsWhenEmpty(){
        // check vet
        colorLabelWhenEmpty(lblVet,mVet);

        colorLabelWhenEmpty(lblReferralReason,mReferralReason);

        // check owner
        //  owner has either mail or tel number
        if ((referral.getOwnerEmail().isEmpty() && referral.getOwnerTel().isEmpty())
                || referral.getOwnerName().isEmpty()){
            lblOwner.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            lblOwner.setTextColor(getResources().getColor(R.color.light_gray));
        }

        // check patient
        // patient data
        if (referral.getPatientType().isEmpty()){
            lblPatient.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            lblPatient.setTextColor(getResources().getColor(R.color.light_gray));
        }

    }

    private void modelToView(){
        // copies the model in the view
        mVet.setText(referral.getVetPractice());
        mReferralReason.setText(referral.getReason());
        if (referral.getPatientType().length()>0) {
            mPatient.setText(referral.getPatientType());
        }
        else {
            mPatient.setText(referral.getPatientName());
        }

        mOwner.setText(referral.getOwnerName());
        mEmail.setChecked(referral.getContactByEmail());
        mTel.setChecked(!referral.getContactByEmail());
        colorLabelsWhenEmpty();
    }

    private void viewToModel(){
        // only pushes the reason and contact preference to the model. It is assumed that the other activities sync to the model when they close
        referral.setReason(mReferralReason.getText().toString());
        referral.setContactByEmail(mEmail.isChecked());
        referral.store(getSharedPreferences(PREFS_NAME, 0));
    }


    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
