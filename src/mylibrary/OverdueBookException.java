package mylibrary;

// 延滞未返却
public class OverdueBookException extends Exception {
    public OverdueBookException(String message) {
        super(message);
    }
}
