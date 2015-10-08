package charlyn23.c4q.nyc.movieapptb;


public class Movie {
    private String title;
    private String overView;
    private String imageLink;
    private String releaseDate;
    private String popularity;
    private String vote;

    public Movie() {
    }

    public Movie(String title, String overView, String imageLink, String releaseDate, String popularity, String vote) {
        this.title = title;
        this.overView = overView;
        this.imageLink = imageLink;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.vote = vote;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getImageLink() {
        return "https://image.tmdb.org/t/p/original" + imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}