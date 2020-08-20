package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.databinding.GridLayoutElementImageBinding
import com.example.autofillgridlayoutmanagerapplication.databinding.GridLayoutElementTextViewBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IOnItemInRecyclerClickedListener
import java.lang.IllegalArgumentException


class RecyclerAdapterForDisplayingYambGame(
    private val context : Context,
    private var itemsInRecycler : List<ItemInRecycler>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClickListener : IOnItemInRecyclerClickedListener? = null

    inner class ImageViewHolder(private val imageElementBindingLayout: GridLayoutElementImageBinding) : RecyclerView.ViewHolder(imageElementBindingLayout.root) {
        internal fun bind(position: Int) {
            imageElementBindingLayout.itemInRecycler = itemsInRecycler[position]
            imageElementBindingLayout.executePendingBindings()
        }
    }
    inner class TextViewHolder(private val textElementBindingLayout: GridLayoutElementTextViewBinding) : RecyclerView.ViewHolder(textElementBindingLayout.root) {
        internal fun bind(position: Int) {
            textElementBindingLayout.itemInRecycler = itemsInRecycler[position]
            textElementBindingLayout.onClick = onClickListener
            textElementBindingLayout.positionOfItemClicked = position
            textElementBindingLayout.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.grid_layout_element_text_view ->{
                val textElementBindingLayout = GridLayoutElementTextViewBinding.inflate(LayoutInflater.from(context),parent,false)
                TextViewHolder(textElementBindingLayout)
            }
            R.layout.grid_layout_element_image -> {
                val imageElementBindingLayout = GridLayoutElementImageBinding.inflate(LayoutInflater.from(context),parent,false)
                ImageViewHolder(imageElementBindingLayout)
            }
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

    fun changeYambGameForDisplay(newList :List<ItemInRecycler>){
        this.itemsInRecycler = newList
        notifyDataSetChanged()
    }


}