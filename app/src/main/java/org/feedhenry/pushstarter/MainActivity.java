package org.feedhenry.pushstarter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private TextView tokenTextView;
    private Button shareButton;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenTextView = (TextView) findViewById(R.id.token);
        shareButton = (Button) findViewById(R.id.shareButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseApp.initializeApp(getApplicationContext());

        try {
            token = FirebaseInstanceId.getInstance().getToken();

            Log.i(this.getLocalClassName(), "FCM Registration Token: " + token);

            tokenTextView.setText(token);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (token != null && token.length() > 0) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "FCM Token");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, token);
                        startActivity(Intent.createChooser(sharingIntent, "Share token"));
                    }
                }
            });

        } catch (Exception e) {
            Log.d(this.getLocalClassName(), "Failed to complete token refresh", e);
        }
    }
}
