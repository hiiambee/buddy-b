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

class Savings : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var expenseRecyclerView: RecyclerView
    private lateinit var savingRecyclerView: RecyclerView
    private lateinit var expenseArrayList: ArrayList<ExpenseR>
    private lateinit var savingArrayList: ArrayList<SavingR>
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var savingAdapter: SavingAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_savings, container, false)

        expenseRecyclerView = rootView.findViewById(R.id.exprecycler)
        savingRecyclerView = rootView.findViewById(R.id.savrecycler)
        expenseRecyclerView.layoutManager = LinearLayoutManager(context)
        savingRecyclerView.layoutManager = LinearLayoutManager(context)

        expenseArrayList = arrayListOf()
        savingArrayList = arrayListOf()

        expenseAdapter = ExpenseAdapter(expenseArrayList)
        savingAdapter = SavingAdapter(savingArrayList)

        expenseRecyclerView.adapter = expenseAdapter
        savingRecyclerView.adapter = savingAdapter

        fetchsavdata()
        fetchexpdata()

        val addbutton = rootView.findViewById<ImageView>(R.id.addsavbutton)
        addbutton.setOnClickListener{
            val intent = Intent(requireContext(), SavAdd::class.java)
            startActivity(intent)
        }

        val addexpbutton = rootView.findViewById<ImageView>(R.id.addexpbutton)
        addexpbutton.setOnClickListener{
            val intent = Intent(requireContext(), ExpAdd::class.java)
            startActivity(intent)
        }

        return rootView
    }

    private fun fetchexpdata() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Database").document(uid)
            .collection("expense")
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
                            val exp = dc.document.toObject(ExpenseR::class.java)
                            exp.documentId = dc.document.id
                            expenseArrayList.add(exp)
                        }
                    }

                    expenseAdapter.notifyDataSetChanged()
                }


            })
    }


    private fun fetchsavdata() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Database").document(uid)
            .collection("saving")
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
                            val sav = dc.document.toObject(SavingR::class.java)
                            sav.documentId = dc.document.id
                            savingArrayList.add(sav)
                        }
                    }

                    savingAdapter.notifyDataSetChanged()
                }


            })
    }


}
