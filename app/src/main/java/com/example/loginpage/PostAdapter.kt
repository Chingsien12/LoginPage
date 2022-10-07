package com.example.loginpage
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_post.view.*
import kotlinx.android.synthetic.main.li_post.view.Title
import org.w3c.dom.Text

class PostAdapter(context: Context, private val Posts: ArrayList<Post>,
//private val listener: OnItemClickListener
                  ) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    //inherited form the RecycleView class
   inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)//, View.OnClickListener// hold the view of the R.layout.li_post as all textView
    {
//        val pis=itemView.findViewById<TextView>(R.id.editText)
        //REGISTER THE ID

        val pid = itemView.PID
        val title = itemView.Title
        val description = itemView.Desc
        val location = itemView.location
        val application = itemView.application
        val contract = itemView.contract
        val date = itemView.Date
        val email=itemView.email
        val phone=itemView.phone

//        init {
//            itemView.setOnClickListener { this }
//        }
//
//        override fun onClick(p0: View?) {
//            val position=adapterPosition
//            if(position!=RecyclerView.NO_POSITION)
//            {
//                listener.onItemClick(position)
//            }
//        }
    }
    //creating the interface
//interface OnItemClickListener
//    {
//        fun onItemClick(position: Int)
//    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {//need to return the view holder
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.li_post,
            parent,
            false
        )//li_post is not attach to the root so it refer to the textView in the constraint layout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: Post = Posts[position]
        holder.pid.text = post.pid.toString()
        holder.title.text = post.title
        holder.description.text = post.description
        holder.location.text = post.location
        holder.application.text = post.application
        holder.contract.text = post.contract
        holder.date.text = post.date
        holder.email.text=post.email
        holder.phone.text=post.phone
        // using inint function

    }
    override fun getItemCount(): Int {
        return Posts.size
    }
}