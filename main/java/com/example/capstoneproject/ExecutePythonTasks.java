package com.example.capstoneproject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExecutePythonTasks extends AsyncTask<Void, Void, String> {
    private Context mContext;

    // Constructor to receive the context
    public ExecutePythonTasks(Context context) {
        this.mContext = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("http://your_server_ip:5000/execute-python-code"); // Replace with your server URL
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            String response = stringBuilder.toString();
            urlConnection.disconnect();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Handle the response received from the server
        if (result != null) {
            // Process the server's response using the received context
            Toast.makeText(mContext, "Server Response: " + result, Toast.LENGTH_LONG).show();
        } else {
            // Handle if no response or error occurred using the received context
            Toast.makeText(mContext, "No response from the server", Toast.LENGTH_SHORT).show();
        }
    }
}
