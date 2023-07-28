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

class BillAdapter(private val billList: ArrayList<Bills>) : RecyclerView.Adapter<BillAdapter.MyViewHolder>() {

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bills_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BillAdapter.MyViewHolder, position: Int) {
        val bill: Bills = billList[position]
        holder.title.text = bill.title
        holder.amount.text = bill.amount.toString()
        holder.due.text = bill.due.toString()

        holder.deleteButton.setOnClickListener {
            deleteBill(position)
        }

        holder.checkbutton.setOnClickListener{
            checkbill(position)
        }
    }

    private fun checkbill(position: Int) {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        // Get the bill document ID from the Firestore based on its position in the list
        val billId = billList[position].documentId

        db.collection("Database").document(uid)
            .collection("bills").document(billId!!)
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    val title = it.data?.get("title")?.toString()
                    val amount = it.data?.get("amount")
                    val due = it.data?.get("due")

                    val billmap = hashMapOf(
                        "title" to title,
                        "amount" to amount,
                        "date" to due
                    )

                    db.collection("Database").document(uid)
                        .collection("expense")
                        .document()
                        .set(billmap)
                        .addOnSuccessListener {

                            // Remove the bill from Firestore
                            db.collection("Database").document(uid)
                                .collection("bills").document(billId!!)
                                .delete()
                                .addOnSuccessListener {
                                    // Remove the bill from the list and update the RecyclerView
                                    billList.removeAt(position)
                                    notifyItemRemoved(position)
                                }
                                .addOnFailureListener { e ->
                                    // Handle any errors that occur during deletion
                                    Log.e("BillAdapter", "Error deleting bill: ${e.message}")
                                }

                        }
                        .addOnFailureListener { e ->
                            // Handle any errors that occur during deletion
                            Log.e("BillAdapter", "Error deleting bill: ${e.message}")
                        }

                }


            }
            .addOnFailureListener { e ->
                // Handle any errors that occur during deletion
                Log.e("BillAdapter", "Error deleting bill: ${e.message}")
            }
    }

    override fun getItemCount(): Int {
        return billList.size
    }

    private fun deleteBill(position: Int) {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        // Get the bill document ID from the Firestore based on its position in the list
        val billId = billList[position].documentId

        // Remove the bill from Firestore
        db.collection("Database").document(uid)
            .collection("bills").document(billId!!)
            .delete()
            .addOnSuccessListener {
                // Remove the bill from the list and update the RecyclerView
                billList.removeAt(position)
                notifyItemRemoved(position)
            }
            .addOnFailureListener { e ->
                // Handle any errors that occur during deletion
                Log.e("BillAdapter", "Error deleting bill: ${e.message}")
            }
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.billtitle)
        val amount: TextView = itemView.findViewById(R.id.billprice)
        val due: TextView = itemView.findViewById(R.id.billdate)
        val deleteButton: ImageView = itemView.findViewById(R.id.b_delbutton)
        val checkbutton: ImageView = itemView.findViewById(R.id.b_checkbutton)
    }
}