package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.ItemInRecycler
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.*


class FragmentYambViewModel : ViewModel() {

    var diceRolled = listOf<Int>()
    var onClickItemPosition = 0

    private val aheadCall_ : MutableLiveData<AheadCall> = MutableLiveData()
    val aheadCall : LiveData<AheadCall>
        get() = aheadCall_

    private val  isPopUpEnabled_ : MutableLiveData<PopUpState> = MutableLiveData()
    val isPopUpEnabled : LiveData<PopUpState>
        get()= isPopUpEnabled_

    private val  isRecyclerEnabled_ : MutableLiveData<Recycler> = MutableLiveData(Recycler.ENABLED)
    val isRecyclerEnabled : LiveData<Recycler>
        get()= isRecyclerEnabled_

    private val itemsInRecycler_ : MutableLiveData<List<ItemInRecycler>> = MutableLiveData()
    val itemsInRecycler : LiveData<List<ItemInRecycler>>
        get() = itemsInRecycler_

    private val sendDataToPopUp_ : MutableLiveData<SendingDataToPopUp> = MutableLiveData()
    val sendDataToPopUp : LiveData<SendingDataToPopUp>
        get() = sendDataToPopUp_

    private val isButtonForChangingFragmentsEnabled_ : MutableLiveData<ButtonForChangingFragmentsState> = MutableLiveData()
    val isButtonForChangingFragmentsEnabled : LiveData<ButtonForChangingFragmentsState>
        get() = isButtonForChangingFragmentsEnabled_

    private val finishedGame_ = MutableLiveData<FinishGameState>(FinishGameState.NOT_FINISHED)
    val  finishGameState : LiveData<FinishGameState>
        get() = finishedGame_

    init {
       // generateStartingItems()
        generateStartingTestItems()
    }




    fun changeItemsWhenAheadCallIsCalled(){
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getAheadCallPressedList()
    }

    fun changeItemsWhenPopUpForEnteringValuesCloses(valueOfItemPicked: Int){

        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getNewList(onClickItemPosition,valueOfItemPicked)

        if(ItemListAndStatsGenerator.isGameFinished()){
            changeIsGameFinishedState()
            changeIsRecyclerScrollingAndClickingEnabled()
        }
        freezeAllItems()
    }

    fun changeIsGameFinishedState(){
        if(finishedGame_.value == FinishGameState.FINISHED)
            finishedGame_.value = FinishGameState.NOT_FINISHED
        else{
            FinishGameState.FINISHED.totalPoints = ItemListAndStatsGenerator.getTotalPoints()
            finishedGame_.value = FinishGameState.FINISHED
        }

    }

    fun unFreezeAllItems(){
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getUnfreezedList()
    }

    fun changeIsPopUpDialogEnabledState(position: Int){
        this.onClickItemPosition = position
        PopUpState.SHOW.position = position
        isPopUpEnabled_.value = PopUpState.SHOW
        isPopUpEnabled_.value = PopUpState.HIDE
    }

    fun enableSendingDataToPup(){
        SendingDataToPopUp.ENABLED.diceRolled = diceRolled
        SendingDataToPopUp.ENABLED.position = onClickItemPosition
        sendDataToPopUp_.value = SendingDataToPopUp.ENABLED
        sendDataToPopUp_.value = SendingDataToPopUp.DISABLED
    }

    fun changeAheadCall(aheadCall: Boolean){
        if(aheadCall)
            this.aheadCall_.value = AheadCall.CALLED
        else
            this.aheadCall_.value = AheadCall.NOT_CALLED
    }

    fun changeIsRecyclerScrollingAndClickingEnabled(){
        if(isRecyclerEnabled_.value == Recycler.ENABLED)
            isRecyclerEnabled_.value = Recycler.DISABLED
        else
            isRecyclerEnabled_.value = Recycler.ENABLED
    }

    fun resetAllItems(){
         this.generateStartingItems()
    }

    fun changeButtonForChangingFragmentsState(){
        if( isButtonForChangingFragmentsEnabled_.value == ButtonForChangingFragmentsState.ENABLED )
            isButtonForChangingFragmentsEnabled_.value = ButtonForChangingFragmentsState.DISABLED
        else{
            if(finishGameState.value == FinishGameState.NOT_FINISHED)
                isButtonForChangingFragmentsEnabled_.value = ButtonForChangingFragmentsState.ENABLED
        }
    }

    private fun generateStartingItems(){
      this.itemsInRecycler_.value = ItemListAndStatsGenerator.generateStartingItems()
    }

    private fun freezeAllItems(){ //make them unclickable
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getFreezedList()
    }

    private fun generateStartingTestItems() {
        itemsInRecycler_.value = ItemListAndStatsGenerator.generateStartingTestItems()
    }

}