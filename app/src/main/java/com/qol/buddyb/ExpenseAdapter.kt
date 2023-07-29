package com.qol.buddyb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpenseAdapter(private val expList: ArrayList<ExpenseR>) : RecyclerView.Adapter<ExpenseAdapter.MyViewHolder>(){

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exp_list, parent, false)
        return ExpenseAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return expList.size
    }

    override fun onBindViewHolder(holder: ExpenseAdapter.MyViewHolder, position: Int) {
        val exp: ExpenseR = expList[position]
        holder.title.text = exp.title
        holder.amount.text = exp.amount.toString()
        holder.due.text = exp.due.toString()

        holder.deleteButton.setOnClickListener {
            deleteExp(position)
        }
    }

    private fun deleteExp(position: Int) {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val billId = expList[position].documentId

        db.collection("Database").document(uid)
            .collection("expense").document(billId!!)
            .delete()
            .addOnSuccessListener {
                expList.removeAt(position)
                notifyItemRemoved(position)
            }
            .addOnFailureListener { e ->
                Log.e("BillAdapter", "Error deleting bill: ${e.message}")
            }

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.exptitle)
        val amount: TextView = itemView.findViewById(R.id.expamount)
        val due: TextView = itemView.findViewById(R.id.expdate)
        val deleteButton: ImageView = itemView.findViewById(R.id.ex_delbutton)
    }
}