package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created By JohnNguyen - Onesoft on 07/11/2018
 */
public class ModelCategory implements Parcelable {
    private String name, thumb;

    public ModelCategory(Parcel in) {
        name = in.readString();
        thumb = in.readString();
    }

    public static final Creator<ModelCategory> CREATOR = new Creator<ModelCategory>() {
        @Override
        public ModelCategory createFromParcel(Parcel in) {
            return new ModelCategory(in);
        }

        @Override
        public ModelCategory[] newArray(int size) {
            return new ModelCategory[size];
        }
    };

    public ModelCategory(String thumb, String category) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(thumb);
    }
}
