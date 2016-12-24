package charlyn23.weatherapp;

/**
 * Created by charlynbuchanan on 12/23/16.
 */

//This interface will allow me to pass the data obtained by the AsyncTask to the MainActivity
    //to update the UI.
public  interface AsyncResponse {
    void processFinish(String output);

}
