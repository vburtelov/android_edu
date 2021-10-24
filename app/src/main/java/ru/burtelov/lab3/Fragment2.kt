package ru.burtelov.lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.burtelov.lab3.databinding.Fragment2LayoutBinding
import java.lang.Exception


class Fragment2 : Fragment() {

    lateinit var binding: Fragment2LayoutBinding

    private var uuid = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment2LayoutBinding.inflate(inflater, container, false)

        nextButtonClick()
        prevButtonClick()

        return binding.root
    }

    private fun nextButtonClick() {
        binding.next.setOnClickListener {
            val activityFunctions = requireActivity() as ActivityFunctions
            try {
                Calculate.field2 = binding.secondTextView.text.toString().toInt()
            } catch (e: Exception) {
                Calculate.field2 = null
            }
            activityFunctions.showNextFragment(uuid)
        }
    }

    private fun prevButtonClick() {
        binding.prev.setOnClickListener {
            val activityFunctions = requireActivity() as ActivityFunctions
            activityFunctions.showPreviousFragment(uuid)
        }
    }

}