package com.mikolajnowacki.amprojectapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mikolajnowacki.amprojectapp.databinding.FragmentControllerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ControllerFragment : Fragment() {
    private lateinit var binding: FragmentControllerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentControllerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddNewMotor.setOnClickListener {
            val addNewMotorIntent = Intent(activity, AddNewMotorActivity::class.java)
            addNewMotorIntent.putExtra("userID", arguments?.getString("userID"))
            startActivity(addNewMotorIntent)
        }
    }

}