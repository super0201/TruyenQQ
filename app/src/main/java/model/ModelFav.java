package model;

import android.os.Parcel;
import android.os.Parcelable;


public class ModelFav implements Parcelable {
    private String user, bookmark, thumb;

    public ModelFav(Parcel in) {
        user = in.readString();
        bookmark = in.readString();
        thumb = in.readString();
    }

    public static final Creator<ModelFav> CREATOR = new Creator<ModelFav>() {
        @Override
        public ModelFav createFromParcel(Parcel in) {
            return new ModelFav(in);
        }

        @Override
        public ModelFav[] newArray(int size) {
            return new ModelFav[size];
        }
    };

    public ModelFav(String user, String bookmark, String thumb) {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user);
        parcel.writeString(bookmark);
        parcel.writeString(thumb);
    }
}
