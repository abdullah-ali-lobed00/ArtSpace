
package com.example.artspace;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ArtworkDetailsActivity extends AppCompatActivity {

    private TextView detailsText;
    private Button backButton;
    private Button visitWebsiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artwork_details);

        // Connect XML elements
        detailsText = findViewById(R.id.detailsText);

        backButton = findViewById(R.id.backButton);

        visitWebsiteButton =
                findViewById(R.id.visitWebsiteButton);

        // Receive data from MainActivity
        Intent receivedIntent = getIntent();

        String title = receivedIntent.getStringExtra("title");
        String artist = receivedIntent.getStringExtra("artist");
        String year = receivedIntent.getStringExtra("year");

        // Handle missing data
        if (title == null) {
            title = "Unknown";
        }

        if (artist == null) {
            artist = "Unknown";
        }

        if (year == null) {
            year = "Unknown";
        }

        // Display artwork information
        String information = String.format(
                Locale.getDefault(),
                "Title: %s\n\nArtist: %s\n\nYear: %s",
                title,
                artist,
                year
        );

        detailsText.setText(information);

        // Back to main gallery
        backButton.setOnClickListener(v -> {
            finish();
        });

        // Visit an external website
        visitWebsiteButton.setOnClickListener(v -> {

            Uri websiteUri = Uri.parse(
                    "https://artsandculture.google.com/"
            );

            Intent websiteIntent = new Intent(
                    Intent.ACTION_VIEW,
                    websiteUri
            );

            try {
                startActivity(websiteIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(
                        this,
                        "No browser available",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
