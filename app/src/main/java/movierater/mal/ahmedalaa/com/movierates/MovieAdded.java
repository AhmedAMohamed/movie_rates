package movierater.mal.ahmedalaa.com.movierates;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmedalaa on 9/17/16.
 */
public class MovieAdded implements Parcelable{

    boolean state;

    public MovieAdded(boolean state) {
        this.state = state;
    }

    protected MovieAdded(Parcel in) {
        state = in.readByte() != 0;
    }

    public static final Creator<MovieAdded> CREATOR = new Creator<MovieAdded>() {
        @Override
        public MovieAdded createFromParcel(Parcel in) {
            return new MovieAdded(in);
        }

        @Override
        public MovieAdded[] newArray(int size) {
            return new MovieAdded[size];
        }
    };

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (state ? 1 : 0));
    }
}
