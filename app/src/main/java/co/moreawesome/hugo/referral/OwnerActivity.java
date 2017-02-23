package co.moreawesome.hugo.referral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class OwnerActivity extends AppCompatActivity  implements TextWatcher {

    private static final String TAG="OwnerActivity";

    private Referral referral;

    private EditText mEditTextOwnerName;
    private EditText mEditTextOwnerEmail;
    private EditText mEditTextOwnerTel;
    private TextView lblOwnerName;
    private TextView lblOwnerEmail;
    private TextView lblOwnerTel;




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
        lblOwnerName = (TextView) findViewById(R.id.lbl_ownername);
        lblOwnerEmail = (TextView) findViewById(R.id.lbl_owneremail);
        lblOwnerTel = (TextView) findViewById(R.id.lbl_ownertel);

        mEditTextOwnerName.addTextChangedListener(this);
        mEditTextOwnerEmail.addTextChangedListener(this);
        mEditTextOwnerTel.addTextChangedListener(this);

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

    @Override
    public void afterTextChanged(Editable s) {
        colorLabelsWhenEmpty();
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    private void colorLabelWhenEmpty(TextView label, EditText editText){
        if (editText.getText().toString().isEmpty()){
            label.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            label.setTextColor(getResources().getColor(R.color.light_gray));
        }
    }

    private void colorLabelsWhenEmpty(){
        colorLabelWhenEmpty(lblOwnerName,mEditTextOwnerName);
        // when either email or tel is filled, both can be light gray. If both empty, both accent color
        if (!mEditTextOwnerEmail.getText().toString().isEmpty() || !mEditTextOwnerTel.getText().toString().isEmpty()) {
            lblOwnerEmail.setTextColor(getResources().getColor(R.color.light_gray));
            lblOwnerTel.setTextColor(getResources().getColor(R.color.light_gray));
        }
        else {
            lblOwnerEmail.setTextColor(getResources().getColor(R.color.colorAccent));
            lblOwnerTel.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    private void modelToView() {
        // copies the model in the view
        mEditTextOwnerName.setText(referral.getOwnerName());
        mEditTextOwnerEmail.setText(referral.getOwnerEmail());
        mEditTextOwnerTel.setText(referral.getOwnerTel());
        colorLabelsWhenEmpty();
    }

    private void viewToModel() {

        referral.setOwnerName(mEditTextOwnerName.getText().toString());
        referral.setOwnerEmail(mEditTextOwnerEmail.getText().toString());
        referral.setOwnerTel(mEditTextOwnerTel.getText().toString());

        referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }
}
