import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewardscodingexercise.Model.Item
import com.example.fetchrewardscodingexercise.R

class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val listIdTextView: TextView = itemView.findViewById(R.id.listIdTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.listIdTextView.text = "ListID: ${currentItem.listId.toString()}"
        holder.nameTextView.text = "Name: ${currentItem.name}"
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
