package ru.burtelov.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import ru.burtelov.lab6.databinding.MainActivityBinding
import ru.burtelov.lab6.viewmodels.NodeViewModel
import ru.burtelov.lab6.viewmodels.NodeViewModelFactory

// https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel =
            ViewModelProviders.of(this,
                NodeViewModelFactory((application as NodeApp).repository))[NodeViewModel::class.java]
    }
}