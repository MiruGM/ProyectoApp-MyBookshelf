package com.mgarzon.proyectoapp.model

object BooksProvider {
    val listOfBooks = mutableListOf<Book> (
        Book(
            "The Lord of the Rings",
            "J. R. R. Tolkien",
            "Fantasy",
            "The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkien's 1937 children's book The Hobbit, but eventually developed into a much larger work.",
            false,
            ""
        ),
        Book(
            "The Hobbit",
            "J. R. R. Tolkien",
            "Fantasy",
            "The Hobbit, or There and Back Again is a children's fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.",
            false,
            ""
        ),
        Book(
            "The Silmarillion",
            "J. R. R. Tolkien",
            "Fantasy",
            "The Silmarillion is a collection of mythopoeic works by English writer J. R. R. Tolkien, edited and published posthumously by his son, Christopher Tolkien, in 1977, with assistance from Guy Gavriel Kay.",
            false,
            ""
        ),
        Book(
            "The Great Gatsby",
            "F. Scott Fitzgerald",
            "Novel",
            "The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.",
            false,
            ""
        ),
        Book(
            "The Catcher in the Rye",
            "J. D. Salinger",
            "Novel",
            "The Catcher in the Rye is a novel by J. D. Salinger, partially published in serial form in 1945–1946 and as a novel in 1951. It was originally intended for adults but is read by adolescents for its themes of angst, alienation, and as a critique on superficiality in society.",
            false,
            ""
        )
    )

    fun getBooks(): List<Book> {
       Thread.sleep(2000)
        return listOfBooks
    }

    fun addBook(book: Book) {
        listOfBooks.add(book)
    }

    fun deleteBook(position: Int) {
        listOfBooks.removeAt(position)
    }

    fun editBook(position: Int, book: Book) {
        listOfBooks[position] = book
    }
}