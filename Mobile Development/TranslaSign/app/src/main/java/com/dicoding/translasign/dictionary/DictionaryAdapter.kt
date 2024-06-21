package com.dicoding.translasign.dictionary

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.translasign.R
import com.dicoding.translasign.data.api.DictionaryData
import java.util.Locale

class DictionaryAdapter(private var items: List<DictionaryData>) :
    RecyclerView.Adapter<DictionaryAdapter.ViewHolder>(), Filterable {

    private var filteredItems: List<DictionaryData> = items

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.textView.text = item.name
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DictionaryScreenActivity::class.java).apply {
                putExtra("name", item.name)
                putExtra("description", item.description)
                putExtra("photo", item.photo)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = filteredItems.size

    fun updateList(newItems: List<DictionaryData>) {
        items = newItems
        filteredItems = newItems
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""
                filteredItems = if (charString.isEmpty()) {
                    items
                } else {
                    items.filter {
                        it.name?.lowercase(Locale.getDefault())?.contains(charString) == true
                    }
                }
                return FilterResults().apply { values = filteredItems }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as List<DictionaryData>
                notifyDataSetChanged()
            }
        }
    }
}
