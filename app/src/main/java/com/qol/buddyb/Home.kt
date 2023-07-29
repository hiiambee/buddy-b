package com.qol.buddyb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qol.buddyb.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Bills>
    private lateinit var myAdapter: BillAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recyclerView = binding.billhomerecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        myAdapter = BillAdapter(userArrayList)
        recyclerView.adapter = myAdapter

        getdisplayname()
        fetchmoneydata()
        fetchbilldata()

        return binding.root
    }

    private fun getdisplayname() {
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("displayname").document(uid)
        ref.get().addOnSuccessListener {
            if (it != null) {
                val disna = it.data?.get("displayname")?.toString()
                binding.userdisplay.text = "Welcome, $disna"
            }
        }
    }

    private fun fetchmoneydata() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val mainCollectionRef = db.collection("Database")
        val subCollectionRef = mainCollectionRef.document(uid).collection("expense")
        val subCollectionRef2 = mainCollectionRef.document(uid).collection("saving")
        var totalbal = 0

        subCollectionRef2.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    var sum2 = 0

                    for (document in documents) {
                        val savingData = document.toObject(Saving::class.java)
                        sum2 += savingData.amount ?: 0
                    }
                    totalbal += sum2
                    val amountRedTextView = view?.findViewById<TextView>(R.id.homeinc)
                    amountRedTextView?.text = "PHP $sum2"

                } else {
                    val amountRedTextView = view?.findViewById<TextView>(R.id.homeinc)
                    amountRedTextView?.text = "PHP 0.00"
                }

                subCollectionRef.get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            var sum = 0

                            for (document in documents) {
                                val expenseData = document.toObject(Expense::class.java)
                                sum += expenseData.amount ?: 0
                            }
                            totalbal -= sum
                            val amountRedTextView = view?.findViewById<TextView>(R.id.homeexp)
                            amountRedTextView?.text = "PHP $sum"
                            val amountbalTextView = view?.findViewById<TextView>(R.id.hometotal)
                            amountbalTextView?.text = "PHP $totalbal"

                        } else {
                            val amountRedTextView = view?.findViewById<TextView>(R.id.homeexp)
                            amountRedTextView?.text = "PHP 0.00"
                        }
                    }
                    .addOnFailureListener { exception ->
                        val amountRedTextView = view?.findViewById<TextView>(R.id.homeexp)
                        amountRedTextView?.text = "OFFLINE"
                    }


            }
            .addOnFailureListener { exception ->
                val amountRedTextView = view?.findViewById<TextView>(R.id.homeinc)
                amountRedTextView?.text = "OFFLINE"
                val amountExpTextView = view?.findViewById<TextView>(R.id.homeexp)
                amountExpTextView?.text = "OFFLINE"
            }

    }

    private fun fetchbilldata() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Database").document(uid)
            .collection("bills")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
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
