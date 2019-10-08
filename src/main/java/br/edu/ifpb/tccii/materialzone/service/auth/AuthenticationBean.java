package br.edu.ifpb.tccii.materialzone.service.auth;

public class AuthenticationBean {

    private String message;

    public AuthenticationBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("MaterialZone [message = %s]", message);
    }
}
