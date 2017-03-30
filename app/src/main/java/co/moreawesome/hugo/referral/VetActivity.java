package co.moreawesome.hugo.referral;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VetActivity extends AppCompatActivity implements TextWatcher  {

    private static final String TAG="VetActivity";

    private FloatingActionButton mFab;
    private Referral referral;
    private EditText mEditTextName;
    private EditText mEditTextVetPractice;
    private EditText mEditTextVetPlace;
    private EditText mEditTextVetEmail;
    private TextView mlblName;
    private TextView mlblVetPractice;
    private TextView mlblVetPlace;
    private TextView mlblVetEmail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vet);

        mFab = (FloatingActionButton) findViewById(R.id.fabvet);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // the vet object is stored in preferences - instantiate
        referral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));

        mEditTextName = (EditText) findViewById(R.id.name);
        mEditTextVetPractice = (EditText) findViewById(R.id.vetpractice);
        mEditTextVetPlace = (EditText) findViewById(R.id.vetplace);
        mEditTextVetEmail = (EditText) findViewById(R.id.vetemail);
        mlblName = (TextView) findViewById(R.id.lbl_name);
        mlblVetPractice = (TextView) findViewById(R.id.lbl_vetpractice);
        mlblVetPlace = (TextView) findViewById(R.id.lbl_vetplace);
        mlblVetEmail = (TextView) findViewById(R.id.lbl_vetemail);

        mEditTextName.addTextChangedListener(this);
        mEditTextVetPractice.addTextChangedListener(this);
        mEditTextVetPlace.addTextChangedListener(this);
        mEditTextVetEmail.addTextChangedListener(this);


        modelToView();


    }

    @Override
    protected void onStop(){
        super.onStop();
    }


    @Override
    protected void onPause(){
        super.onPause();

        // put the model back in the preferences
        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();
    }


    private void colorLabelWhenEmpty(TextView label, EditText editText){
        if (editText.getText().toString().isEmpty()){
            label.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            label.setTextColor(getResources().getColor(R.color.light_gray));
        }
    }

    private void colorLabelsWhenEmpty(){
        colorLabelWhenEmpty(mlblVetPractice,mEditTextVetPractice);
        colorLabelWhenEmpty(mlblVetEmail,mEditTextVetEmail);
    }

    @Override
    public void afterTextChanged(Editable s) {
        colorLabelsWhenEmpty();
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        referral.clearVet();
        referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
        modelToView();
    }

    private void modelToView(){
        // copies the model in the view
        mEditTextName.setText(referral.getName());
        mEditTextVetPractice.setText(referral.getVetPractice());
        mEditTextVetPlace.setText(referral.getVetPlace());
        mEditTextVetEmail.setText(referral.getVetEmail());
        colorLabelsWhenEmpty();
    }

    private void viewToModel() {
        //  stores the vetpractice, vetplace and name preferences to the model.
        referral.setName(mEditTextName.getText().toString());
        referral.setVetPractice(mEditTextVetPractice.getText().toString());
        referral.setVetPlace(mEditTextVetPlace.getText().toString());
        referral.setVetEmail(mEditTextVetEmail.getText().toString());
        referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
    }
}
