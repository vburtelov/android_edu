package ru.burtelov.lab4

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit

class AsyncFragment : Fragment() {

    companion object{
        const val TAG = "Async"
    }

    var persons = mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        PersonHolder.addListener(personsListener)
        MessagesSender().execute()
    }

    private val personsListener: PersonsListener = {
        if (it != null) {
            persons.add(it)
            val activityCallback = requireActivity() as ActivityCallback
            activityCallback.getNewPerson(it)
        }
    }


    class MessagesSender : AsyncTask<Void, Void, Void>() {

        override fun onProgressUpdate(vararg p0: Void?) {
            super.onProgressUpdate()
            PersonHolder.sendMessage()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            for (i in 1..PersonHolder.persons.count()) {
                TimeUnit.SECONDS.sleep(2)
                publishProgress()
            }
            return null
        }
    }
}