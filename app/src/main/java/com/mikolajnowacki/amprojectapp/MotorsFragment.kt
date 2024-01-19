package com.mikolajnowacki.amprojectapp

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mikolajnowacki.amprojectapp.databinding.FragmentMotorsBinding
import com.mikolajnowacki.amprojectapp.databinding.ItemMotorMonitorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class MotorsFragment : Fragment() {
    private lateinit var binding: FragmentMotorsBinding
    private lateinit var motorCollectionRef: CollectionReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMotorsBinding.inflate(inflater, container, false)
        motorCollectionRef = Firebase.firestore.collection(arguments?.getString("userID") ?: "SyncDataError")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        realtimeUpdates()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun realtimeUpdates() {
        motorCollectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val motorList: MutableList<Motor> = ArrayList()
                for (document in it) {
                    val motor = Motor(
                        document.getString("name") ?: "Motor",
                        document.getString("voltageRating") ?: "0",
                        document.getString("maxRPM") ?: "0",
                        document.getString("currentRPM") ?: "0",
                        document.getString("maxLoad") ?: "N/A",
                        document.getString("status") ?: "offline"
                    )
                    motorList.add(motor)
                }
                val motorAdapter = MotorAdapter(motorList)
                binding.listViewMotors.adapter = motorAdapter
            }
        }
    }
}