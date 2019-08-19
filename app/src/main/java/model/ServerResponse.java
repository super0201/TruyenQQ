package model;

public class ServerResponse {
    private String message;
    private Integer result;
    private ModelUser modelUser;

    public ServerResponse(Integer result, String message, ModelUser modelUser) {
        this.result = result;
        this.message = message;
        this.modelUser = modelUser;
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

    public ModelUser getModelUser() {
        return modelUser;
    }

    public void setModelUser(ModelUser modelUser) {
        this.modelUser = modelUser;
    }
}