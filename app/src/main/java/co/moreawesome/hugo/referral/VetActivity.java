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

    private Referral mReferral;
    private EditText mEditTextName;
    private Spinner mSpinnerVets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Create");

        setContentView(R.layout.activity_vet);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setHomeButtonEnabled(true);

        // the vet object is stored in preferences - instantiate
        mReferral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));

        mEditTextName = (EditText) findViewById(R.id.name);

        mSpinnerVets = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vets, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinnerVets.setAdapter(adapter);
        mSpinnerVets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mReferral.setVetPractice(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });


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
        mReferral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }

    private void modelToView(){
        // copies the model in the view
        mEditTextName.setText(mReferral.getName());

        int pos = getSpinnerIndex(mSpinnerVets,mReferral.getVetPractice());
        if (pos != -1){
            // we have a valid position
             mSpinnerVets.setSelection(pos);
        }
    }

    private void viewToModel() {
        // only pushes the vet and name preferences to the model.
        mReferral.setName(mEditTextName.getText().toString());
        // it is assumed that with every selection, the model is updated.
    }

    private int getSpinnerIndex(Spinner spinner, String myString) {
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        // Check for this when you set the position.
        return -1;
    }
}
