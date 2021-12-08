package ru.burtelov.lab4

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.burtelov.lab4.databinding.ItemBinding

interface IPersonClickListener {
    fun showPersonName(name: String)
    fun showLikePersonName(name: String)
}

class PersonAdapter(private val clickListener: IPersonClickListener) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private var persons = mutableListOf<Person>()

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPerson(person: Person) {
        persons.add(person)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPreviousPersons(prevPersons: MutableList<Person>) {
        for (person in prevPersons)
            persons.add(person)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun getItemCount(): Int = persons.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]
        holder.bind(person, clickListener)
    }

    class PersonViewHolder(var binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, clickListener: IPersonClickListener) {
            itemView.setOnClickListener {
                clickListener.showPersonName(person.name)
            }
            binding.run {
                like.setOnClickListener {
                    clickListener.showLikePersonName(person.name)
                }
                name.text = person.name
                date.text = person.date
                info.text = person.info
                sex.text = person.sex
                if (person.image_path.isNotBlank())
                    Glide.with(photo.context)
                        .load(person.image_path)
                        .circleCrop()
                        .placeholder(R.drawable.ic_photo)
                        .into(photo)
                else
                    photo.setImageResource(R.drawable.ic_photo)
            }
        }
    }
}
