package com.example.autofillgridlayoutmanagerapplication


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.*


class BottomSheetDialog() : BottomSheetDialogFragment(), ISetDiceRolledOnBottomSheetObserver {


    var setButtonClickableState : ISetButtonClickableState? = null
    var recyclerAdapterStateListener : ISetKeepItemRecyclerState? = null
    var keepChange : Boolean = false                                                    //za lsitenere (observere)
    var diceRolled = mutableListOf<Int>()

    var rowIndex = 0                      //zbog racunanja koji se broj unosu (red odredjuje jer se unesava 2 para, skala, poker..)
    var positionOfItemChanged = 0        //da znam tocno na kojoj pozici mi je item koji se promijenio, tako mogu reci na koji item se zove notifyItemChanged() u recycleru
    var valueForInput = 0               //vrijednost koja se upisuje u textView (ako user stisne da)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog_layout,container,false)
    }

    val MAX_INDEX = 7
    val MIN_INDEX = 8
    val TWO_PAIRS_INDEX = 10
    val FULL = 11
    val STRAIGHT = 12
    val POKER = 13
    val YAMB = 14




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            setCubePicturesSource()


             if(rowIndex < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index){
                 for(member in diceRolled)
                     if(member == rowIndex + 1)
                         valueForInput += rowIndex + 1
                 tvDialog.text = "Zelite li upisati $valueForInput na poziciji $positionOfItemChanged ?"
             }
            when(rowIndex){
                MAX_INDEX ->{
                    val array_of_max_or_min = diceRolled
                    val temp = 0
                    for((index,member) in diceRolled.withIndex()){
                        for((index_two,number) in diceRolled.withIndex()){
                            if(member > number){
                                val temp = array_of_max_or_min[index]
                                array_of_max_or_min[index] = array_of_max_or_min[index_two]
                                array_of_max_or_min[index_two] = temp
                            }
                        }
                    }
                    Log.i("polje","$array_of_max_or_min")
                    for((index,member) in array_of_max_or_min.withIndex()){
                        if(index != array_of_max_or_min.size - 1)
                            valueForInput += member
                    }
                    tvDialog.text = "Zelite li upisati $valueForInput na poziciji $positionOfItemChanged ?"
                }
                MIN_INDEX -> {
                    val array_of_max_or_min = diceRolled
                    val temp = 0
                    for((index,member) in diceRolled.withIndex()){
                        for((index_two,number) in diceRolled.withIndex()){
                            if(member < number){
                                val temp = array_of_max_or_min[index]
                                array_of_max_or_min[index] = array_of_max_or_min[index_two]
                                array_of_max_or_min[index_two] = temp
                            }
                        }
                    }
                    Log.i("polje","$array_of_max_or_min")
                    for((index,member) in array_of_max_or_min.withIndex()){
                        if(index != array_of_max_or_min.size - 1)
                            valueForInput += member
                    }
                    tvDialog.text = "Zelite li upisati $valueForInput na poziciji $positionOfItemChanged ?"

                }
                TWO_PAIRS_INDEX ->{

                }
                STRAIGHT -> {

                }
                FULL ->{

                }
                 POKER-> {

                }
                YAMB ->{

                }

            }

        btnDialogYes.setOnClickListener {
            Log.i("BOTTOM", "KLIKNUO NA DA")
            this.keepChange = true

            dismiss()
            recyclerAdapterStateListener!!.updateRecyclerKeepItemState(keepChange,positionOfItemChanged,valueForInput)
            setButtonClickableState!!.updateIsButtonClickable(keepChange)
            valueForInput = 0
        }
        btnDialogNo.setOnClickListener {
            Log.i("BOTTOM", "KLIKNUO NA NE")
            this.keepChange = false

            dismiss()
            recyclerAdapterStateListener!!.updateRecyclerKeepItemState(keepChange,positionOfItemChanged,valueForInput)
            setButtonClickableState!!.updateIsButtonClickable(keepChange)
            valueForInput = 0
        }

    }

    private fun setCubePicturesSource() {
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
           else -> R.drawable.cube1
       }
    }

    override fun updateDiceRolledBottomSheet(diceRolled: MutableList<Int>) {
            this.diceRolled = diceRolled
        }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setButtonClickableState = context as ISetButtonClickableState         //triggera se funkicja update u Main Acitivityu
    }


}
