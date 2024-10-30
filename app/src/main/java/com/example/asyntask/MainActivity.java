package com.example.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView statusText, resultText; // Added resultText
    private Button buttonCount, buttonDownload, buttonTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        statusText = findViewById(R.id.statusText);
        resultText = findViewById(R.id.resultText); // Initialize resultText
        buttonCount = findViewById(R.id.buttonCount);
        buttonTimer = findViewById(R.id.buttonTimer);

        buttonCount.setOnClickListener(v -> new CountTask().execute());
        buttonTimer.setOnClickListener(v -> new TimerTask().execute(5));
    }

    private class CountTask extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            progressBar.setProgress(0);
            statusText.setText("Status: Processing...");
            resultText.setText(""); // Clear previous result
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int total = 0;
            for (int i = 1; i <= 100; i++) {
                total += i;
                publishProgress((i * 100) / 100); // Update progress
                try {
                    Thread.sleep(50); // Simulate a time-consuming process
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return total;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            statusText.setText("Status: Done");
            resultText.setText("Hasil: " + result); // Display result here
        }
    }

    private class TimerTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            progressBar.setProgress(0);
            statusText.setText("Status: Processing...");
            resultText.setText(""); // Clear previous result
        }

        @Override
        protected Void doInBackground(Integer... seconds) {
            for (int i = seconds[0]; i > 0; i--) {
                publishProgress((5 - i + 1) * 20); // Update progress
                try {
                    Thread.sleep(1000); // Wait for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(500); // Vibrate for 500 milliseconds
            }
            statusText.setText("Status: Done");
            resultText.setText("Waktu habis!"); // Display timer message here
        }
    }
}