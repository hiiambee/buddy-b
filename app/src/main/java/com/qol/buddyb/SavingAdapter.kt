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
import java.time.Instant
import kotlin.collections.hashMapOf
import kotlin.collections.hashMapOf as hashMapOf1

class SavingAdapter (private val savList: ArrayList<SavingR>) : RecyclerView.Adapter<SavingAdapter.MyViewHolder>() {

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sav_list, parent, false)
        return SavingAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return savList.size
    }

    override fun onBindViewHolder(holder: SavingAdapter.MyViewHolder, position: Int) {
        val sav: SavingR = savList[position]
        holder.title.text = sav.title
        holder.amount.text = sav.amount.toString()
        holder.due.text = sav.due.toString()

        holder.deleteButton.setOnClickListener {
            deleteSav(position)
        }
    }

    private fun deleteSav(position: Int) {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val savId = savList[position].documentId

        db.collection("Database").document(uid)
            .collection("saving").document(savId!!)
            .delete()
            .addOnSuccessListener {
                savList.removeAt(position)
                notifyItemRemoved(position)
            }
            .addOnFailureListener { e ->
                Log.e("BillAdapter", "Error deleting bill: ${e.message}")
            }

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.savtitle)
        val amount: TextView = itemView.findViewById(R.id.savamount)
        val due: TextView = itemView.findViewById(R.id.savdate)
        val deleteButton: ImageView = itemView.findViewById(R.id.sv_delbutton)
    }

}