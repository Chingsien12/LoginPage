package com.example.loginpage

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_post_user.view.*


class PostAdapterUser(context: Context, private val Posts: ArrayList<Post>/*,private val listener:(Post)->Unit*/) :
    RecyclerView.Adapter<PostAdapterUser.ViewHolder>() {
    //inherited form the RecycleView class

    private var onClickItem:((Post)->Unit)?=null//
    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)// hold the view of the R.layout.li_post as all textView
    {
        val pid = itemView.PID
        val title = itemView.Title
        val description = itemView.Desc
        val location = itemView.location
        val application = itemView.application
        val contract = itemView.contract
        val date = itemView.Date
        val email=itemView.email
        val phone=itemView.phone
        val check=itemView.check
        fun bind(post:Post)
        {
            pid.text=post.pid.toString()
            title.text=post.title
            description.text=post.description
            location.text=post.location
            application.text=post.application
            contract.text=post.contract
            date.text=post.date
            email.text=post.email
            phone.text=post.phone
        }
    }

    fun setOnclickItem(callbacks:(Post)->Unit )
    {
        this.onClickItem=callbacks
    }//

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {//need to return the view holder
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.li_post_user,
            parent,
            false
        )//li_post is not attach to the root so it refer to the textView in the constraint layout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: Post = Posts[position]
        var i:Int=0
        holder.bind(post)
        holder.pid.text = post.pid.toString()
        holder.title.text = post.title
        holder.description.text = post.description
        holder.location.text = post.location
        holder.application.text = post.application
        holder.contract.text = post.contract
        holder.date.text = post.date
        holder.email.text=post.email
        holder.phone.text=post.phone
        holder.itemView.setOnClickListener {//
            onClickItem?.invoke(post)
//            if(i==0)
//            {
//                holder.check.visibility=View.VISIBLE
//                i=1
//            }else
//            {
//                holder.check.visibility=View.GONE
//                i=0
//            }

        }

    }

    override fun getItemCount(): Int {
        return Posts.size
    }
}