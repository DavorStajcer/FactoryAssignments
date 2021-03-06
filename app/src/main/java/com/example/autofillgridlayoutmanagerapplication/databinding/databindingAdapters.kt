package com.example.autofillgridlayoutmanagerapplication.databinding

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.ItemInGame

@BindingAdapter("imageSource")
    fun bindImageToImageView(imageView : ImageView, imageResource : Int){
        imageView.setImageResource(imageResource)
    }

    @BindingAdapter("recyclerImage")
    fun bindImageToImageViewInRecycler(imageView: ImageView, string: String){
        imageView.setImageResource(string.toInt())
    }


    @BindingAdapter("clickableState")
    fun setClickableStateOfRollingCube(imageView: ImageView,clickable : Boolean){
        imageView.isEnabled = clickable
    }

    @BindingAdapter("changeBackground")
    fun changeBackgroudnOfAheadCallButton(button: Button,backgroundResource : Int){
        button.setBackgroundResource(backgroundResource)
    }

    @BindingAdapter("item")
    fun bindTextToTextViewInRecycler(textView: TextView, item: ItemInGame){
        textView.text = null
        textView.background = null
        if (item.data != null) {
            textView.text = item.data.toString()
            textView.setBackgroundResource(R.drawable.text_view_value_set)
        }else if(item.clickable){
            textView.setBackgroundResource(R.drawable.text_view_for_input)
        }
    }


//