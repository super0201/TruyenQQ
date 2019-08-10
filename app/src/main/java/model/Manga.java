package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Manga {

    @SerializedName( "id" )
    @Expose
    private String id;

    @SerializedName( "name" )
    @Expose
    private String name;

    @SerializedName( "thumb" )
    @Expose
    private String thumb;

    @SerializedName( "category" )
    @Expose
    private String category;

    @SerializedName( "description" )
    @Expose
    private String description;

    @SerializedName( "date_add" )
    @Expose
    private String date_add;

    @SerializedName( "date_update" )
    @Expose
    private String date_update;

    public Manga() { }

    public Manga(String id, String name, String thumb, String category, String description, String date_add, String date_update) {
        this.id = id;
        this.name = name;
        this.thumb = thumb;
        this.category = category;
        this.description = description;
        this.date_add = date_add;
        this.date_update = date_update;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_add() {
        return date_add;
    }

    public void setDate_add(String date_add) {
        this.date_add = date_add;
    }

    public String getDate_update() {
        return date_update;
    }

    public void setDate_update(String date_update) {
        this.date_update = date_update;
    }
}
