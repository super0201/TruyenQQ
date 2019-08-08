package model;

import java.io.Serializable;

public class Responde implements Serializable {
    private String success, message;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
