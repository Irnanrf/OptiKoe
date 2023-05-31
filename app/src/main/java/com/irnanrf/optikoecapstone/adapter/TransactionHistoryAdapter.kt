package com.irnanrf.optikoecapstone.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irnanrf.optikoecapstone.DetailProductActivity
import com.irnanrf.optikoecapstone.DetailTransactionActivity
import com.irnanrf.optikoecapstone.R
import com.irnanrf.optikoecapstone.data.model.TransactionHistory
import com.irnanrf.optikoecapstone.databinding.ItemTransactionHistoryBinding
import com.irnanrf.optikoecapstone.util.toRupiah

@SuppressLint("NotifyDataSetChanged")
class TransactionHistoryAdapter : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    private var data = ArrayList<TransactionHistory>()


    inner class ViewHolder(private val itemBinding: ItemTransactionHistoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TransactionHistory, position: Int) {
            itemBinding.apply {
                tvDate.text = item.date
                tvStatus.text = item.status
                imgProduct.setImageResource(item.image)
                tvProductName.text = item.productName
                tvQuantity.text = "Quantity : " + item.quantity
                tvTotalprice.text = item.totalPrice.toRupiah()
            }


        }
    }

    fun removeItems(){
        data.clear()
    }

    fun addItems(items: List<TransactionHistory>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailTransactionActivity::class.java)
            intentDetail.putExtra("productName", data[holder.adapterPosition].productName.toString())
            intentDetail.putExtra("productPrice", data[holder.adapterPosition].productPrice.toString())
            intentDetail.putExtra("quantity", data[holder.adapterPosition].quantity.toString())
            intentDetail.putExtra("shippingAddress", data[holder.adapterPosition].shippingAddress.toString())
            intentDetail.putExtra("shippingCourier", data[holder.adapterPosition].shippingCourier.toString())
            intentDetail.putExtra("shippingCost", data[holder.adapterPosition].shippingCost.toString())
            intentDetail.putExtra("shippingName", data[holder.adapterPosition].shippingName.toString())
            intentDetail.putExtra("shippingPhone", data[holder.adapterPosition].shippingPhone.toString())
            intentDetail.putExtra("totalPrice", data[holder.adapterPosition].totalPrice.toString())
            intentDetail.putExtra("paymentMethod", data[holder.adapterPosition].paymentMethod.toString())
            intentDetail.putExtra("status", data[holder.adapterPosition].status.toString())
            intentDetail.putExtra("date", data[holder.adapterPosition].date.toString())
            intentDetail.putExtra("image", data[holder.adapterPosition].image.toString())
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}