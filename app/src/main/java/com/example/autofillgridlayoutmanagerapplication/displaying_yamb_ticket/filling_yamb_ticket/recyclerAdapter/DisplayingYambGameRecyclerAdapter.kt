package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.databinding.FillingYambTicketImageElementInRecyclerBinding
import com.example.autofillgridlayoutmanagerapplication.databinding.FillingYambTicketTextElementInRecyclerBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IViewModelForDisplayingYambTicket
import java.lang.IllegalArgumentException


class DisplayingYambGameRecyclerAdapter(
    private val context : Context,
    private var listOfItemsInRecycler : List<ItemInGame>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewModelReference : IViewModelForDisplayingYambTicket? = null

    inner class ImageViewHolder(private val imageElementBindingLayout: FillingYambTicketImageElementInRecyclerBinding) : RecyclerView.ViewHolder(imageElementBindingLayout.root) {
        internal fun bind(position: Int) {
            imageElementBindingLayout.itemInRecycler = listOfItemsInRecycler[position]
            imageElementBindingLayout.executePendingBindings()
        }
    }
    inner class TextViewHolder(private val textElementBindingLayout: FillingYambTicketTextElementInRecyclerBinding) : RecyclerView.ViewHolder(textElementBindingLayout.root) {
        internal fun bind(position: Int) {
            textElementBindingLayout.itemInGame = listOfItemsInRecycler[position]
            textElementBindingLayout.viewModel = viewModelReference
            textElementBindingLayout.positionOfItemClicked = position
            textElementBindingLayout.clickable = listOfItemsInRecycler[position].clickable
            textElementBindingLayout.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.filling_yamb_ticket_text_element_in_recycler ->{
                val textElementBindingLayout = FillingYambTicketTextElementInRecyclerBinding.inflate(LayoutInflater.from(context),parent,false)
                TextViewHolder(textElementBindingLayout)
            }
            R.layout.filling_yamb_ticket_image_element_in_recycler -> {
                val imageElementBindingLayout = FillingYambTicketImageElementInRecyclerBinding.inflate(LayoutInflater.from(context),parent,false)
                ImageViewHolder(imageElementBindingLayout)
            }
            else -> throw IllegalArgumentException("Unknown layout type.")
        }
    }
    override fun getItemCount(): Int {
        return listOfItemsInRecycler.count()
    }
    override fun getItemViewType(position: Int): Int {
        return listOfItemsInRecycler[position].layoutId
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(listOfItemsInRecycler[position].layoutId ){
            R.layout.filling_yamb_ticket_text_element_in_recycler -> { (holder as TextViewHolder).bind(position)
            }
            R.layout.filling_yamb_ticket_image_element_in_recycler -> {(holder as ImageViewHolder).bind(position)
            }
            else -> throw IllegalArgumentException("View type is out of bounds")
        }
    }

    fun changeYambGameForDisplay(newList :List<ItemInGame>){
        this.listOfItemsInRecycler = newList
        notifyDataSetChanged()
    }


}