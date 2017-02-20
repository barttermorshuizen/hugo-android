package co.moreawesome.hugo.referral;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "HugoPreferences";
    private static final String TAG = "MainActivity";

    private Referral referral; // single reference to the model of the app


    // the view objects
    private Button mClearButton;
    private FloatingActionButton mFab;
    private EditText mVet;
    private EditText mReferralReason;
    private EditText mPatient;
    private EditText mOwner;
    private RadioButton mEmail;
    private RadioButton mTel;

    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instantiate model based on preferences
        referral = new Referral(getSharedPreferences(PREFS_NAME, 0));

       setContentView(R.layout.activity_main);

        ab = getSupportActionBar();
        Log.v(TAG,ab.getTitle().toString());


        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail();
            }
        });

        mClearButton = (Button) findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
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


        mReferralReason = (EditText) findViewById(R.id.referral_reason);

        mPatient = (EditText) findViewById(R.id.patient);
        mPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PetActivity.class);
                startActivity(intent);
            }
        });

        mOwner = (EditText) findViewById(R.id.owner);
        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,OwnerActivity.class);
                startActivity(intent);
            }
        });

        mEmail = (RadioButton) findViewById(R.id.radio_email);
        mTel = (RadioButton) findViewById(R.id.radio_tel);

        modelToView();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.v(TAG, "Restart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        referral = new Referral(getSharedPreferences(PREFS_NAME, 0));
        modelToView();
        Log.v(TAG, "Resume");

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Pause");
        viewToModel();
    }



    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Pause");

        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
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


    private void clear(){
        referral.clear();
        referral.store(getSharedPreferences(PREFS_NAME, 0));
        modelToView();
    }

    private void modelToView(){
        // copies the model in the view
        mVet.setText(referral.getVetPractice());
        mReferralReason.setText(referral.getReason());
        if (referral.getPatientType().length()>0) {
            mPatient.setText(referral.getPatientName() + " (" + referral.getPatientType() + ")");
        }
        else {
            mPatient.setText(referral.getPatientName());
        }
        mOwner.setText(referral.getOwnerName());
        mEmail.setChecked(referral.getContactByEmail());
        mTel.setChecked(!referral.getContactByEmail());
    }

    private void viewToModel(){
        // only pushes the reason and contact preference to the model. It is assumed that the other activities sync to the model when they close
        referral.setReason(mReferralReason.getText().toString());
        referral.setContactByEmail(mEmail.isChecked());
        referral.store(getSharedPreferences(PREFS_NAME, 0));
    }

    private void mail(){

        viewToModel();
        // check before mail
        String msg = referral.validateComplete();
        if (msg.equals("Ok")) {

            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setType("*/*");
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"bart@moreawesome.co"});
            if (!referral.getOwnerEmail().isEmpty()){
                i.putExtra(Intent.EXTRA_CC, new String[] {referral.getOwnerEmail()});
            }
            i.putExtra(Intent.EXTRA_SUBJECT, "Verwijzing");
            i.putExtra(Intent.EXTRA_TEXT, referral.toMessage());
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            } else {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // msg contains an error
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
