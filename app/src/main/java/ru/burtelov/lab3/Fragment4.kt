package ru.burtelov.lab3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.burtelov.lab3.databinding.Fragment4LayoutBinding


class Fragment4 : Fragment() {

    lateinit var binding: Fragment4LayoutBinding

    private var uuid = 3

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment4LayoutBinding.inflate(inflater, container, false)

        binding.result.text =
            Calculate.field1.toString() + " " + Calculate.operation + " " + Calculate.field2.toString() + " = " + Calculate.calculate()
                .toString()

        prevButtonClick()

        return binding.root
    }

    private fun prevButtonClick() {
        binding.prev.setOnClickListener {
            val activityFunctions = requireActivity() as ActivityFunctions
            activityFunctions.showPreviousFragment(uuid)
        }
    }

}