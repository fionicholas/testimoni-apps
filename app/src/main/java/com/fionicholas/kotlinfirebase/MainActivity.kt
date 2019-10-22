package com.fionicholas.kotlinfirebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.fionicholas.kotlinfirebase.model.Testimoni
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etComment: EditText
    lateinit var ratingBar: RatingBar
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.et_name)
        etComment = findViewById(R.id.et_comment)
        ratingBar = findViewById(R.id.ratingBar)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            saveTestimoni()
        }
    }
    private fun saveTestimoni(){
        val name = etName.text.toString().trim()
        val comment = etComment.text.toString().trim()

        if(name.isEmpty()){
            etName.error = "Please enter a name"
            return
        }
        if(comment.isEmpty()){
            etComment.error = "Please enter a comment"
            return
        }


        val ref = FirebaseDatabase.getInstance().getReference("testimoni")

        val testiId = ref.push().key

        val testi = Testimoni(testiId, name, comment, ratingBar.numStars)
        ref.child(testiId!!).setValue(testi).addOnCompleteListener {
            Toast.makeText(applicationContext, "Testi has been sent", Toast.LENGTH_LONG).show()
        }
    }
}
