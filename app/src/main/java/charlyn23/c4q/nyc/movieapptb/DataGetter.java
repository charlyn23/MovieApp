package charlyn23.c4q.nyc.movieapptb;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by charlynbuchanan on 10/7/15.
 */
public class DataGetter {
    public DataGetter() {
        new AsyncClass().execute();
    }

    class AsyncClass extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String jsonURL = "https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=2015-09-01&primary_release_date.lte=2015-10-07&api_key=/discover/movie?primary_release_date.gte=2015-09-01&primary_release_date.lte=2015-10-27&api_key=45b53fed0a34751a8fda0043801d6e08";
            URL url = null;
            try {
                url = new URL(jsonURL);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                if ((connection != null) || (url != null)) {
                    Log.v("status", "CONNECTED");
                }
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                result = stringBuilder.toString();
                Log.d("json: ", result);

                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("JSON", "Malformed url: " + e.toString());

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("JSON", "IOException url" + e.toString());
            }

            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getJsonString();
            jsonString = s;
            Log.v("JSON", getJsonString());
            getMovieData();
            Log.v("getter: ", String.valueOf(getMovieData()));

        }
    }


    private static String jsonString = "";

    public static String getJsonString() {
        return jsonString;
    }

    //Get each movie of JSON object
    public static ArrayList<JSONObject> getMovieData(){
        String json = getJsonString();
        JSONObject movie = null;

        ArrayList<JSONObject> movies = new ArrayList<>();
        String title;
        String poster;
        String description;
        String date;
        String vote;
        String popularity;

        try {
            JSONObject object = new JSONObject(json);

            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                movie = (JSONObject) results.get(i);
                movies.add(movie);
                title = movie.getString("title");
                poster = movie.getString("poster_path");
                description = movie.getString("overview");
                date = movie.getString("release_date");
                vote = movie.getString("vote_average");
                popularity = movie.getString("popularity");

                Log.d("movie data = " , title + " " + poster + "\n " + description);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON", "Malformed url: " + e.toString());
        }
        Log.d("movie list size :", String.valueOf(movies.size()));
        Log.d("movie list :", String.valueOf(movies));

        return movies;
    }


}
