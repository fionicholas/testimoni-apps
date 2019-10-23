package com.fionicholas.kotlinfirebase.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fionicholas.kotlinfirebase.R
import com.fionicholas.kotlinfirebase.model.Testimoni
import com.google.firebase.database.FirebaseDatabase


class TestiAdapter(val mContext: Context,val layoutResId: Int, val testiList : List<Testimoni>)
    : ArrayAdapter<Testimoni>(mContext, layoutResId, testiList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val tvName = view.findViewById<TextView>(R.id.textName)
        val tvComment = view.findViewById<TextView>(R.id.textComent)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)

        val testi = testiList[position]

        tvName.text = testi.name
        tvComment.text = testi.comment

        btnEdit.setOnClickListener {
            showUpdateDialog(testi)
        }

        return view;
    }

    fun showUpdateDialog(testi: Testimoni) {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Update Testimoni")
        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.layout_update_testimoni, null)

        val editName = view.findViewById<EditText>(R.id.et_name)
        val editComment = view.findViewById<EditText>(R.id.et_comment)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        editName.setText(testi.name)
        editComment.setText(testi.comment)
        ratingBar.rating = testi.rating.toFloat()
        builder.setView(view)
        builder.setPositiveButton("Update") { p0, p1 ->
            val dbTestimoni = FirebaseDatabase.getInstance().getReference("testimoni")

            val name = editName.text.toString().trim()
            val comment = editComment.text.toString().trim()

            if(name.isEmpty()){
                editName.error = "Please Enter a Name"
                editName.requestFocus()

                return@setPositiveButton
            }

            val testi = Testimoni(testi.id, name, comment, ratingBar.rating.toInt())
            dbTestimoni.child(testi.id!!).setValue(testi)

            Toast.makeText(mContext, "Success Updated Testimoni", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("Cancel") { p0, p1 ->

        }

        val alert = builder.create()
        alert.show()

    }
}