package service;

public class BookNotFoundExpeption extends RuntimeException {
    public BookNotFoundExpeption(String bookID) {
        super("can't find book by \"" + bookID + "\" id");
    }
}
