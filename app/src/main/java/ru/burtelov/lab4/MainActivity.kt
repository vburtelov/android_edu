package ru.burtelov.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import ru.burtelov.lab4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PersonAdapter(object : IPersonClickListener {
            override fun showPersonName(name: String) {
                Snackbar.make(binding.root, "Нажата карточка: ${name}", 500).show()
            }

            override fun showLikePersonName(name: String) {
                Snackbar.make(binding.root, "Нажат лайк: ${name}", 500).show()
            }

        })

        val layoutManager = LinearLayoutManager(this)
        binding.recycleview.layoutManager = layoutManager
        binding.recycleview.adapter = adapter

        PersonHolder.addListener(personsListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        PersonHolder.removeListener(personsListener)
    }

    private val personsListener: PersonsListener = {
        adapter.persons = it
    }
}