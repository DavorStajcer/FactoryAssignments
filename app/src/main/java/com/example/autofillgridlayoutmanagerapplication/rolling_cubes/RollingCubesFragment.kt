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
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.RollingCubesBindingData
import com.example.autofillgridlayoutmanagerapplication.databinding.RollingCubesFragmentBinding
import com.example.autofillgridlayoutmanagerapplication.view_model_factory.ViewModelFactory
import kotlinx.android.synthetic.main.rolling_cubes_fragment.*


class RollingCubesFragment() : Fragment(R.layout.rolling_cubes_fragment){

    private lateinit var viewModel : RollingCubesViewModel
    private lateinit var fragmentCubesLayoutDatabinding : RollingCubesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCubesLayoutDatabinding  = RollingCubesFragmentBinding.inflate(inflater,container,false)
        return fragmentCubesLayoutDatabinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(),ViewModelFactory( GamesPlayedDatabase.getInstanceOfDatabase(requireContext()))).get(RollingCubesViewModel::class.java)
        val imageViews = listOf<ImageView>(ivCube1,ivCube2,ivCube3,ivCube4,ivCube5,ivCube6)
        fragmentCubesLayoutDatabinding.data = RollingCubesBindingData()


        viewModel.setListeners.observe(viewLifecycleOwner, Observer {
            if(it)
                addListeners(imageViews)
            else
                removeListeners(imageViews)
        })
        viewModel.databidingObject.observe(viewLifecycleOwner, Observer {
            fragmentCubesLayoutDatabinding.data = it
        })
        viewModel.buttonForAheadCallIsEnabled.observe(viewLifecycleOwner, Observer {
            btnAheadCall.isEnabled = it
        })
        viewModel.buttonForRollingCubesIsEnabled.observe(viewLifecycleOwner, Observer<Boolean>{
            btnRollDice.isEnabled = it
        })

        btnRollDice.setOnClickListener { _ ->
            viewModel.rollEachCube()
            viewModel.bindPictureData()
            viewModel.changeButtonsAndListeners()
            viewModel.saveRolledStatsIfRollingIsOver()
        }
        btnAheadCall.setOnClickListener { _ ->
            viewModel.changeAheadCall()
            btnAheadCall.setBackgroundResource(R.drawable.btn_ahead_call_pressed)
        }
    }

    private fun addListeners(imageViews: List<ImageView>) { //ovako kad ih postavim, radi program
        for ((index, image) in imageViews.withIndex()) {
            image.setOnClickListener {
                viewModel.changeCubePressedState(index)
            }
        }
    }
    private fun removeListeners(imageViews: List<ImageView>){
        for (image in imageViews) {
            image.setOnClickListener(null)
        }
    }

    override fun onDestroyView() {
        viewModel.disposeOfObservers()
        super.onDestroyView()
    }

}


