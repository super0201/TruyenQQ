package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListManga {
    @SerializedName("data_all")
    @Expose
    private ArrayList<Manga> mangas = new ArrayList<>();

    public ArrayList<Manga> getMangas() {
        return mangas;
    }

    public void setMangas(ArrayList<Manga> mangas) {
        this.mangas = mangas;
    }
}
