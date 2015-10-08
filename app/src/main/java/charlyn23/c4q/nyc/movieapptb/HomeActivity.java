package charlyn23.c4q.nyc.movieapptb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

public class HomeActivity extends Activity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private ListView movieListView;
    private ContentAdapter moviesAdapter;

    private List<Movie> movieList = new ArrayList<>();
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView label = (TextView) findViewById(R.id.label);

        movieListView = (ListView) findViewById(R.id.list);
        moviesAdapter = new ContentAdapter(this, R.layout.list_row, movieList);
        movieListView.setAdapter(moviesAdapter);

        DataGetterAsyncClass dataGetterAsyncClass = new DataGetterAsyncClass();
        dataGetterAsyncClass.execute();

        Log.v(TAG, "calling DataGetterAsyncClass to retrieve movies");



    }

//    public static void setContentAdapter() {
//        ContentAdapter moviesAdapter = new ContentAdapter(ContentAdapter.context, R.layout.list_row, DataGetter.getMovieData());
//        movieListView.setAdapter(moviesAdapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class DataGetterAsyncClass extends AsyncTask<Void, Void, List<Movie>> {
        private final String TAG = DataGetterAsyncClass.class.getSimpleName();

        protected List<Movie> doInBackground(Void... params) {
            String result = "";
            String jsonURL = "https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=2015-09-01&primary_release_date.lte=2015-10-07&api_key=/discover/movie?primary_release_date.gte=2015-09-01&primary_release_date.lte=2015-10-27&api_key=45b53fed0a34751a8fda0043801d6e08";
            URL url = null;
            List<Movie> resultMovieList = new ArrayList<>();
            try {
                url = new URL(jsonURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if ((connection != null) || (url != null)) {
                    Log.v(TAG, "status: CONNECTED");
                }

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                result = stringBuilder.toString();
                Log.d(TAG, "json string:\n" + result);

                resultMovieList = parseMovieData(result);

                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "Malformed url: " + e.toString());

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "IOException url" + e.toString());
            }

            return resultMovieList;
        }

        @Override
        protected void onPostExecute(final List<Movie> movieList) {
            Log.v(TAG, "found movies: count=" + movieList.size());
            HomeActivity.this.movieList = movieList;
            HomeActivity.this.moviesAdapter.setMovieList(movieList);
            Log.v(TAG, String.format("adapter loaded with %d movies", movieList.size()));

            final AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), PosterActivity.class);
                    String title = movieList.get(position).getTitle();
                    String imageLink = movieList.get(position).getImageLink();
                    String date = movieList.get(position).getReleaseDate();
                    String summary = movieList.get(position).getOverView();
                    String popularity = movieList.get(position).getPopularity();
                    String vote = movieList.get(position).getVote();

                    intent.putExtra("title", title);
                    intent.putExtra("imageLink", imageLink);
                    intent.putExtra("date", date);
                    intent.putExtra("summary", summary);
                    intent.putExtra("popularity", popularity);
                    intent.putExtra("vote", vote);
                    intent.putExtra("imageLink", imageLink);
                    startActivity(intent);
                }
            };
            movieListView.setOnItemClickListener(listener);
        }
    }


    //Get each movie of JSON object
    public  List<Movie> parseMovieData(String jsonString) {
        List<charlyn23.c4q.nyc.movieapptb.Movie> resultMovieList = new ArrayList<>();
        try {
            JSONObject resultJsonObject = new JSONObject(jsonString);
            JSONArray moviesJsonArray = resultJsonObject.getJSONArray("results");
            int movieCount = moviesJsonArray.length();

            for (int index = 0; index < movieCount; index++) {
                JSONObject movieJsonObject = moviesJsonArray.getJSONObject(index);

                String title = movieJsonObject.optString("title", "");
                String overView = movieJsonObject.optString("overview", "");
                String imageLink = movieJsonObject.getString("poster_path");
                String releaseDate = movieJsonObject.optString("release_date");
                String popularity = movieJsonObject.optString("popularity");
                String vote = movieJsonObject.optString("vote_average");


                Movie movie = new Movie(title, overView, imageLink, releaseDate, popularity, vote);
                resultMovieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultMovieList;
    }
}
