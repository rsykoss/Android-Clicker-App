package com.example.clicker;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.widget.Toast;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {


    android.support.v7.widget.CardView btnA, btnB, btnC, btnD;
    TextView txtResponse;
    Button btnSend;
    EditText text;
    int count = 0; // counter value

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //txtResponse = findViewById(R.id.txtResponseId);
        btnA = findViewById(R.id.btnAId);
        btnB = findViewById(R.id.btnBId);
        btnC = findViewById(R.id.btnCId);
        btnD = findViewById(R.id.btnDId);
        btnSend = findViewById(R.id.btnSend);
        text = (EditText)findViewById(R.id.text);

    }

    // ADD the following Button's onClick handler (set in XML layout)
    public void btnOnClickHandler(View v) {
        btnA.setCardBackgroundColor(Color.parseColor("#f44336"));
        btnB.setCardBackgroundColor(Color.parseColor("#2196f3"));
        btnC.setCardBackgroundColor(Color.parseColor("#009688"));
        btnD.setCardBackgroundColor(Color.parseColor("#ff9800"));
        switch (v.getId()) {
            case R.id.btnAId:
                btnA.setCardBackgroundColor(Color.parseColor("#43464b"));
                new HttpTask().execute("http://10.27.86.13:9999/clicker/select?choice=a"); // Send HTTP request
                Toast.makeText(this, "Sent", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnBId:
                btnB.setCardBackgroundColor(Color.parseColor("#43464b"));
                new HttpTask().execute("http://10.27.86.13:9999/clicker/select?choice=b"); // Send HTTP request
                Toast.makeText(this, "Sent", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnCId:
                btnC.setCardBackgroundColor(Color.parseColor("#43464b"));
                new HttpTask().execute("http://10.27.86.13:9999/clicker/select?choice=c"); // Send HTTP request
                Toast.makeText(this, "Sent", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnDId:
                btnD.setCardBackgroundColor(Color.parseColor("#43464b"));
                new HttpTask().execute("http://10.27.86.13:9999/clicker/select?choice=d"); // Send HTTP request
                Toast.makeText(this, "Sent", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnSend:
                String comment = text.getText().toString();
                new HttpTask().execute("http://10.27.86.13:9999/clicker/select?comment=" + comment); // Send HTTP request
                Toast.makeText(this, "Sent", Toast.LENGTH_LONG).show();
                break;
        }
    }
    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strURLs) {
            URL url = null;
            HttpURLConnection conn = null;
            try {
                url = new URL(strURLs[0]);
                conn = (HttpURLConnection) url.openConnection();
                // Get the HTTP response code (e.g., 200 for "OK", 404 for "Not found")
                // and pass a string description in result to onPostExecute(String result)
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {  // 200
                    return "OK (" + responseCode + ")";
                } else {
                    return "Fail (" + responseCode + ")";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // Displays the result of the AsyncTask.
        // The String result is passed from doInBackground().
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            //txtResponse.setText(result);  // put it on TextView
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Message");
            if(result.equals("OK (200)"))
                alertDialog.setMessage("Submitted!");
            else
                alertDialog.setMessage("Something went wrong:( Please try again!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }
}
