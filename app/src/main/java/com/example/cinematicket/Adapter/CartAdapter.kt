import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinematicket.Model.CartItem
import com.example.cinematicket.databinding.ViewholderCartBinding

class CartAdapter(private var itemList: List<CartItem> = emptyList()) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    // ViewHolder class
    class ViewHolder(val binding: ViewholderCartBinding): RecyclerView.ViewHolder(binding.root)

    // Inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Return the size of the current item list
    override fun getItemCount(): Int {
        return itemList.size
    }

    // Bind data to each ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.binding.filmTitle.text = item.title
        holder.binding.numTicketTxt.text = "num of tickets: ${item.numberOfTickets}"
        holder.binding.totalPriceTxt.text = "Total Price: ${item.totalPrice}"

        Glide.with(holder.itemView.context)
            .load(item.filmImage)
            .into(holder.binding.filmPic)
    }

    // Update the list when new data is available
    fun updateList(newList: List<CartItem>) {
        itemList = newList
        notifyDataSetChanged() // Notify the adapter that data has changed
    }
}
