package com.mikolajnowacki.amprojectapp

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mikolajnowacki.amprojectapp.databinding.ActivityAddNewMotorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AddNewMotorActivity : AppCompatActivity() {

    private lateinit var motorCollectionRef: CollectionReference
    private lateinit var binding: ActivityAddNewMotorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewMotorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        motorCollectionRef =
            Firebase.firestore.collection(intent.getSerializableExtra("userID") as String)

        val spinnerValues: Array<String> = arrayOf("offline", "online")
        val spinnerAdapter =
            ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, spinnerValues)
        binding.spinnerStatus.adapter = spinnerAdapter

        binding.buttonSave.setOnClickListener {
            val motorName = binding.editTextName.text.toString()
            val motorRating = binding.editTextRating.text.toString()
            val motorCurrentRPM = "0"
            val motorMaxRPM = binding.editTextMaxRpm.text.toString()
            val motorMaxOverload = binding.editTextMaxOverload.text.toString()
            val motorStatus = binding.spinnerStatus.selectedItem.toString()
            val motor = Motor(
                motorName,
                motorRating,
                motorMaxRPM,
                motorCurrentRPM,
                motorMaxOverload,
                motorStatus
            )
            uploadMotor(motor)
            finish()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun uploadMotor(motor: Motor) = CoroutineScope(Dispatchers.IO).launch {
        try {
            motorCollectionRef.add(motor).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@AddNewMotorActivity,
                    "Motor settings successfully saved!",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@AddNewMotorActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}