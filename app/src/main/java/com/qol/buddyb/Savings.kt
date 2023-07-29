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
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<SavingR>
    private lateinit var myAdapter: SavingAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_savings, container, false)

        recyclerView = rootView.findViewById(R.id.savrecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        myAdapter = SavingAdapter(userArrayList)
        recyclerView.adapter = myAdapter

        fetchsavdata()

        val addbutton = rootView.findViewById<ImageView>(R.id.addsavbutton)
        addbutton.setOnClickListener{
            val intent = Intent(requireContext(), SavAdd::class.java)
            startActivity(intent)
        }

        return rootView
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
                            userArrayList.add(sav)
                        }
                    }

                    myAdapter.notifyDataSetChanged()
                }


            })
    }


}
