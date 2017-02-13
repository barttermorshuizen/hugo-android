package co.moreawesome.hugo.hugo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "HugoPreferences";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instantiate model based on preferences
        referral = new Referral(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Snackbar.make(v, "Opening patient form ...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mOwner = (EditText) findViewById(R.id.owner);
        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Opening owner form ...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mEmail = (RadioButton) findViewById(R.id.radio_email);
        mTel = (RadioButton) findViewById(R.id.radio_tel);

        modelToView();
    }

    @Override
    protected void onStop(){
        super.onStop();

        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();


        // store the referral state in the preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        referral.store(editor);

        // Commit the edits!
        editor.commit();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clear(){
        // clears all form fields except the vet
        mReferralReason.setText("");
        // mPatient.setText("");
        // mOwner.setText("");
        mEmail.setChecked(true);
        mTel.setChecked(false);
        // clear the model, except the vet
        viewToModel();
    }

    private void modelToView(){
        // copies the model in the view
        mVet.setText(referral.getVet().getSummary());
        mReferralReason.setText(referral.getReason());
        //TODO owner
        //TODO patient
        mEmail.setChecked(referral.getContactByEmail());
        mTel.setChecked(!referral.getContactByEmail());
    }

    private void viewToModel(){
        // only pushes the reason and contact preference to the model. It is assumed that the other activities sync to the model when they close
        referral.setReason(mReferralReason.getText().toString());
        referral.setContactByEmail(mEmail.isChecked());
    }

    private void mail(){
        viewToModel();
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setType("*/*");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bart@moreawesome.co"});
        i.putExtra(Intent.EXTRA_SUBJECT, "verwijzing");
        i.putExtra(Intent.EXTRA_TEXT   , referral.getReason());
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
        else {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
