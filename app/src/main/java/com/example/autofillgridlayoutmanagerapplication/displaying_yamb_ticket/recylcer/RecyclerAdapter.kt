package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.R
import java.lang.IllegalArgumentException


class RecyclerAdapter(
    private val context : Context,
    private val itemsInRecycler : List<ItemInRecycler>,
    private val onTextClicked : (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val image: ImageView = itemView.findViewById<ImageView>(R.id.ivGridElement)

        internal fun bind(position: Int){
            image.setImageResource(itemsInRecycler[position].data as Int)    //postavlja izvor slike koji treba bit na toj određenoj poziciji u RecyclerViewu
        }

    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById<TextView>(R.id.tvGridElement)

        internal fun bind(position: Int) {
            resetTextAndBackgroundOfView(text)
            setTextAndBackgorundsOfItems(itemsInRecycler[position],text,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            R.layout.grid_layout_element_text_view ->
                TextViewHolder(LayoutInflater.from(context).inflate(
                R.layout.grid_layout_element_text_view,parent,false))

            R.layout.grid_layout_element_image ->
                ImageViewHolder(LayoutInflater.from(context).inflate(
                R.layout.grid_layout_element_image,parent,false))

            else -> throw IllegalArgumentException("Unknown layout type.")
        }
    }
    override fun getItemCount(): Int {
        return itemsInRecycler.count()
    }
    override fun getItemViewType(position: Int): Int {
        return itemsInRecycler[position].layoutId // [0][0],[1][0],[2][0],[3][0],[4][0],[5][0], [0][1] ..
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(itemsInRecycler[position].layoutId ){
            R.layout.grid_layout_element_text_view -> { (holder as TextViewHolder).bind(position)
            }
            R.layout.grid_layout_element_image -> {(holder as ImageViewHolder).bind(position)
            }
            else -> throw IllegalArgumentException("View type is out of bounds")
        }
    }



    private fun resetTextAndBackgroundOfView(textView: TextView){
        textView.text = null
        textView.background = null
    }
    private fun setTextAndBackgorundsOfItems(item : ItemInRecycler, textView: TextView, position: Int){

        if (item.data != null) {
            textView.text = item.data.toString()
            textView.setBackgroundResource(R.drawable.text_view_value_set)
        }

        if(item.clickable){
                textView.setBackgroundResource(R.drawable.text_view_for_input)
                textView.setOnClickListener {
                    onTextClicked(position)
                }
        }

    }


}