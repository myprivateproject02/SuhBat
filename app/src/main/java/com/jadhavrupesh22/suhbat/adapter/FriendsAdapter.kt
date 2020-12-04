package com.jadhavrupesh22.suhbat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.utils.GetTimeAgo
import kotlinx.android.synthetic.main.single_user_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class FriendsAdapter(
    var allUser: List<User>,
    context: Context,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_user_layout, parent, false)
        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.itemView.apply {
            username.text = allUser[position].username
            val time = GetTimeAgo.getTimeAgo(allUser[position].lastSeen.seconds)
            lastSeen.text = time
            Glide.with(context)
                .load(allUser[position].profileUrl)
                .into(profileImg);
        }
    }

    override fun getItemCount(): Int {
        return allUser.size
    }

    inner class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}