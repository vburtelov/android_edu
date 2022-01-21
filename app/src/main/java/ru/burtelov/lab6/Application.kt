package ru.burtelov.lab6

import android.app.Application
import ru.burtelov.lab6.db.Db

class NodeApp: Application() {
    val db by lazy { Db.getDb(this)}
    val repository by lazy { Repository(db.dao()) }
}