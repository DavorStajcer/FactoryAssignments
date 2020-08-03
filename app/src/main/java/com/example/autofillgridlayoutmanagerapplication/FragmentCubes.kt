package com.example.autofillgridlayoutmanagerapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ViewSwitcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bacanjekockica.Cube
import kotlinx.android.synthetic.main.fragment_dice_roll.*


class FragmentCubes() : Fragment(R.layout.fragment_dice_roll){

    val viewModel : FragmentCubesViewModel = ViewModelProvider(this).get(FragmentCubesViewModel::class.java)
    val imageViews = listOf<ImageView>(ivCube1,ivCube2,ivCube3,ivCube4,ivCube5,ivCube6)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnVrti.isEnabled = true
        btnNajava.isEnabled = false

        var i = 1


        btnVrti.setOnClickListener { it ->
            viewModel.rollDice()
            displayCubes(imageViews,viewModel.cubes)
            if(i == 1){
                addListeners(viewModel.cubes)
                btnNajava.isEnabled = true
            }

            i = 2
        }

        btnNajava.setOnClickListener { _ ->
            this.viewModel.aheadCall.value = true
            btnNajava.isEnabled = false
           btnNajava.setBackgroundResource(R.drawable.btn_ahead_call_pressed)
        }


    }

    private fun addListeners(cubes: List<Cube>) { //ovako kad ih postavim, radi program
        ivCube1.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[0].pressed = !cubes[0].pressed
        }
        ivCube2.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[1].pressed = !cubes[1].pressed
        }
        ivCube3.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[2].pressed = !cubes[2].pressed
        }
        ivCube4.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[3].pressed = !cubes[3].pressed
        }
        ivCube5.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[4].pressed = !cubes[4].pressed
        }
        ivCube6.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[5].pressed = !cubes[5].pressed
        }
    }

    private fun displayCubes(imageViewsList: List<ImageView>, cubes: List<Cube>) {
       for((index,imageView) in imageViewsList.withIndex()){
            imageView.setImageResource(cubes[index].currentPicture)
       }
    }
}


