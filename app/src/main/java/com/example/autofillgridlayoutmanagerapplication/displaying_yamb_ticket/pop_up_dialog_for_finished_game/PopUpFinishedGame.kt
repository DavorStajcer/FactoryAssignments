package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_finished_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.databinding.PopUpFinishedGameBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ISaveOrContinueListener
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ISendTotalPoints
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.pop_up_finished_game.*

class PopUpFinishedGame : BottomSheetDialogFragment() {

    private var viewModel : FinishedGamePopUpViewModel? = null
    var onSaveOrContinueClickedListener : ISaveOrContinueListener? = null
    var onViewModelInitializedListener : ISendTotalPoints? = null
    lateinit var databindingLayout : PopUpFinishedGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databindingLayout = PopUpFinishedGameBinding.inflate(inflater,container,false)
        return databindingLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = viewModel ?: ViewModelProvider(this).get(FinishedGamePopUpViewModel::class.java)
        onViewModelInitializedListener!!.sendTotalPoints()

        viewModel!!.continueNextGame.observe(this, Observer {
            if(it == ContinueGame.CONTINUE){
                if(it.saveGame)
                    onSaveOrContinueClickedListener!!.saveGame()
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

    fun getTotalPoints(totalPoints : Int){
        viewModel!!.chnageDataForBinding(totalPoints)
    }

    override fun onDestroyView() {
        ContinueGame.CONTINUE.saveGame = false
        super.onDestroyView()
    }

}