package co.moreawesome.hugo.hugo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class VetActivity extends AppCompatActivity {

    private Veterinarian vet;

    private EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // the vet object is stored in preferences - instantiate
        vet = new Veterinarian(this.getParent());

        mName = (EditText) findViewById(R.id.name);

        modelToView();

    }

    @Override
    protected void onStop(){
        super.onStop();
        // put the model back in the preferences
        // save the form in the referral object. The other forms should have saved their state upon closing at all times
        viewToModel();

        // store the referral state in the preferences
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        vet.store(editor);
        // Commit the edits!
        editor.commit();
    }

    private void modelToView(){
        // copies the model in the view
        mName.setText(vet.getName());
    }

    private void viewToModel() {
        // only pushes the reason and contact preference to the model. It is assumed that the other activities sync to the model when they close
        vet.setName(mName.getText().toString());
    }
}
