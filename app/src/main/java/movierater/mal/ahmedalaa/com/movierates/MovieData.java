package movierater.mal.ahmedalaa.com.movierates;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ahmedalaa on 8/13/16.
 */
public class MovieData implements Parcelable{

    private String poster_path;
    private boolean adult;
    private String overview;
    private ArrayList<Integer> genre_ids;
    private String id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private double popularity;
    private int vote_count;
    private boolean video;
    private double vote_average;
    private String date;

    private ArrayList<Review> reviews;
    private ArrayList<Trailer> trailers;

    public MovieData() {

    }

    public MovieData(String poster_path, boolean adult, String overview,
                     ArrayList<Integer> genre_ids, String id, String original_title,
                     String original_language, String title, String backdrop_path,
                     double popularity, int vote_count, boolean video, double vote_average,
                     String date, ArrayList<Review> reviews, ArrayList<Trailer> trailers) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
        this.date = date;
        this.reviews = reviews;
        this.trailers = trailers;
    }

    protected MovieData(Parcel in) {
        poster_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        id = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        vote_count = in.readInt();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
        date = in.readString();
        reviews = in.createTypedArrayList(Review.CREATOR);
        trailers = in.createTypedArrayList(Trailer.CREATOR);
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "poster_path='" + poster_path + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", genre_ids=" + genre_ids +
                ", id='" + id + '\'' +
                ", original_title='" + original_title + '\'' +
                ", original_language='" + original_language + '\'' +
                ", title='" + title + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", popularity=" + popularity +
                ", vote_count=" + vote_count +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", date='" + date + '\'' +
                ", reviews=" + reviews +
                ", trailers=" + trailers +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieData movieData = (MovieData) o;

        if (adult != movieData.adult) return false;
        if (Double.compare(movieData.popularity, popularity) != 0) return false;
        if (vote_count != movieData.vote_count) return false;
        if (video != movieData.video) return false;
        if (Double.compare(movieData.vote_average, vote_average) != 0) return false;
        if (poster_path != null ? !poster_path.equals(movieData.poster_path) : movieData.poster_path != null)
            return false;
        if (overview != null ? !overview.equals(movieData.overview) : movieData.overview != null)
            return false;
        if (genre_ids != null ? !genre_ids.equals(movieData.genre_ids) : movieData.genre_ids != null)
            return false;
        if (id != null ? !id.equals(movieData.id) : movieData.id != null) return false;
        if (original_title != null ? !original_title.equals(movieData.original_title) : movieData.original_title != null)
            return false;
        if (original_language != null ? !original_language.equals(movieData.original_language) : movieData.original_language != null)
            return false;
        if (title != null ? !title.equals(movieData.title) : movieData.title != null) return false;
        if (backdrop_path != null ? !backdrop_path.equals(movieData.backdrop_path) : movieData.backdrop_path != null)
            return false;
        if (date != null ? !date.equals(movieData.date) : movieData.date != null) return false;
        if (reviews != null ? !reviews.equals(movieData.reviews) : movieData.reviews != null)
            return false;
        return trailers != null ? trailers.equals(movieData.trailers) : movieData.trailers == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = poster_path != null ? poster_path.hashCode() : 0;
        result = 31 * result + (adult ? 1 : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (genre_ids != null ? genre_ids.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (original_title != null ? original_title.hashCode() : 0);
        result = 31 * result + (original_language != null ? original_language.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (backdrop_path != null ? backdrop_path.hashCode() : 0);
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + vote_count;
        result = 31 * result + (video ? 1 : 0);
        temp = Double.doubleToLongBits(vote_average);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        result = 31 * result + (trailers != null ? trailers.hashCode() : 0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(id);
        parcel.writeString(original_title);
        parcel.writeString(original_language);
        parcel.writeString(title);
        parcel.writeString(backdrop_path);
        parcel.writeDouble(popularity);
        parcel.writeInt(vote_count);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeDouble(vote_average);
        parcel.writeString(date);
        parcel.writeTypedList(reviews);
        parcel.writeTypedList(trailers);
    }
}
