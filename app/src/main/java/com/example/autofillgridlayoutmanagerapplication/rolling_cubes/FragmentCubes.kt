package com.example.autofillgridlayoutmanagerapplication.rolling_cubes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.MainActivity
import com.example.autofillgridlayoutmanagerapplication.R

import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.CubeIndexes
import com.example.bacanjekockica.Cube
import kotlinx.android.synthetic.main.fragmet_cubes_layout.*


class FragmentCubes() : Fragment(R.layout.fragmet_cubes_layout){


    lateinit var viewModel : FragmentCubesViewModel
    lateinit var imageViews : List<ImageView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         viewModel = ViewModelProvider(requireActivity()).get(FragmentCubesViewModel::class.java)

        imageViews = listOf<ImageView>(ivCube1,ivCube2,ivCube3,ivCube4,ivCube5,ivCube6)

        viewModel.setListeners.observe(viewLifecycleOwner, Observer {
            addListeners()
        })
        viewModel.cubes.observe(viewLifecycleOwner, Observer {
            displayCubes(imageViews,it)
        })

        viewModel.buttonForChangingFragmentsIsEnabled.observe(viewLifecycleOwner, Observer {
            Log.i("buttonChange","FRAGMENT CUBES, value -> $it")
            (activity as MainActivity).setButtonForChangingFragments(it)
        })
        viewModel.buttonForAheadCallIsEnabled.observe(viewLifecycleOwner, Observer {
            btnAheadCall.isEnabled = it
        })
        viewModel.buttonForRollingCubesIsEnabled.observe(viewLifecycleOwner, Observer<Boolean>{
            Log.i("buttonChange","BUTTON FRO ROLLING CUBES -> $it")
            btnRollDice.isEnabled = it
        })

        viewModel.diceRolled.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).setDiceRolledInYamb(it)
        })
        viewModel.aheadCall.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).setAheadCallInYamb(it)
        })


        btnRollDice.setOnClickListener { _ ->
            viewModel.rollDice()
        }

        btnAheadCall.setOnClickListener { _ ->
            viewModel.changeAheadCall()
            btnAheadCall.setBackgroundResource(R.drawable.btn_ahead_call_pressed)
        }


    }

    private fun addListeners() { //ovako kad ih postavim, radi program
        ivCube1.setOnClickListener {
            viewModel.changeCubePressedState(CubeIndexes.CUBE_ONE.index)
        }
        ivCube2.setOnClickListener {
            viewModel.changeCubePressedState(CubeIndexes.CUBE_TWO.index)
        }
        ivCube3.setOnClickListener {
            viewModel.changeCubePressedState(CubeIndexes.CUBE_THREE.index)
        }
        ivCube4.setOnClickListener {
            viewModel.changeCubePressedState(CubeIndexes.CUBE_FOUR.index)
        }
        ivCube5.setOnClickListener {
            viewModel.changeCubePressedState(CubeIndexes.CUBE_FIVE.index)
        }
        ivCube6.setOnClickListener {
            viewModel.changeCubePressedState(CubeIndexes.CUBE_SIX.index)
        }
    }
    private fun displayCubes(imageViewsList: List<ImageView>, cubes: List<Cube>) {
       for((index,imageView) in imageViewsList.withIndex()){
            imageView.setImageResource(cubes[index].currentPicture)
       }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.setButtonsClickable()
    }

}

