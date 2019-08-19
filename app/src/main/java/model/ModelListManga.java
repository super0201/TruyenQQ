package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelListManga implements Parcelable {
    @SerializedName("data_all")
    @Expose
    private ArrayList<ModelManga> mangases = new ArrayList<>();

    protected ModelListManga(Parcel in) {
    }

    public static final Creator<ModelListManga> CREATOR = new Creator<ModelListManga>() {
        @Override
        public ModelListManga createFromParcel(Parcel in) {
            return new ModelListManga( in );
        }

        @Override
        public ModelListManga[] newArray(int size) {
            return new ModelListManga[size];
        }
    };

    public ArrayList<ModelManga> getMangases() {
        return mangases;
    }

    public void setMangases(ArrayList<ModelManga> mangases) {
        this.mangases = mangases;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
