package charlyn23.c4q.nyc.movieapptb;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by charlynbuchanan on 10/8/15.
 */
public class PosterActivity extends Activity {

    private ImageView posterView;
    private TextView summaryView;
    private TextView dateView;
    private TextView voteView;
    private TextView popularityView;
    private TextView titleView;
    String summary;
    String vote;
    String popularity;
    String date;
    String title;
    String imageLink;
    boolean noImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        posterView = (ImageView) findViewById(R.id.posterView);
        summaryView = (TextView) findViewById(R.id.summaryView);
        dateView = (TextView) findViewById(R.id.dateView);
        voteView = (TextView) findViewById(R.id.voteView);
        popularityView = (TextView) findViewById(R.id.popularityView);
        titleView = (TextView) findViewById(R.id.titleView);

        summary = getIntent().getExtras().getString("summary");
        summaryView.setText(summary);

        title = getIntent().getExtras().getString("title");
        titleView.setText(title);

        date = getIntent().getExtras().getString("date");
        dateView.setText("Release date :" + date);

        popularity = getIntent().getExtras().getString("popularity");
        popularityView.setText("Popularity: " + popularity);

        vote = getIntent().getExtras().getString("vote");
        voteView.setText("Avg rating: " + vote);

        imageLink = getIntent().getExtras().getString("imageLink");
        Picasso.with(getBaseContext()).load(imageLink).into(posterView);

        noImage = getIntent().getExtras().getBoolean("noImage");
        if (posterView.getDrawable() == null) {
            posterView.setImageResource(R.drawable.noimage);

        }
    }
}
