package ru.burtelov.lab4

typealias PersonsListener = (persons: List<Person>) -> Unit

object PersonHolder {

    private var persons = mutableListOf<Person>()
    private var listeners = mutableSetOf<PersonsListener>()

    init {
        persons.add(
            Person(
                "Осип Мандельштам", "мужчина", "1891-1938",
                "русский поэт, прозаик и переводчик, эссеист, критик, литературовед. " +
                        "Один из крупнейших русских поэтов XX века",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Mandelshtam17.jpg/395px-Mandelshtam17.jpg"
            )
        )

        persons.add(
            Person(
                "Владимир Маяковский", "мужчина", "1893-1930",
                "русский советский поэт. Футурист. Один из наиболее значимых русских поэтов" +
                        " XX века. Классик советской литературы",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Majakovszkij.jpg/390px-Majakovszkij.jpg"
            )
        )

        persons.add(
            Person(
                "Джордж Оруэлл", "мужчина", "1903-1950",
                "британский писатель, журналист и публицист. Его работы отличаются простым" +
                        " стилем письма, критикой тоталитаризма" +
                        " и поддержкой демократического социализма.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/George_Orwell_press_photo.jpg/345px-George_Orwell_press_photo.jpg"
            )
        )

        persons.add(
            Person(
                "Александр Пушкин", "мужчина", "1799-1837",
                "русский поэт, драматург и прозаик, заложивший основы русского реалистического" +
                        " направления, литературный критик и теоретик литературы, историк, публицист" +
                        ", журналист; один из самых авторитетных литературных" +
                        " деятелей первой трети XIX века. ",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Kiprensky_Pushkin.jpg/405px-Kiprensky_Pushkin.jpg"
            )
        )

        persons.add(
            Person(
                "Лев Толстой", "мужчина", "1828-1910",
                "один из наиболее известных русских писателей и мыслителей, один из величайших" +
                        " писателей-романистов мира",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Leo_Tolstoy%2C_portrait.jpg/411px-Leo_Tolstoy%2C_portrait.jpg"
            )
        )

        persons.add(
            Person(
                "Антон Чехов", "мужчина", "1860-1904",
                "Один из самых известных драматургов мира. Его произведения переведены более" +
                        " чем на сто языков. Его пьесы, в особенности «Чайка», «Три сестры» и" +
                        " «Вишнёвый сад», на протяжении более ста лет ставятся во многих театрах мира. ",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Anton_Chekhov_1889.jpg/411px-Anton_Chekhov_1889.jpg"
            )
        )

        persons.add(
            Person(
                "Николай Гоголь", "мужчина", "1809-1852",
                "русский прозаик, драматург, поэт, критик, публицист, признанный одним из" +
                        " классиков русской литературы",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Gogol_karandash.jpg/411px-Gogol_karandash.jpg"
            )
        )

        persons.add(
            Person(
                "Иван Тургенев", "мужчина", "1818-1883",
                "русский писатель-реалист, поэт, публицист, драматург, прозаик, переводчик." +
                        " Один из классиков русской литературы, внёсших наиболее значительный вклад" +
                        " в её развитие во второй половине XIX века",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/Turgenev_by_Repin.jpg/411px-Turgenev_by_Repin.jpg"
            )
        )
    }

    fun addListener(listener: PersonsListener) {
        listeners.add(listener)
        listener.invoke(persons)
    }

    fun removeListener(listener: PersonsListener) {
        listeners.remove(listener)
    }
}