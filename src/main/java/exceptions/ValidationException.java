package exceptions;

public class ValidationException extends RuntimeException{
    private String mesaj;

    public ValidationException(String mesaj) {
        this.mesaj=mesaj;
    }

    public String getMesaj() {
        return mesaj;
    }
}
