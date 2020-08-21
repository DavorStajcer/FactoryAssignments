package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.databinding.ImageElementRecyclerBinding
import com.example.autofillgridlayoutmanagerapplication.databinding.TextElementRecyclerBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IViewModelForDisplayingYambTicket
import java.lang.IllegalArgumentException


class RecyclerAdapterForDisplayingYambGame(
    private val context : Context,
    private var itemsInRecycler : List<ItemInRecycler>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewModelReference : IViewModelForDisplayingYambTicket? = null

    inner class ImageViewHolder(private val imageElementBindingLayout: ImageElementRecyclerBinding) : RecyclerView.ViewHolder(imageElementBindingLayout.root) {
        internal fun bind(position: Int) {
            imageElementBindingLayout.itemInRecycler = itemsInRecycler[position]
            imageElementBindingLayout.executePendingBindings()
        }
    }
    inner class TextViewHolder(private val textElementBindingLayout: TextElementRecyclerBinding) : RecyclerView.ViewHolder(textElementBindingLayout.root) {
        internal fun bind(position: Int) {
            textElementBindingLayout.itemInRecycler = itemsInRecycler[position]
            textElementBindingLayout.viewModel = viewModelReference
            textElementBindingLayout.positionOfItemClicked = position
            textElementBindingLayout.clickable = itemsInRecycler[position].clickable
            textElementBindingLayout.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.text_element_recycler ->{
                val textElementBindingLayout = TextElementRecyclerBinding.inflate(LayoutInflater.from(context),parent,false)
                TextViewHolder(textElementBindingLayout)
            }
            R.layout.image_element_recycler -> {
                val imageElementBindingLayout = ImageElementRecyclerBinding.inflate(LayoutInflater.from(context),parent,false)
                ImageViewHolder(imageElementBindingLayout)
            }
            else -> throw IllegalArgumentException("Unknown layout type.")
        }
    }
    override fun getItemCount(): Int {
        return itemsInRecycler.count()
    }
    override fun getItemViewType(position: Int): Int {
        return itemsInRecycler[position].layoutId
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(itemsInRecycler[position].layoutId ){
            R.layout.text_element_recycler -> { (holder as TextViewHolder).bind(position)
            }
            R.layout.image_element_recycler -> {(holder as ImageViewHolder).bind(position)
            }
            else -> throw IllegalArgumentException("View type is out of bounds")
        }
    }

    fun changeYambGameForDisplay(newList :List<ItemInRecycler>){
        this.itemsInRecycler = newList
        notifyDataSetChanged()
    }


}