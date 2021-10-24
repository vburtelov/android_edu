package ru.burtelov.lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.burtelov.lab3.databinding.Fragment3LayoutBinding


class Fragment3 : Fragment() {

    lateinit var binding: Fragment3LayoutBinding

    private var uuid = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment3LayoutBinding.inflate(inflater, container, false)

        nextButtonClick()
        prevButtonClick()

        return binding.root
    }

    private fun nextButtonClick() {
        binding.next.setOnClickListener {
            val activityFunctions = requireActivity() as ActivityFunctions
            Calculate.operation = binding.operation.text.toString()
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