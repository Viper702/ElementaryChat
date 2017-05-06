package xyz.n4ve.elementarychat;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class SelectActivity extends AppCompatActivity {

    Button boardButton, privateChatButton, signOutButton;
    TextView username_tw, email_tw, uid_tw;

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();
    }

    private void init() {
        username_tw = (TextView) findViewById(R.id.username);
        email_tw = (TextView) findViewById(R.id.email);
        uid_tw = (TextView) findViewById(R.id.uid);
        signOutButton = (Button) findViewById(R.id.signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                finish();
                startActivity(new Intent(SelectActivity.this, LoginActivity.class));
            }
        });
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            String name = user.getDisplayName();
            username_tw.setText("Name : " + name);
            String email = user.getEmail();
            email_tw.setText("Email : " + email);
            String uid = user.getUid();
            uid_tw.setText("UID : " + uid);
            //Uri photoUrl = user.getPhotoUrl();

        }
        /*
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    finish();
                    startActivity(new Intent(SelectActivity.this, LoginActivity.class));
                }
            }
        };
        */

        // [START_EXCLUDE]
        //updateUI(user);
        // [END_EXCLUDE]


        // [END auth_state_listener]
    }

    public void signOut() {
        mAuth.signOut();
    }

}
