package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListManga implements Parcelable {
    @SerializedName("data_all")
    @Expose
    private ArrayList<Manga> mangas = new ArrayList<>();

    protected ListManga(Parcel in) {
    }

    public static final Creator<ListManga> CREATOR = new Creator<ListManga>() {
        @Override
        public ListManga createFromParcel(Parcel in) {
            return new ListManga( in );
        }

        @Override
        public ListManga[] newArray(int size) {
            return new ListManga[size];
        }
    };

    public ArrayList<Manga> getMangas() {
        return mangas;
    }

    public void setMangas(ArrayList<Manga> mangas) {
        this.mangas = mangas;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
