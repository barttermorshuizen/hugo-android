package co.moreawesome.hugo.referral;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class TransferActivity extends AppCompatActivity {

    private Referral referral;
    private FloatingActionButton mFab;
    private TextView mProgressTxt;
    private ProgressBar mProgressBar;
    private boolean mailSuccess;

    private class AsyncMailTask extends AsyncTask<Referral, Integer, String> {

        @Override
        protected String doInBackground(Referral... params) {
            if (params.length > 0) {
                publishProgress(0);
                Referral referral = params[0];
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", getString(R.string.smtp_host));
                props.put("mail.smtp.port", getString(R.string.smtp_port));
                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(getString(R.string.smtp_user), getString(R.string.smtp_password));
                            }
                        });
                publishProgress(2);
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(referral.getVetEmail()));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getString(R.string.email_hugo)));
                    message.setSubject("Verwijzing ten behoeve van " + referral.getOwnerName());
                    message.setText(referral.toMessage());

                    Transport.send(message);
                    publishProgress(4);
                    return "";

                } catch (MessagingException e) {
                    publishProgress(4);
                    return e.getLocalizedMessage();
                }
            }
            publishProgress(4);
            return "";
        }


        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }


        protected void onPostExecute(String result){
            mailSuccess = "".equals(result);

            if (mailSuccess) {
                // mail was  successful
                mProgressTxt.setText(R.string.msg_referralsentsuccess);
                mailSuccess = true;
            }
            else {
                // mail was not successful
                mProgressTxt.setText(getString(R.string.msg_referralsenterror));
                mailSuccess = false;
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        referral = new Referral(getSharedPreferences(MainActivity.PREFS_NAME, 0));

        mFab = (FloatingActionButton) findViewById(R.id.fabtransfer);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressTxt =  (TextView) findViewById(R.id.progressTxt);

        mProgressTxt.setText(R.string.msg_referralbeingsent);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mailSuccess) {
                    // only clear form when mail action was successful
                    referral.clear();
                    referral.store(getSharedPreferences(MainActivity.PREFS_NAME, 0));
                }
                finish();
            }
        });

        if (isOnline()) {
            new AsyncMailTask().execute(referral);
        }
        else {
            mailSuccess = false;
            mProgressBar.setVisibility(View.INVISIBLE);
            mProgressTxt.setText(R.string.msg_referralsenterrorconnection);

        }


    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(TransferActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
