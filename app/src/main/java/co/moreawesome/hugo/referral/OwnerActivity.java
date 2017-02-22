package co.moreawesome.hugo.referral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class OwnerActivity extends AppCompatActivity {

    private static final String TAG="OwnerActivity";

    private Referral referral;

    private EditText mEditTextOwnerName;
    private EditText mEditTextOwnerEmail;
    private EditText mEditTextOwnerTel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "Create");

        setContentView(R.layout.activity_owner);

        // the vet object is stored in preferences - instantiate
        referral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));
        mEditTextOwnerName = (EditText) findViewById(R.id.ownername);
        mEditTextOwnerEmail = (EditText) findViewById(R.id.owneremail);
        mEditTextOwnerTel = (EditText) findViewById(R.id.ownertel);

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
        mEditTextOwnerName.setText(referral.getOwnerName());
        mEditTextOwnerEmail.setText(referral.getOwnerEmail());
        mEditTextOwnerTel.setText(referral.getOwnerTel());
    }

    private void viewToModel() {

        referral.setOwnerName(mEditTextOwnerName.getText().toString());
        referral.setOwnerEmail(mEditTextOwnerEmail.getText().toString());
        referral.setOwnerTel(mEditTextOwnerTel.getText().toString());

        referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }
}
