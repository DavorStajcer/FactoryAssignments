package com.example.autofillgridlayoutmanagerapplication


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.*


class PopUpWhenClickedDialog() : BottomSheetDialogFragment(){

    lateinit var viewModel: PopUpWhenClickedDialogViewModel
    var adapter : RecyclerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog_layout,container,false)
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val positions = bundle?.getIntegerArrayList("positions")
        val positionOfItemClicked = positions!![0]

        val rowOfItemClicked = positions[1]

        val dices = bundle.getIntegerArrayList("diceRolled") as ArrayList<Int>
        Log.i("tag","$dices")

        val factory = PopUpViewModelFactory(dices,positionOfItemClicked,rowOfItemClicked)
        viewModel = ViewModelProvider(this,factory).get(PopUpWhenClickedDialogViewModel::class.java)


        setCubePicturesSource()

        tvDialog.text = viewModel.getTextForDisplay()



        btnDialogYes.setOnClickListener {
            adapter?.updateRecyclerState(viewModel.positionOfItemClickedInRecycler,viewModel.getValueForInput())
            this.dismiss()
        }
        btnDialogNo.setOnClickListener {
            this.dismiss()
        }

    }

    private fun setCubePicturesSource() {
        val diceRolled = this.viewModel.diceRolled
        ivSheetDialogDiceRolledOne.setImageResource(getCubePictureSoruce(diceRolled[0]))
        ivSheetDialogDiceRolledTwo.setImageResource(getCubePictureSoruce(diceRolled[1]))
        ivSheetDialogDiceRolledThree.setImageResource(getCubePictureSoruce(diceRolled[2]))
        ivSheetDialogDiceRolledFour.setImageResource(getCubePictureSoruce(diceRolled[3]))
        ivSheetDialogDiceRolledFive.setImageResource(getCubePictureSoruce(diceRolled[4]))
        ivSheetDialogDiceRolledSix.setImageResource(getCubePictureSoruce(diceRolled[5]))

    }
    private fun getCubePictureSoruce(numberRolled: Int) : Int {
       return when(numberRolled){
            1 -> R.drawable.cube1
            2 -> R.drawable.cube2
            3 -> R.drawable.cube3
            4 -> R.drawable.cube4
            5 -> R.drawable.cube5
            6 -> R.drawable.cube6
           else -> Log.i("tag","POP UP BOTTROM SHEET -> getCubePictureSource -> ne odgovara ni jedna slika")
       }
    }
    


}
