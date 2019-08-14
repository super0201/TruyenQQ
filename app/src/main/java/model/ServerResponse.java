package model;

public class ServerResponse {
    private String message;
    private Integer result;
    private User user;

    public ServerResponse(Integer result, String message, User user) {
        this.result = result;
        this.message = message;
        this.user = user;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}