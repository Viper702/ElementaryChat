package xyz.n4ve.elementarychat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    EditText username, password, email;
    Button regisButton;
    String email_input, username_input, password_input;


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]

        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_input = email.getText().toString();
                username_input = username.getText().toString();
                password_input = password.getText().toString();
                if (username_input.isEmpty()) username.setError("Can't be blank");
                else if (password_input.isEmpty()) password.setError("Can't be blank");
                else {
                    createAccount(username_input, password_input);
                    Toast.makeText(RegisterActivity.this, "Account Create Successful",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void init() {
        email = (EditText) findViewById(R.id.input_email);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        regisButton = (Button) findViewById(R.id.registerButton);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        // [END create_user_with_email]
    }
}
