package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created By JohnNguyen - Onesoft on 19/11/2018
 */
public class ModelFav implements Parcelable {
    private String name, thumb, category, type, res;

    public ModelFav(String name, String thumb, String category, String type, String res) {
        this.name = name;
        this.thumb = thumb;
        this.category = category;
        this.type = type;
        this.res = res;
    }

    protected ModelFav(Parcel in) {
        name = in.readString();
        thumb = in.readString();
        category = in.readString();
        type = in.readString();
        res = in.readString();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(thumb);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeString(res);
    }
}
