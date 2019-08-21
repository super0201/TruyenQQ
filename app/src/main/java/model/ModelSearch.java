package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class ModelSearch implements Parcelable {
    private int id;
    private String category, thumb, name, description, date_add;

    public ModelSearch(Parcel in) {
        name = in.readString();
        category = in.readString();
        thumb = in.readString();
        description = in.readString();
        date_add = in.readString();
    }

    public static final Creator<ModelSearch> CREATOR = new Creator<ModelSearch>() {
        @Override
        public ModelSearch createFromParcel(Parcel in) {
            return new ModelSearch(in);
        }

        @Override
        public ModelSearch[] newArray(int size) {
            return new ModelSearch[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getDate_add() {
        return date_add;
    }

    public void setDate_add(String date_add) {
        this.date_add = date_add;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ModelSearch(String category, String thumb, String name, String description, String date_add) {
        this.id = id;
        this.category = category;
        this.thumb = thumb;
        this.name = name;
        this.description = description;
        this.date_add = date_add;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(thumb);
        dest.writeString(description);
        dest.writeString(date_add);
    }
}
