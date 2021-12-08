package ru.burtelov.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.burtelov.lab4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

interface ActivityCallback {
    fun getNewPerson(person: Person)
}

class MainActivity : AppCompatActivity(), ActivityCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter
    private var fragment: AsyncFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        adapter = PersonAdapter(object : IPersonClickListener {
            override fun showPersonName(name: String) {
                Snackbar.make(binding.root, "Нажата карточка: ${name}", 500).show()
            }

            override fun showLikePersonName(name: String) {
                Snackbar.make(binding.root, "Нажат лайк: ${name}", 500).show()
            }

        })

        var fm = supportFragmentManager
        var oldFragment = fm.findFragmentByTag(AsyncFragment.TAG)
        if (oldFragment == null) {
            fragment = AsyncFragment()
            fm.beginTransaction().add(fragment!!, AsyncFragment.TAG).commit()
        } else {
            fragment = oldFragment as AsyncFragment
            adapter.getPreviousPersons(fragment!!.persons)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recycleview.layoutManager = layoutManager
        binding.recycleview.adapter = adapter
    }

    override fun getNewPerson(person: Person) {
        adapter.addNewPerson(person)
    }
}