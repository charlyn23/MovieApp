package charlyn23.c4q.nyc.movieapptb;

/**
 * Created by charlynbuchanan on 10/8/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by charlynbuchanan on 10/7/15.
 */
public class ContentAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movieList;

    public ContentAdapter(Context context, int resource, List<Movie> movies) {
        super(context, R.layout.list_row, movies);
        this.movieList = movies;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView title;
        ImageView listImage;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.listImage = (ImageView) view.findViewById(R.id.list_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (movieList != null && movieList.size() > position) {

            Movie currentMovie = movieList.get(position);

            holder.title.setText(currentMovie.getTitle());

            Picasso.with(getContext()).load(currentMovie.getImageLink()).into(holder.listImage);

        }

        return view;

    }

}
