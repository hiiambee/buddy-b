package com.qol.buddyb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant

class BillAdapter(private val billList : ArrayList<Bills>) : RecyclerView.Adapter<BillAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bills_list,
        parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BillAdapter.MyViewHolder, position: Int) {
        val bill : Bills = billList[position]
        holder.title.text = bill.title
        holder.amount.text = bill.amount.toString()
        holder.due.text = bill.due.toString()
    }

    override fun getItemCount(): Int {
        return billList.size
    }

    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.billtitle)
        val amount : TextView = itemView.findViewById(R.id.billprice)
        val due : TextView = itemView.findViewById(R.id.billdate)

    }
}