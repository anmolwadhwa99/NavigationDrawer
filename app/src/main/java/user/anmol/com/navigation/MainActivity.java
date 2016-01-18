package user.anmol.com.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText recipientEditText;
    private EditText subjectEditText;
    private EditText bodyEditText;
    private boolean isEmailCorrect;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipientEditText = (EditText) findViewById(R.id.recipient_id);
        subjectEditText = (EditText) findViewById(R.id.subject_id);
        bodyEditText = (EditText) findViewById(R.id.body_id);
        parentView =  findViewById(R.id.root_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValidEmail();
                if (isEmailCorrect == true) {
                    sendEmail();
                }
            }
        });
    }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void isValidEmail(){
        Snackbar snackbar = Snackbar.make(parentView,"Please enter in a valid email address",Snackbar.LENGTH_LONG);
        Snackbar snackbar2 = Snackbar.make(parentView, "Please enter something in the body", Snackbar.LENGTH_LONG);
        Snackbar snackbar3 = Snackbar.make(parentView, "Please enter something in the subject", Snackbar.LENGTH_LONG);
        if(recipientEditText.getText().toString().isEmpty()){
            isEmailCorrect = false;
            snackbar.show();
        }else{
            if(Patterns.EMAIL_ADDRESS.matcher(recipientEditText.getText().toString()).matches() == false){
                isEmailCorrect = false;
                snackbar.show();
            }else{
                isEmailCorrect = true;
            }
        }
        if(bodyEditText.getText().toString().isEmpty()){
            snackbar2.show();
            isEmailCorrect = false;
        }
        if(subjectEditText.getText().toString().isEmpty()){
            snackbar3.show();
            isEmailCorrect = false;
        }
    }

    protected void sendEmail() {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEditText.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectEditText.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, bodyEditText.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
