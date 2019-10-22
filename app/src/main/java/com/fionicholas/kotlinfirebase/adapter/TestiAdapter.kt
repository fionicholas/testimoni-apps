package com.fionicholas.kotlinfirebase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fionicholas.kotlinfirebase.R
import com.fionicholas.kotlinfirebase.model.Testimoni



class TestiAdapter(val mContext: Context,val layoutResId: Int, val testiList : List<Testimoni>)
    : ArrayAdapter<Testimoni>(mContext, layoutResId, testiList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val tvName = view.findViewById<TextView>(R.id.textName)
        val tvComment = view.findViewById<TextView>(R.id.textComent)

        val testi = testiList[position]

        tvName.text = testi.name
        tvComment.text = testi.comment

        return view;
    }
}