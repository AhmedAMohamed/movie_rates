package movierater.mal.ahmedalaa.com.movierates;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmedalaa on 9/16/16.
 */
public class Review implements Parcelable{

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public Review(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, String size, String type) {
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public Review(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    protected Review(Parcel in) {
        id = in.readString();
        iso_639_1 = in.readString();
        iso_3166_1 = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readString();
        type = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", iso_639_1='" + iso_639_1 + '\'' +
                ", iso_3166_1='" + iso_3166_1 + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (!id.equals(review.id)) return false;
        if (iso_639_1 != null ? !iso_639_1.equals(review.iso_639_1) : review.iso_639_1 != null)
            return false;
        if (iso_3166_1 != null ? !iso_3166_1.equals(review.iso_3166_1) : review.iso_3166_1 != null)
            return false;
        if (!key.equals(review.key)) return false;
        if (!name.equals(review.name)) return false;
        if (site != null ? !site.equals(review.site) : review.site != null) return false;
        if (size != null ? !size.equals(review.size) : review.size != null) return false;
        return type != null ? type.equals(review.type) : review.type == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (iso_639_1 != null ? iso_639_1.hashCode() : 0);
        result = 31 * result + (iso_3166_1 != null ? iso_3166_1.hashCode() : 0);
        result = 31 * result + key.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(iso_639_1);
        parcel.writeString(iso_3166_1);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(size);
        parcel.writeString(type);
    }
}
