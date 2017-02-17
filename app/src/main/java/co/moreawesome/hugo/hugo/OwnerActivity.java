package co.moreawesome.hugo.hugo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class OwnerActivity extends AppCompatActivity {

    private static final String TAG="OwnerActivity";

    private Referral mReferral;

    private EditText mEditTextOwnerName;
    private EditText mEditTextOwnerTel;
    private EditText mEditTextOwnerEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "Create");

        setContentView(R.layout.activity_owner);

        // the vet object is stored in preferences - instantiate
        mReferral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));
        mEditTextOwnerName = (EditText) findViewById(R.id.ownername);
        mEditTextOwnerTel = (EditText) findViewById(R.id.ownertel);
        mEditTextOwnerEmail = (EditText) findViewById(R.id.owneremail);

        modelToView();
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
        Log.v(TAG, "Stop");
    }

    private void modelToView() {
        // copies the model in the view
        mEditTextOwnerName.setText(mReferral.getOwnerName());
        mEditTextOwnerTel.setText(mReferral.getOwnerTel());
        mEditTextOwnerEmail.setText(mReferral.getOwnerEmail());
    }

    private void viewToModel() {

        mReferral.setOwnerName(mEditTextOwnerName.getText().toString());
        mReferral.setOwnerTel(mEditTextOwnerTel.getText().toString());
        mReferral.setOwnerEmail(mEditTextOwnerEmail.getText().toString());

        mReferral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }
}
