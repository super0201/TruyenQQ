package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {
    private String user, email, name, pass, old_password, new_password, result, message;
    private Date date_add,date_update;

    protected User(Parcel in) {
        user = in.readString();
        email = in.readString();
        name = in.readString();
        pass = in.readString();
        old_password = in.readString();
        new_password = in.readString();
        result = in.readString();
        message = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate_add() {
        return date_add;
    }

    public void setDate_add(Date date_add) {
        this.date_add = date_add;
    }

    public Date getDate_update() {
        return date_update;
    }

    public void setDate_update(Date date_update) {
        this.date_update = date_update;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(pass);
        dest.writeString(old_password);
        dest.writeString(new_password);
        dest.writeString(result);
        dest.writeString(message);
    }
}
