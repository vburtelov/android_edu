package ru.burtelov.lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.burtelov.lab3.databinding.Fragment1LayoutBinding


class Fragment1 : Fragment() {

    lateinit var binding: Fragment1LayoutBinding

    private var uuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1LayoutBinding.inflate(inflater, container, false)
        nextButtonClick()
        return binding.root
    }

    private fun nextButtonClick() {
        binding.nextButton.setOnClickListener {
            val activityFunctions = requireActivity() as ActivityFunctions
            try {
                Calculate.field1 = binding.firstTextView.text.toString().toInt()
            } catch (e: Exception) {
                Calculate.field1 = null
            }
            activityFunctions.showNextFragment(uuid)
        }
    }


}