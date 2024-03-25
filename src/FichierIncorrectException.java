import java.io.IOException;

public class FichierIncorrectException extends IOException {
    public FichierIncorrectException(String message) {
        super(message);
    }
}
