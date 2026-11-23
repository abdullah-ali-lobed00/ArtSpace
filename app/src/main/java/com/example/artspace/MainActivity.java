package com.example.artspace;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "currentIndex";

    private int currentIndex = 0;

    private ImageView artworkImage;
    private TextView artworkCounter;
    private TextView artworkTitle;
    private TextView artworkInfo;
    private Button previousButton;
    private Button nextButton;

    // Six artwork images
    private final int[] images = {
            R.drawable.art_1,
            R.drawable.art_2,
            R.drawable.art_3,
            R.drawable.art_4,
            R.drawable.art_5,
            R.drawable.art_6
    };

    // Artwork titles
    private final String[] titles = {
            "Mona Lisa",
            "The Starry Night",
            "Girl with a Pearl Earring",
            "The Great Wave",
            "The Scream",
            "The Birth of Venus"
    };

    // Artist names
    private final String[] artists = {
            "Leonardo da Vinci",
            "Vincent van Gogh",
            "Johannes Vermeer",
            "Katsushika Hokusai",
            "Edvard Munch",
            "Sandro Botticelli"
    };

    // Creation years
    private final String[] years = {
            "c. 1503",
            "1889",
            "c. 1665",
            "c. 1831",
            "1893",
            "c. 1485"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Connect Java components with XML
        artworkImage = findViewById(R.id.artworkImage);
        artworkCounter = findViewById(R.id.artworkCounter);
        artworkTitle = findViewById(R.id.artworkTitle);
        artworkInfo = findViewById(R.id.artworkInfo);

        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        // Restore selected artwork after rotation
        if (savedInstanceState != null) {
            currentIndex =
                    savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // Next artwork
        nextButton.setOnClickListener(v -> {
            if (currentIndex < images.length - 1) {
                currentIndex++;
                updateArtwork();
            }
        });

        // Previous artwork
        previousButton.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateArtwork();
            }
        });

        // Open artwork details using Explicit Intent
        artworkImage.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ArtworkDetailsActivity.class
            );

            intent.putExtra("title", titles[currentIndex]);
            intent.putExtra("artist", artists[currentIndex]);
            intent.putExtra("year", years[currentIndex]);

            startActivity(intent);
        });

        // Display selected artwork
        updateArtwork();
    }

    // Update image, title, artist, year and counter
    private void updateArtwork() {

        artworkImage.setImageResource(images[currentIndex]);

        artworkImage.setContentDescription(
                titles[currentIndex]
        );

        artworkTitle.setText(titles[currentIndex]);

        artworkInfo.setText(
                String.format(
                        Locale.getDefault(),
                        "%s (%s)",
                        artists[currentIndex],
                        years[currentIndex]
                )
        );

        artworkCounter.setText(
                String.format(
                        Locale.getDefault(),
                        "%d / %d",
                        currentIndex + 1,
                        images.length
                )
        );

        // Disable Previous on first artwork
        previousButton.setEnabled(currentIndex > 0);

        // Disable Next on last artwork
        nextButton.setEnabled(
                currentIndex < images.length - 1
        );
    }

    // Preserve state during screen rotation
    @Override
    protected void onSaveInstanceState(
            @NonNull Bundle outState
    ) {
        outState.putInt(KEY_INDEX, currentIndex);
        super.onSaveInstanceState(outState);
    }
}
