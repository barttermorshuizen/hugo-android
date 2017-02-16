package co.moreawesome.hugo.hugo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class VetActivity extends AppCompatActivity {

    private Referral mReferral;

    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // the vet object is stored in preferences - instantiate
        mReferral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));

        mEditTextName = (EditText) findViewById(R.id.name);

        modelToView();

    }

    @Override
    protected void onStop(){
        super.onStop();
        // put the model back in the preferences
        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();
        mReferral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }

    private void modelToView(){
        // copies the model in the view
        mEditTextName.setText(mReferral.getName());
    }

    private void viewToModel() {
        // only pushes the reason and contact preference to the model. It is assumed that the other activities sync to the model when they close
        mReferral.setName(mEditTextName.getText().toString());
    }
}
