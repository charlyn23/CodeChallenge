package charlyn23.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    public TextView blurb;
    public TextView dayZeroDateTextView;
    public TextView dayZeroMinTextView;
    public TextView dayZeroMaxTextView;
    public TextView dayOneMinTextView;
    public TextView dayOneMaxTextView;
    public TextView dayOneDay;
    public TextView dayTwoMinTextView;
    public TextView dayTwoMaxTextView;
    public TextView dayTwoDay;
    public TextView dayThreeMinTextView;
    public TextView dayThreeMaxTextView;
    public TextView dayThreeDay;
    public TextView dayFourMinTextView;
    public TextView dayFourMaxTextView;
    public TextView dayFourDay;
    public TextView dayFiveMinTextView;
    public TextView dayFiveMaxTextView;
    public TextView dayFiveDay;
    public TextView daySixMinTextView;
    public TextView daySixMaxTextView;
    public TextView daySixDay;
    public Button tempScale;
    public ImageView dayZeroImage;
    public ImageView dayOneImage;
    public ImageView dayTwoImage;
    public ImageView dayThreeImage;
    public ImageView dayFourImage;
    public ImageView dayFiveImage;
    public ImageView daySixImage;
    public boolean farenheit;
    public String todayString;

    public static final String TAG = "MainActivity";
    DataFetcher dataFetcher = new DataFetcher();
    AsyncResponse asyncResponse = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            System.out.println(output);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataFetcher = new DataFetcher();
        dataFetcher.listener = this;
        dataFetcher.execute();
        farenheit = true;

        tempScale = (Button) findViewById(R.id.tempScaleButton);
        tempScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (farenheit) {
                    setToCelisius();
                    farenheit = false;
                    tempScale.setBackgroundResource(R.drawable.button_pressed);
                } else {
                    farenheit = true;
                    tempScale.setBackgroundResource(R.drawable.button_default);
                    setToFarenheit();
                }
            }
        });
        blurb = (TextView) findViewById(R.id.blurb);
        dayZeroDateTextView = (TextView) findViewById(R.id.dayZeroDate);
        dayZeroImage = (ImageView) findViewById(R.id.dayZeroImage);
        dayZeroMaxTextView = (TextView) findViewById(R.id.dayZeroMaxTemp);
        dayZeroMinTextView = (TextView) findViewById(R.id.dayZeroMinTemp);

        dayOneImage = (ImageView) findViewById(R.id.dayOneImage);
        dayOneMinTextView = (TextView) findViewById(R.id.dayOneMinTemp);
        dayOneMaxTextView = (TextView) findViewById(R.id.dayOneMaxTemp);
        dayOneDay = (TextView) findViewById(R.id.dayOneDay);

        dayTwoImage = (ImageView) findViewById(R.id.dayTwoImage);
        dayTwoMinTextView = (TextView) findViewById(R.id.dayTwoMinTemp);
        dayTwoMaxTextView = (TextView) findViewById(R.id.dayTwoMaxTemp);
        dayTwoDay = (TextView) findViewById(R.id.dayTwoDay);

        dayThreeImage = (ImageView) findViewById(R.id.dayThreeImage);
        dayThreeMinTextView = (TextView) findViewById(R.id.dayThreeMinTemp);
        dayThreeMaxTextView = (TextView) findViewById(R.id.dayThreeMaxTemp);
        dayThreeDay = (TextView) findViewById(R.id.dayThreeDay);

        dayFourImage = (ImageView) findViewById(R.id.dayFourImage);
        dayFourMinTextView = (TextView) findViewById(R.id.dayFourMinTemp);
        dayFourMaxTextView = (TextView) findViewById(R.id.dayFourMaxTemp);
        dayFourDay = (TextView) findViewById(R.id.dayFourDay);

        dayFiveImage = (ImageView) findViewById(R.id.dayFiveImage);
        dayFiveMinTextView = (TextView) findViewById(R.id.dayFiveMinTemp);
        dayFiveMaxTextView = (TextView) findViewById(R.id.dayFiveMaxTemp);
        dayFiveDay = (TextView) findViewById(R.id.dayFiveDay);

        daySixImage = (ImageView) findViewById(R.id.daySixImage);
        daySixMinTextView = (TextView) findViewById(R.id.daySixMinTemp);
        daySixMaxTextView = (TextView) findViewById(R.id.daySixMaxTemp);
        daySixDay = (TextView) findViewById(R.id.daySixDay);


        //Calendar.getDay() is deprecated. I created the following algorithm as a workaround.
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        Log.i("Today", String.valueOf(today));
        ArrayList<String> week = new ArrayList<>();
        week.add("foo"); //placeholder so indices and day int are the same
        week.add("Sun");
        week.add("Mon");
        week.add("Tues");
        week.add("Wed");
        week.add("Thurs");
        week.add("Fri");
        week.add("Sat");

        todayString = week.get(today);

        ArrayList<String> modifiedWeekArrayList = new ArrayList<>();
        for (int i = today; i < week.size(); i++){
            modifiedWeekArrayList.add(week.get(i));
            System.out.println("modWeekArrayList day" + i + " = " + week.get(i));
        }
        for (int i = 1; i <= today; i++) {
            modifiedWeekArrayList.add(week.get(i));
            System.out.println("modWeekArrayList day" + i + " = " + week.get(i));
        }
        ArrayList<TextView> dayTextViews = new ArrayList<>();
        dayTextViews.add(dayZeroDateTextView);
        dayTextViews.add(dayOneDay);
        dayTextViews.add(dayTwoDay);
        dayTextViews.add(dayThreeDay);
        dayTextViews.add(dayFourDay);
        dayTextViews.add(dayFiveDay);
        dayTextViews.add(daySixDay);

        dayOneDay.setText(modifiedWeekArrayList.get(1));
        dayTwoDay.setText(modifiedWeekArrayList.get(2));
        dayThreeDay.setText(modifiedWeekArrayList.get(3));
        dayFourDay.setText(modifiedWeekArrayList.get(4));
        dayFiveDay.setText(modifiedWeekArrayList.get(5));
        daySixDay.setText(modifiedWeekArrayList.get(6));

        try {
            dataFetcher.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int dayZeroMinF;
    int dayZeroMaxF;
    int dayZeroMinC;
    int dayZeroMaxC;
    String dayZeroWeatherPrimaryCoded;
    String dayZeroDate;
    String dayZeroBlurb;

    int dayOneMaxF;
    int dayOneMinF;
    int dayOneMaxC;
    int dayOneMinC;
    String dayOneWeatherPrimaryCoded;
    String dayOneDate;

    String dayTwoDate;
    String dayTwoWeatherPrimaryCoded;
    int  dayTwoMinF;
    int dayTwoMinC;
    int dayTwoMaxF;
    int dayTwoMaxC;

    int dayThreeMinF;
    int dayThreeMaxF;
    int dayThreeMinC;
    int dayThreeMaxC;
    String dayThreeDate;
    String dayThreeWeatherPrimaryCoded;

    int dayFourMinF;
    int dayFourMinC;
    int dayFourMaxF;
    int dayFourMaxC;
    String dayFourDate;
    String dayFourWeatherPrimaryCoded;

    int dayFiveMinF;
    int dayFiveMaxF;
    int dayFiveMinC;
    int dayFiveMaxC;
    String dayFiveDate;
    String dayFiveWeatherPrimaryCoded;


    int daySixMinF;
    int daySixMaxF;
    int daySixMinC;
    int daySixMaxC;
    String daySixDate;
    String daySixWeatherPrimaryCoded;


    //Get weather endpoints and update UI once AsyncTask has fetched data
    @Override
    public void processFinish(String output)  {
        System.out.println("processFinish " + output);
        try {
            JSONObject jsonObject = new JSONObject(output);
            JSONArray response = jsonObject.getJSONArray("response");
            JSONObject noNameObject = response.getJSONObject(0);
            JSONArray periods = noNameObject.getJSONArray("periods");

            JSONObject dayZero = periods.getJSONObject(0);
            dayZeroDate = dayZero.getString("dateTimeISO").substring(0, 10);
            Log.i("day0 ", dayZeroDate);
            dayZeroDateTextView.setText(todayString + " " + dayZeroDate.substring(5, 7) + "/" + dayZeroDate.substring(8));
            dayZeroMaxF = dayZero.getInt("maxTempF");
            dayZeroMinF = dayZero.getInt("minTempF");
            dayZeroMaxC = dayZero.getInt("maxTempC");
            dayZeroMinC = dayZero.getInt("minTempC");
            dayZeroWeatherPrimaryCoded = dayZero.getString("weatherPrimaryCoded");
            dayZeroBlurb = dayZero.getString("weather");
            blurb.setText(dayZeroBlurb);
            dayZeroMinTextView.setText(String.valueOf(dayZeroMinF + "°"));
            dayZeroMaxTextView.setText(String.valueOf(dayZeroMaxF + "°"));

            JSONObject dayOne = periods.getJSONObject(1);
            dayOneDate = dayOne.getString("dateTimeISO");
            dayOneMaxF = dayOne.getInt("maxTempF");
            dayOneMinF = dayOne.getInt("minTempF");
            dayOneMaxC = dayOne.getInt("maxTempC");
            dayOneMinC = dayOne.getInt("minTempC");
            dayOneWeatherPrimaryCoded = dayOne.getString("weatherPrimaryCoded");
            dayOneMinTextView.setText(String.valueOf(dayOneMinF + "°"));
            dayOneMaxTextView.setText(String.valueOf(dayOneMaxF + "°"));

            JSONObject dayTwo = periods.getJSONObject(2);
            dayTwoDate = dayTwo.getString("dateTimeISO");
            dayTwoMaxF = dayTwo.getInt("maxTempF");
            dayTwoMinF = dayTwo.getInt("minTempF");
            dayTwoMaxC = dayTwo.getInt("maxTempC");
            dayTwoMinC = dayTwo.getInt("minTempC");
            dayTwoWeatherPrimaryCoded = dayTwo.getString("weatherPrimaryCoded");
            dayTwoMinTextView.setText(String.valueOf(dayTwoMinF + "°"));
            dayTwoMaxTextView.setText(String.valueOf(dayTwoMaxF + "°"));

            JSONObject dayThree = periods.getJSONObject(3);
            dayThreeDate = dayThree.getString("dateTimeISO");
            dayThreeMaxF = dayThree.getInt("maxTempF");
            dayThreeMinF = dayThree.getInt("minTempF");
            dayThreeMaxC = dayThree.getInt("maxTempC");
            dayThreeMinC = dayThree.getInt("minTempC");
            dayThreeWeatherPrimaryCoded = dayThree.getString("weatherPrimaryCoded");
            dayThreeMinTextView.setText(String.valueOf(dayThreeMinF + "°"));
            dayThreeMaxTextView.setText(String.valueOf(dayThreeMaxF + "°"));

            JSONObject dayFour = periods.getJSONObject(4);
            dayFourDate = dayFour.getString("dateTimeISO");
            dayFourMaxF = dayFour.getInt("maxTempF");
            dayFourMinF = dayFour.getInt("minTempF");
            dayFourMaxC = dayFour.getInt("maxTempC");
            dayFourMinC = dayFour.getInt("minTempC");
            dayFourWeatherPrimaryCoded = dayFour.getString("weatherPrimaryCoded");
            dayFourMinTextView.setText(String.valueOf(dayFourMinF + "°"));
            dayFourMaxTextView.setText(String.valueOf(dayThreeMaxF + "°"));

            JSONObject dayFive = periods.getJSONObject(5);
            dayFiveDate = dayFive.getString("dateTimeISO");
            dayFiveMaxF = dayFive.getInt("maxTempF");
            dayFiveMinF = dayFive.getInt("minTempF");
            dayFiveMaxC = dayFive.getInt("maxTempC");
            dayFiveMinC = dayFive.getInt("minTempC");
            dayFiveWeatherPrimaryCoded = dayFive.getString("weatherPrimaryCoded");
            dayFiveMinTextView.setText(String.valueOf(dayFiveMinF + "°"));
            dayFiveMaxTextView.setText(String.valueOf(dayFiveMaxF + "°"));

            JSONObject daySix = periods.getJSONObject(6);
            daySixDate = daySix.getString("dateTimeISO");
            daySixMaxF = daySix.getInt("maxTempF");
            daySixMinF = daySix.getInt("minTempF");
            daySixMaxC = daySix.getInt("maxTempC");
            daySixMinC = daySix.getInt("minTempC");
            daySixWeatherPrimaryCoded = daySix.getString("weatherPrimaryCoded");
            daySixMinTextView.setText(String.valueOf(daySixMinF + "°"));
            daySixMaxTextView.setText(String.valueOf(daySixMaxF + "°"));
            setImages();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setToCelisius() {
        dayZeroMaxTextView.setText(String.valueOf(dayZeroMaxC + "°"));
        dayZeroMinTextView.setText(String.valueOf(dayZeroMinC + "°"));
        dayOneMaxTextView.setText(String.valueOf(dayOneMaxC + "°"));
        dayOneMinTextView.setText(String.valueOf(dayOneMinC + "°"));
        dayTwoMaxTextView.setText(String.valueOf(dayTwoMaxC + "°"));
        dayTwoMinTextView.setText(String.valueOf(dayTwoMinC + "°"));
        dayThreeMaxTextView.setText(String.valueOf(dayThreeMaxC + "°"));
        dayThreeMinTextView.setText(String.valueOf(dayThreeMinC + "°"));
        dayFourMaxTextView.setText(String.valueOf(dayFourMaxC + "°"));
        dayFourMinTextView.setText(String.valueOf(dayFourMinC + "°"));
        dayFiveMaxTextView.setText(String.valueOf(dayFiveMaxC + "°"));
        dayFiveMinTextView.setText(String.valueOf(dayFiveMinC + "°"));
        daySixMaxTextView.setText(String.valueOf(daySixMaxC + "°"));
        daySixMinTextView.setText(String.valueOf(daySixMinC + "°"));
    }

    public void setToFarenheit() {
        dayZeroMaxTextView.setText(String.valueOf(dayZeroMaxF + "°"));
        dayZeroMinTextView.setText(String.valueOf(dayZeroMinF + "°"));
        dayOneMaxTextView.setText(String.valueOf(dayOneMaxF + "°"));
        dayOneMinTextView.setText(String.valueOf(dayOneMinF + "°"));
        dayTwoMaxTextView.setText(String.valueOf(dayTwoMaxF + "°"));
        dayTwoMinTextView.setText(String.valueOf(dayTwoMinF + "°"));
        dayThreeMaxTextView.setText(String.valueOf(dayThreeMaxF + "°"));
        dayThreeMinTextView.setText(String.valueOf(dayThreeMinF + "°"));
        dayFourMaxTextView.setText(String.valueOf(dayFourMaxF + "°"));
        dayFourMinTextView.setText(String.valueOf(dayFourMinF + "°"));
        dayFiveMaxTextView.setText(String.valueOf(dayFiveMaxF + "°"));
        dayFiveMinTextView.setText(String.valueOf(dayFiveMinF + "°"));
        daySixMaxTextView.setText(String.valueOf(daySixMaxF + "°"));
        daySixMinTextView.setText(String.valueOf(daySixMinF + "°"));
    }

    public void setImages(){
        //HashMap creates weatherCode + ImageView key value pairs that I can iterate through and populate
        //ImageViews according to its weatherCode
        HashMap<String, ImageView> weatherByDay = new HashMap<>();
        weatherByDay.put(dayZeroWeatherPrimaryCoded, dayZeroImage);
        weatherByDay.put(dayOneWeatherPrimaryCoded, dayOneImage);
        weatherByDay.put(dayTwoWeatherPrimaryCoded, dayTwoImage);
        weatherByDay.put(dayThreeWeatherPrimaryCoded, dayThreeImage);
        weatherByDay.put(dayFourWeatherPrimaryCoded, dayFourImage);
        weatherByDay.put(dayFiveWeatherPrimaryCoded, dayFiveImage);
        weatherByDay.put(daySixWeatherPrimaryCoded, daySixImage);

        //First, we'll look for a few specific weather codes to update UI.
        for (String code : weatherByDay.keySet()){
            if (code.endsWith(":R") | code.endsWith(":RW")) {
                weatherByDay.get(code).setBackgroundResource(R.drawable.raining);
            }
            else if (code.endsWith(":S") | code.contains(":SI") | code.contains("BS")){
                weatherByDay.get(code).setBackgroundResource(R.drawable.snowing);
            }
            else if (code.endsWith(":A")){
                weatherByDay.get(code).setBackgroundResource(R.drawable.hail);
            }
            else if (code.contains("T")){
                weatherByDay.get(code).setBackgroundResource(R.drawable.bolt);
            }
            else if (code.contains("WM") | code.endsWith("RS") | code.contains("SW")){
                weatherByDay.get(code).setBackgroundResource(R.drawable.rainsnow);
            }
            //If no weather code is found, use cloud coverage code, per Aeris API
            else if (code.endsWith("CL") | code.endsWith("FW")){
                    weatherByDay.get(code).setBackgroundResource(R.drawable.sunny);
            }
            else if (code.endsWith("SC") | code.endsWith("BK")){
                    weatherByDay.get(code).setBackgroundResource(R.drawable.partly_cloudy);
            }
            else if (code.contains("OV")){
                    weatherByDay.get(code).setBackgroundResource(R.drawable.very_cloudy);
            }
            else if (code.contains("IC") | code.contains("FR")){
                weatherByDay.get(code).setBackgroundResource(R.drawable.icicle);
            }
        }
    }
}



