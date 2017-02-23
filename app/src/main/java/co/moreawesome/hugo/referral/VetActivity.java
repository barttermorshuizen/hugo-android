package co.moreawesome.hugo.referral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class VetActivity extends AppCompatActivity {

    private static final String TAG="VetActivity";

    private Referral referral;
    private EditText mEditTextName;
    private EditText mEditTextVetPractice;
    private EditText mEditTextVetPlace;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Create");

        setContentView(R.layout.activity_vet);

        // the vet object is stored in preferences - instantiate
        referral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));

        mEditTextName = (EditText) findViewById(R.id.name);
        mEditTextVetPractice = (EditText) findViewById(R.id.vetpractice);
        mEditTextVetPlace = (EditText) findViewById(R.id.vetplace);

        modelToView();

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stop");
    }


    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Pause");

        // put the model back in the preferences
        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();
    }

    private void modelToView(){
        // copies the model in the view
        mEditTextName.setText(referral.getName());
        mEditTextVetPractice.setText(referral.getVetPractice());
        mEditTextVetPlace.setText(referral.getVetPlace());
    }

    private void viewToModel() {
        //  stores the vetpractice, vetplace and name preferences to the model.
        referral.setName(mEditTextName.getText().toString());
        referral.setVetPractice(mEditTextVetPractice.getText().toString());
        referral.setVetPlace(mEditTextVetPlace.getText().toString());
        referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }
}
