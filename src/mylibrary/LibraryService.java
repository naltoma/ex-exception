package mylibrary;

import java.util.HashMap;
import java.util.Map;

public class LibraryService {
    private static final int MAX_BORROW_LIMIT = 5;
    private Map<String, Integer> availableBooks = new HashMap<>();
    private Map<String, Integer> borrowedBooks = new HashMap<>();
    private boolean hasOverdueBooks = false; // 延滞フラグ

    public LibraryService() {
        // 図書の在庫初期化
        availableBooks.put("Java入門", 2);
        availableBooks.put("アルゴリズムの基礎", 1);
        availableBooks.put("データ構造と設計", 0); // 在庫なし
    }

    public void borrowBook(String user, String book) throws BorrowLimitExceededException, OverdueBookException, BookNotAvailableException {
        // 延滞チェック
        if (hasOverdueBooks) {
            throw new OverdueBookException("未返却の延滞図書があります。");
        }

        // 貸出冊数制限チェック
        int borrowedCount = borrowedBooks.getOrDefault(user, 0);
        if (borrowedCount >= MAX_BORROW_LIMIT) {
            throw new BorrowLimitExceededException("貸出可能な冊数を超えています。");
        }

        // 在庫チェック
        int stock = availableBooks.getOrDefault(book, 0);
        if (stock <= 0) {
            throw new BookNotAvailableException("リクエストされた書籍は現在貸出中です。");
        }

        // 貸し出し処理
        availableBooks.put(book, stock - 1);
        borrowedBooks.put(user, borrowedCount + 1);
        System.out.println(user + "さんが「" + book + "」を借りました。");
    }

    public void returnBook(String user, String book) {
        int borrowedCount = borrowedBooks.getOrDefault(user, 0);
        if (borrowedCount > 0) {
            borrowedBooks.put(user, borrowedCount - 1);
            availableBooks.put(book, availableBooks.getOrDefault(book, 0) + 1);
            System.out.println(user + "さんが「" + book + "」を返却しました。");
        }
    }

    public void setOverdueStatus(boolean status) {
        this.hasOverdueBooks = status;
    }
}
