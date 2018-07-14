package com.cagataymuhammet.kotlinapisample.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cagataymuhammet.kotlinapisample.R
import com.cagataymuhammet.kotlinapisample.model.UserModel


class UserAdapter(val userList: List<UserModel>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.txtID?.text = userList[position].id.toString()
        holder?.txtTitle?.text = userList[position].title
        holder?.txtBody?.text = userList[position].body
    }

    override fun onCreateViewHolder(parentView: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parentView?.context).inflate(R.layout.carview_user, parentView, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)
        val txtBody = itemView.findViewById<TextView>(R.id.txt_body)
        val txtID = itemView.findViewById<TextView>(R.id.txt_user_id)
    }

}