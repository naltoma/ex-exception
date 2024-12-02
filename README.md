## Javaで独自例外を実装する例
図書館における貸し出しサービスにて、次に示す状況を例外として実装する例。

- 貸し出し制限超過: 利用者が同時に借りられる最大冊数を超えて本を借りようとした場合。
- 延滞未返却: 利用者が延滞している本を返却しないまま新しい本を借りようとした場合。
- 本の在庫切れ: 借りたい本がすべて貸し出されている場合。

### クラス図
```mermaid
classDiagram
    class LibraryService {
        - MAX_BORROW_LIMIT : int
        - availableBooks : Map<String, Integer>
        - borrowedBooks : Map<String, Integer>
        - hasOverdueBooks : boolean
        + LibraryService()
        + borrowBook(user: String, book: String) : void
        + returnBook(user: String, book: String) : void
        + setOverdueStatus(status: boolean) : void
    }

    class BorrowLimitExceededException {
        + BorrowLimitExceededException(message: String)
    }

    class OverdueBookException {
        + OverdueBookException(message: String)
    }

    class BookNotAvailableException {
        + BookNotAvailableException(message: String)
    }

    LibraryService --> BorrowLimitExceededException : throws
    LibraryService --> OverdueBookException : throws
    LibraryService --> BookNotAvailableException : throws
```

- LibraryService
- 例外クラス
    - BorrowLimitExceededException: 貸し出し制限超過
    - OverdueBookException: 延滞未返却
    - BookNotAvailableException: 本の在庫切れ

### シーケンス図
```mermaid
sequenceDiagram
    participant Main
    participant LibraryService
    participant BorrowLimitExceededException
    participant OverdueBookException
    participant BookNotAvailableException

    Main->>LibraryService: new LibraryService()
    activate LibraryService

    Main->>LibraryService: borrowBook("田中", "Java入門")
    LibraryService-->>Main: 成功
    Main->>LibraryService: borrowBook("田中", "アルゴリズムの基礎")
    LibraryService-->>Main: 成功

    Main->>LibraryService: borrowBook("田中", "データ構造と設計")
    LibraryService->>BookNotAvailableException: throw new BookNotAvailableException()
    activate BookNotAvailableException
    BookNotAvailableException-->>Main: リクエストされた書籍は現在貸出中
    deactivate BookNotAvailableException

    Main->>LibraryService: setOverdueStatus(true)
    LibraryService-->>Main: 状態を更新

    Main->>LibraryService: borrowBook("佐藤", "Java入門")
    LibraryService->>OverdueBookException: throw new OverdueBookException()
    activate OverdueBookException
    OverdueBookException-->>Main: 未返却の延滞図書があります
    deactivate OverdueBookException

    deactivate LibraryService
```

- オブジェクト生成
    - LibraryServiceオブジェクトがMainクラスで生成されます。
- 貸し出し処理
    - LibraryServiceに対して複数のborrowBookメソッドが呼び出されます。
    - 最初の2つは成功し、結果が返されます。
- 例外発生
    - "データ構造と設計"を借りようとするとBookNotAvailableExceptionがスローされ、例外メッセージがMainに返されます。
- 状態更新
    - setOverdueStatus(true)を呼び出し、延滞状態を設定します。
- 延滞チェック
    - その後、"佐藤"が本を借りようとするとOverdueBookExceptionがスローされ、例外メッセージが返されます。
