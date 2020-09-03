package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_game_finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.DisplayingPopUpWhenGameFinishedBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ISaveOrContinueListener
import com.example.autofillgridlayoutmanagerapplication.view_model_factories.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.displaying_pop_up_when_game_finished.*

class DisplayingFinishedGamePopUpDialog : BottomSheetDialogFragment() {

    private var viewModel : FinishedGamePopUpViewModel? = null
    var onSaveOrContinueClickedListener : ISaveOrContinueListener? = null
    lateinit var databindingLayout : DisplayingPopUpWhenGameFinishedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databindingLayout = DisplayingPopUpWhenGameFinishedBinding.inflate(inflater,container,false)
        return databindingLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = viewModel ?: ViewModelProvider(this,ViewModelFactory(GamesPlayedDatabase.getInstanceOfDatabase(requireContext()))).get(FinishedGamePopUpViewModel::class.java)

        viewModel!!.continueNextGame.observe(this, Observer {
            if(it == ContinueGame.CONTINUE){
                if(it.saveGame)
                    onSaveOrContinueClickedListener!!.resetItemsAndSaveGame()
                else
                    onSaveOrContinueClickedListener!!.resetItems()
            }

        })
        viewModel!!.dataForBinding.observe(this, Observer {
            databindingLayout.data = it
        })


        btnSaveGame.setOnClickListener {
            ContinueGame.CONTINUE.saveGame = true
            this.dismiss()
            viewModel!!.changeContinueGameState()
        }
        btnContinue.setOnClickListener {
            this.dismiss()
            viewModel!!.changeContinueGameState()
        }

    }
    override fun onDestroyView() {
        ContinueGame.CONTINUE.saveGame = false
        super.onDestroyView()
    }

}