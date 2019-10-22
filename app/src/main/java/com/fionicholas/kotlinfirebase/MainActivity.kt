package com.fionicholas.kotlinfirebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.fionicholas.kotlinfirebase.adapter.TestiAdapter
import com.fionicholas.kotlinfirebase.model.Testimoni
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etComment: EditText
    lateinit var ratingBar: RatingBar
    lateinit var btnSave: Button
    lateinit var ref: DatabaseReference
    lateinit var testiList: MutableList<Testimoni>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testiList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("testimoni")

        etName = findViewById(R.id.et_name)
        etComment = findViewById(R.id.et_comment)
        ratingBar = findViewById(R.id.ratingBar)
        btnSave = findViewById(R.id.btnSave)
        listView = findViewById(R.id.listView)

        btnSave.setOnClickListener {
            saveTestimoni()
        }

        ref.addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    testiList.clear()
                    for(h in p0.children){
                        val testi = h.getValue(Testimoni::class.java)
                        testiList.add(testi!!)
                    }
                    val adapter = TestiAdapter(applicationContext, R.layout.card_testimoni, testiList)
                    listView.adapter = adapter

                }
            }

        })
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




        val testiId = ref.push().key

        val testi = Testimoni(testiId, name, comment, ratingBar.numStars)
        ref.child(testiId!!).setValue(testi).addOnCompleteListener {
            Toast.makeText(applicationContext, "Testi has been sent", Toast.LENGTH_LONG).show()
        }
    }
}
