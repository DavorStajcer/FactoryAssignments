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
import com.example.autofillgridlayoutmanagerapplication.databinding.RollingCubesFragmentBinding
import com.example.autofillgridlayoutmanagerapplication.view_model_factories.ViewModelFactory
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
        initializeViewModel()
        fragmentCubesLayoutDatabinding.viewModel = viewModel
        addClickListenersOnCubeImages()
        initializeObserverForUiChanges()
        setClickListenerOnButtons()
    }



    private fun initializeViewModel(){
        viewModel = ViewModelProvider(requireActivity(),ViewModelFactory( GamesPlayedDatabase.getInstanceOfDatabase(requireContext()))).get(RollingCubesViewModel::class.java)
    }
    private fun addClickListenersOnCubeImages() {
        val imageViews = listOf<ImageView>(ivCube1,ivCube2,ivCube3,ivCube4,ivCube5,ivCube6)
        for ((index, image) in imageViews.withIndex()) {
            image.setOnClickListener {
                viewModel.changeCubePressedState(index)
            }
        }
    }
    private fun initializeObserverForUiChanges(){
        viewModel.rollingCubesUI.observe(viewLifecycleOwner, Observer {
            fragmentCubesLayoutDatabinding.data = it
            fragmentCubesLayoutDatabinding.executePendingBindings()
        })
    }
    private fun setClickListenerOnButtons(){
        btnRollDice.setOnClickListener {
            viewModel.rollCubesAndChangeUiState()
        }
        btnAheadCall.setOnClickListener {
            viewModel.changeUiAfterAheadCallButtonIsPressed()
        }
    }
    override fun onDestroyView() {
        viewModel.disposeOfObservers()
        super.onDestroyView()
    }

}


