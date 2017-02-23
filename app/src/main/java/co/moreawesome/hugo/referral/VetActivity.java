package co.moreawesome.hugo.referral;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

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


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (referral.getName().isEmpty() || "".equals(referral.getName())) {
                        try {
                            Cursor c = getApplication().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
                            if (c!=null && c.getCount()>0) {
                                c.moveToFirst();
                                referral.setName(c.getString(c.getColumnIndex("display_name")));
                                modelToView();
                            }
                            if (c!=null) {
                                c.close();
                            }
                        }
                        catch (SecurityException se){
                            Log.v(TAG,"Security exception :" + se.getMessage());
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
