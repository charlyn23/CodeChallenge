package charlyn23.weatherapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by charlynbuchanan on 12/22/16.
 */
//http://api.aerisapi.com/forecasts/11101?client_id=CLIENT_ID&client_secret=CLIENT_SECRET

public class DataFetcher extends AsyncTask<Void, Void, String> {

    public AsyncResponse listener;

    public DataFetcher(AsyncResponse listener){
        this.listener=listener;
    }
    public DataFetcher(){

    }
    private String json;
    AsyncResponse asyncResponse;
    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder response = null;
        String url = "http://api.aerisapi.com/forecasts/11101?client_id=c9F4mx88CN8uIzwgmGK2a&client_secret=R46ni8sIFsgcLFwXXzvQdJVBcov3JJNCEaCbNKtg";
        try {
            URL weatherURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)weatherURL.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("final response" + response);
        json = String.valueOf(response);
        return json;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.processFinish(s);
    }

}

