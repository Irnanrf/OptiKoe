package com.irnanrf.optikoecapstone.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irnanrf.optikoecapstone.DetailProductActivity
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.databinding.ItemHomeProductBinding
import com.irnanrf.optikoecapstone.util.toRupiah

@SuppressLint("NotifyDataSetChanged")
class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var data = ArrayList<Product>()


    inner class ViewHolder(private val itemBinding: ItemHomeProductBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Product, position: Int) {
            itemBinding.apply {
                tvName.text = item.name
                Glide.with(itemBinding.root)
                    .load(item.image)
                    .into(imageView)
                tvHarga.text = item.price.toRupiah()
                tvLocation.text = item.location
                tvFaceshape.text = item.faceshape
            }


        }
    }

    fun removeItems(){
        data.clear()
    }

    fun addItems(items: List<Product>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailProductActivity::class.java)
            intentDetail.putExtra("product_id", data[holder.adapterPosition].id.toString())
            intentDetail.putExtra("product_name", data[holder.adapterPosition].name.toString())
            intentDetail.putExtra("product_price", data[holder.adapterPosition].price.toString())
            intentDetail.putExtra("product_desc", data[holder.adapterPosition].desc.toString())
            intentDetail.putExtra("product_face", data[holder.adapterPosition].faceshape.toString())
            intentDetail.putExtra("product_location", data[holder.adapterPosition].location.toString())
            intentDetail.putExtra("product_image", data[holder.adapterPosition].image.toString())
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}