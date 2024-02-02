package com.mgarzon.proyectoapp.model

object ReviewsProvider {
    val listOfReviews = mutableListOf<Review> (
        Review(
            "El Señor de los Anillos",
            "J. R. R. Tolkien",
            "Fantasía",
            "A pesar de ser un libro muy largo, es una lectura imprescindible. La historia es la base de la fantasía moderna" +
                    " y ha inspirado a muchos autores. La historia es muy buena y los personajes son muy interesantes, aunque hay " +
                    "una falta de personajes femeninos desarrollados. Es un libro muy descriptivo, lo que puede me llevó al " +
                    "aburrimiento en ciertos puntos",
            false,
            "https://medios.lamarmota.es/senor-de-los-anillos.jpeg",
            4.5f
        ),
        Review(
            "El Gran Gatsby",
            "F. Scott Fitzgerald",
            "Novela",
            "La novela es de 1925 y se nota. El hecho de que Gatsby no pudiera vivir su vida sin pensar en la Daisy da mucho que" +
                    "pensar. A pesar de todo la novela está muy escrita y ofrece una visión profunda que lo que fueron los felices años 20 " +
                    "en Estados Unidos.",
            false,
            "https://www.anagrama-ed.es/uploads/media/portadas/0001/15/b2834bc4ea71357c8b549dfccdd16d611c6586ea.jpeg",
            2.0f
        ),
        Review(
            "Frankenstein o el moderno Prometeo",
            "Mary Shelley",
            "Novela Gótica",
            "Clásico de la novela gótica imprescindible para cualquier lector. La novela narra la historia de Víctor Frankenstein, " +
                    "un estudiante de ciencias naturales de la universidad de Ingolstadt que consigue dar vida a un ser de apariencia humana " +
                    "pero de dos metros y medio de altura y aspecto repulsivo. Es una novala que hace pensar en los frivolo de la humanidad y en " +
                    "el sentimiento de dios de algunas personas.",
            true,
            "https://static.serlogal.com/imagenes_big/9788439/978843973080.JPG",
            3.0f
        ),
        Review(
            "1984",
            "George Orwell",
            "Ciencia Ficción",
            "La novela es una distopía que se desarrolla en Oceanía, un país gobernado por un partido totalitario que controla todos los " +
                    "aspectos de la vida de sus ciudadanos. La novela es muy interesante y te hace pensar en la sociedad actual y en la " +
                    "posibilidad de que se convierta en una distopía como la de la novela.",
            true,
            "https://imagessl4.casadellibro.com/a/l/t7/44/9788499890944.jpg",
            5.0f
        ),
        Review(
            "El Guardian entre el Centeno",
            "J. D. Salinger",
            "Novela",
            "El Guardian entre el Centeno es una novela del escritor estadounidense J. D. Salinger. Al publicarse en 1951 en los Estados Unidos, " +
                    "la novela provocó numerosas controversias por su lenguaje provocador y por retratar sin tapujos los conflictos en la adolescencia, " +
                    "como el acoso y la sexualidad",
            false,
            "https://www.cartv.es/thumbs/fullwidth/2021-07/el-guardian-entre-el-centeno-portada-240.jpg",
            0.0f
        ),
        Review(
            "Orgullo Y Prejuicio",
            "Austen, Jane",
            "Novala Romántica",
            "Orgullo y prejuicio es una novela de la escritora Jane Austen, publicada en 1813. Cuenta la historia de Elizabeth Bennet y Fitzwilliam Darcy, " +
                    "que se enamoran a pesar de las dificultades que les impone la sociedad de la época. Es una novela romántica muy interesante y que " +
                    "te hace pensar en la sociedad de la época.",
            true,
            "https://cdn.agapea.com/Alianza-Editorial/Orgullo-y-prejuicio-i1n17428974.jpg",
            3.5f
        ),
        Review(
            "Yo, robot",
            "Isaac Asimov",
            "Ciencia Ficción",
            "Yo, robot es una colección de cuentos de ciencia ficción de Isaac Asimov protagonizados por robots. Fue publicada por primera vez en " +
                    "Estados Unidos en 1950 por Gnome Press. En 1957 se publicó una versión ampliada en el Reino Unido por VGSF, bajo el título de " +
                    "The Complete Robot, con algunos relatos adicionales. En 1982 se publicó una edición revisada en Estados Unidos, bajo el título " +
                    "I, Robot: The Rest of the Robots, con otros relatos adicionales. En 1990 se publicó una edición revisada en el Reino Unido, " +
                    "bajo el título The Complete Robot, con todos los relatos de las ediciones anteriores y otros adicionales.",
            true,
            "https://www.cartv.es/thumbs/fullwidth/2021-07/el-guardian-entre-el-centeno-portada-240.jpg",
            1.5f
        ),
        Review(
            "Ana Karenina",
            "Liev N. Tolstói",
            "Novela Romántica",
            "Ana Karenina es una novela del escritor ruso León Tolstói publicada por primera vez en 1877. Con este libro, Tolstói no solo consiguió " +
                    "un gran éxito de público, sino que también obtuvo una gran consideración por parte de la crítica. Tolstói consideraba a Ana " +
                    "Karenina su primera novela verdadera. La novela es muy interesante y te hace pensar en la sociedad de la época.",
            true,
            "https://cdn.agapea.com/Austral/Ana-Karenina-i1n15399766.jpg",
            4.0f
        ),
        Review(
            "El Principito",
            "Antoine de Saint-Exupéry",
            "Novela",
            "El principito es una novela corta y la obra más famosa del escritor y aviador francés Antoine de Saint-Exupéry. La publicó por primera " +
                    "vez en 1943, mientras se hospedaba en un hotel en Nueva York, Estados Unidos. Ha sido traducida a ciento ochenta lenguas y dialectos, " +
                    "entre ellos el sistema de lectura braille. Ha vendido más de 140 millones de copias en todo el mundo, convirtiéndose en uno de los " +
                    "libros más vendidos de la historia. Es una novela muy interesante y que te hace pensar en la sociedad actual.",
            false,
            "https://multimedia.dideco.es/img/literatura/EAN_9788478887200-5.jpg",
            5.0f
        ),
        Review(
            "Don Quijote de la Mancha",
            "Miguel de Cervantes",
            "Novela satírica",
            "Don Quijote de la Mancha es una novela escrita por el español Miguel de Cervantes Saavedra. Publicada su primera parte con el título de " +
                    "El ingenioso hidalgo don Quijote de la Mancha a comienzos de 1605, es la obra más destacada de la literatura española y una de las " +
                    "principales de la literatura universal.",
            true,
            "https://simehbucket.s3.amazonaws.com/images/5a2d2f9c29b2605bc4176f80682b1fdf-full.jpg",
            2.0f
        ),
        Review(
            "Crimen y Castigo",
            "Fiódor Dostoyevski",
            "Novela psicológica",
            "Crimen y castigo es una novela de carácter psicológico escrita por el autor ruso Fiódor Dostoyevski. Fue publicada por primera vez en " +
                    "la revista El mensajero ruso, en 12 entregas, entre 1866 y 1867. La novela narra el asesinato de Rodión Románovich Raskólnikov, " +
                    "un estudiante ruso que decide asesinar a una usurera para demostrar su teoría sobre la existencia de personas que pueden cometer " +
                    "crímenes y delitos para luego utilizar el dinero o los bienes robados en obras de caridad.",
            false,
            "https://global-uploads.webflow.com/6034d7d1f3e0f52c50b2adee/6254541d8ae4df16d4e69bc8_6034d7d1f3e0f54529b2b1a1_Crimen-y-castigo-fiodor-dostoyevski-editorial-alma.jpeg",
            3.0f
        ),
        Review (
            "El nombre de la rosa",
            "Umberto Eco",
            "Novela histórica",
            "El nombre de la rosa es la primera novela del semiólogo y escritor italiano Umberto Eco. Publicada en 1980, narra las investigaciones " +
                    "de Guillermo de Baskerville para esclarecer una serie de crímenes ocurridos en una abadía benedictina en el año 1327. La " +
                    "novela fue llevada al cine en 1986 por Jean-Jacques Annaud con Sean Connery como protagonista.",
            true,
            "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/201604/25/00106523197116____1__640x640.jpg",
            0.5f
        )


    )

    fun getBooks(): List<Review> {
       Thread.sleep(2000)
        return listOfReviews
    }

    fun addBook(review: Review) {
        listOfReviews.add(review)
    }

    fun deleteBook(position: Int) {
        listOfReviews.removeAt(position)
    }

    fun editBook(position: Int, review: Review) {
        listOfReviews[position] = review
    }
}