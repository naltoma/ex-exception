import mylibrary.*;

public class Main {
    public static void main(String[] args) {
        LibraryService library = new LibraryService();

        try {
            library.borrowBook("田中", "Java入門");
            library.borrowBook("田中", "アルゴリズムの基礎");
            library.borrowBook("田中", "データ構造と設計"); // 在庫切れ例外
        } catch (BorrowLimitExceededException | OverdueBookException | BookNotAvailableException e) {
            System.out.println("エラー: " + e.getMessage());
        }

        library.setOverdueStatus(true); // 延滞状態を設定

        try {
            library.borrowBook("佐藤", "Java入門"); // 延滞例外
        } catch (BorrowLimitExceededException | OverdueBookException | BookNotAvailableException e) {
            System.out.println("エラー: " + e.getMessage());
        }
    }
}
