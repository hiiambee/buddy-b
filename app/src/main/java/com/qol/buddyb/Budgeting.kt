package com.qol.buddyb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Budgeting : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Bills>
    private lateinit var myAdapter: BillAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_budgeting, container, false)

        recyclerView = rootView.findViewById(R.id.billrecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        myAdapter = BillAdapter(userArrayList)
        recyclerView.adapter = myAdapter

        fetchmoneydata()
        fetchbilldata()

        val addbutton = rootView.findViewById<ImageView>(R.id.addbillbutton)
        addbutton.setOnClickListener{
            val intent = Intent(requireContext(), BillAdd::class.java)
            startActivity(intent)
        }


        return rootView


    }

    private fun fetchmoneydata() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val mainCollectionRef = db.collection("Database")
        val subCollectionRef = mainCollectionRef.document(uid).collection("expense")
        val subCollectionRef2 = mainCollectionRef.document(uid).collection("saving")
        var totalbal = 0

        subCollectionRef.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    var sum = 0

                    for (document in documents) {
                        val expenseData = document.toObject(Expense::class.java)
                        sum += expenseData.amount?: 0
                    }
                    totalbal -= sum
                    val amountRedTextView = view?.findViewById<TextView>(R.id.amountred)
                    amountRedTextView?.text = "PHP $sum"

                } else {
                    val amountRedTextView = view?.findViewById<TextView>(R.id.amountred)
                    amountRedTextView?.text = "PHP 0.00"
                }
            }
            .addOnFailureListener { exception ->
                val amountRedTextView = view?.findViewById<TextView>(R.id.amountred)
                amountRedTextView?.text = "OFFLINE"
            }

        subCollectionRef2.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    var sum2 = 0

                    for (document in documents) {
                        val savingData = document.toObject(Saving::class.java)
                        sum2 += savingData.amount?: 0
                    }
                    totalbal += sum2
                    val amountRedTextView = view?.findViewById<TextView>(R.id.amountgreen)
                    amountRedTextView?.text = "PHP $sum2"
                    val amountbalTextView = view?.findViewById<TextView>(R.id.amounttotal)
                    amountbalTextView?.text = "PHP $totalbal"

                } else {
                    val amountRedTextView = view?.findViewById<TextView>(R.id.amountgreen)
                    amountRedTextView?.text = "PHP 0.00"
                }
            }
            .addOnFailureListener { exception ->
                val amountRedTextView = view?.findViewById<TextView>(R.id.amountgreen)
                amountRedTextView?.text = "OFFLINE"
            }

    }


    private fun fetchbilldata() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Database").document(uid)
            .collection("bills")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null){
                        Log.e("error",error.message.toString())
                        return
                    }

                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            val bill = dc.document.toObject(Bills::class.java)
                            bill.documentId = dc.document.id
                            userArrayList.add(bill)
                        }
                    }

                    myAdapter.notifyDataSetChanged()
                }


            })
    }
    

}

