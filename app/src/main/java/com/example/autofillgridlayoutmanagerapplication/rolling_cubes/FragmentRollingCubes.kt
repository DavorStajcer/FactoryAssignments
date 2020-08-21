package com.example.autofillgridlayoutmanagerapplication.rolling_cubes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.MainActivity
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.FragmentCubesBinding
import com.example.autofillgridlayoutmanagerapplication.databinding.FragmentCubesDataForBinding
import kotlinx.android.synthetic.main.fragment_cubes.*


class FragmentRollingCubes() : Fragment(R.layout.fragment_cubes){


    private lateinit var viewModel : ViewModelRollingCubes
    private lateinit var imageViews : List<ImageView>
    lateinit var fragmentCubesLayoutDatabinding : FragmentCubesBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCubesLayoutDatabinding  = FragmentCubesBinding.inflate(inflater,container,false)
        return fragmentCubesLayoutDatabinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity() as MainActivity).get(ViewModelRollingCubes::class.java)
        viewModel.database = GamesPlayedDatabase.getInstanceOfDatabase(requireContext())
        imageViews = listOf<ImageView>(ivCube1,ivCube2,ivCube3,ivCube4,ivCube5,ivCube6)
        fragmentCubesLayoutDatabinding.dataForBindingCubes = FragmentCubesDataForBinding()

        viewModel.checkIsButtonForRollingCubesEnabled()
        viewModel.setListeners.observe(viewLifecycleOwner, Observer {
            if(it)
                addListeners()
            else
                removeListeners()
        })
        viewModel.databidingObject.observe(viewLifecycleOwner, Observer {
            fragmentCubesLayoutDatabinding.dataForBindingCubes = it
        })
        viewModel.buttonForAheadCallIsEnabled.observe(viewLifecycleOwner, Observer {
            btnAheadCall.isEnabled = it
        })
        viewModel.buttonForRollingCubesIsEnabled.observe(viewLifecycleOwner, Observer<Boolean>{
            btnRollDice.isEnabled = it
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
        for ((index, image) in imageViews.withIndex()) {
            image.setOnClickListener {
                viewModel.changeCubePressedState(index)
            }
        }
    }
    private fun removeListeners(){
        for (image in imageViews) {
            image.setOnClickListener(null)
        }
    }

    override fun onDestroyView() {
        viewModel.disposeOfObservers()
        super.onDestroyView()
    }

}


