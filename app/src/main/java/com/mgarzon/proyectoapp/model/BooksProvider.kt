package com.mgarzon.proyectoapp.model

object BooksProvider {
    val listOfBooks = mutableListOf<Book> (
        Book(
            "El Señor de los Anillos",
            "J. R. R. Tolkien",
            "Fantasía",
            "A pesar de ser un libro muy largo, es una lectura imprescindible. La historia es la base de la fantasía moderna" +
                    " y ha inspirado a muchos autores. La historia es muy buena y los personajes son muy interesantes, aunque hay " +
                    "una falta de personajes femeninos desarrollados. Es un libro muy descriptivo, lo que puede me llevó al " +
                    "aburrimiento en ciertos puntos",
            false,
            "https://medios.lamarmota.es/senor-de-los-anillos.jpeg"
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
            "El Gran Gatsby",
            "F. Scott Fitzgerald",
            "Novela",
            "The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.",
            false,
            "https://www.anagrama-ed.es/uploads/media/portadas/0001/15/b2834bc4ea71357c8b549dfccdd16d611c6586ea.jpeg"
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