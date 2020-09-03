package com.example.autofillgridlayoutmanagerapplication.databinding

import android.util.Log
import androidx.databinding.BaseObservable
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.bacanjekockica.Cube




sealed class RollingCubesUiState(
    val btnAheadCallBackgroundResource : Int,
    val isBtnAheadCallEnabled : Boolean,
    val isBtnRollingDicesEnabled : Boolean,
    val arePicuturesClickable : Boolean
) {
    var pictureOne : Int = R.drawable.cube1
    var pictureTwo : Int= R.drawable.cube1
    var pictureThree : Int= R.drawable.cube1
    var pictureFour : Int= R.drawable.cube1
    var pictureFive : Int= R.drawable.cube1
    var pictureSix : Int= R.drawable.cube1
    abstract fun getNewState(cubes: List<Cube>) : RollingCubesUiState
    internal fun changePictures(cubes : List<Cube>){
        pictureOne = cubes[0].currentPicture
        pictureTwo = cubes[1].currentPicture
        pictureThree = cubes[2].currentPicture
        pictureFour = cubes[3].currentPicture
        pictureFive = cubes[4].currentPicture
        pictureSix = cubes[5].currentPicture
    }
}
object BeforeRollingCubesUiState : RollingCubesUiState(
    btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_not_pressed,
    isBtnAheadCallEnabled = false,
    isBtnRollingDicesEnabled = true,
    arePicuturesClickable = false
)  {
    override fun getNewState(cubes: List<Cube>): RollingCubesUiState {
        AfterFirstRollUiState.changePictures(cubes)
        return AfterFirstRollUiState
    }
}
object AfterFirstRollUiState : RollingCubesUiState(
    btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_not_pressed,
    isBtnAheadCallEnabled = true,
    isBtnRollingDicesEnabled = true,
    arePicuturesClickable = true
){
    override fun getNewState(cubes: List<Cube>): RollingCubesUiState {
        AfterSecondRollUiState.changePictures(cubes)
        return AfterSecondRollUiState
    }
}
object AfterAheadCallPressedUiState : RollingCubesUiState(
    btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_pressed,
    isBtnAheadCallEnabled = false,
    isBtnRollingDicesEnabled = true,
    arePicuturesClickable = true
){
    override fun getNewState(cubes: List<Cube>): RollingCubesUiState {
        AfterSecondRollUiStateAheadCallPressed.changePictures(cubes)
        return AfterSecondRollUiStateAheadCallPressed
    }
}
object AfterSecondRollUiState : RollingCubesUiState(
    btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_not_pressed,
    isBtnAheadCallEnabled = false,
    isBtnRollingDicesEnabled = false,
    arePicuturesClickable = false
) {
    override fun getNewState(cubes: List<Cube>): RollingCubesUiState {
        BeforeRollingCubesUiState.changePictures(cubes)
        return BeforeRollingCubesUiState
    }
}
object AfterSecondRollUiStateAheadCallPressed : RollingCubesUiState(
    btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_pressed,
    isBtnAheadCallEnabled = false,
    isBtnRollingDicesEnabled = false,
    arePicuturesClickable = false
) {
    override fun getNewState(cubes: List<Cube>): RollingCubesUiState {
        BeforeRollingCubesUiState.changePictures(cubes)
        return BeforeRollingCubesUiState
    }
}


/*
class RollingCubesBindingData(
    var pictureOne : Int = R.drawable.cube1,
    var pictureTwo : Int= R.drawable.cube1,
    var pictureThree : Int= R.drawable.cube1,
    var pictureFour : Int= R.drawable.cube1,
    var pictureFive : Int= R.drawable.cube1,
    var pictureSix : Int= R.drawable.cube1,
    var buttonAheadCallText : String = "Ahead Call",
    var btnRollDicesText : String = "Roll",
    var areImagesClickable : Boolean = false,
    var isBtnAheadCallEnabled : Boolean = false,
    var btnAheadCallBackgroundResource : Int = R.drawable.btn_ahead_call_not_pressed,
    var isAheadCallCalled : Boolean = false,
    var isBtnRollingDicesEnabled : Boolean = true

) : BaseObservable()

    companion object{
        private val beforRollingBindingUiInstance : RollingCubesBindingData by lazy { RollingCubesBindingData() }
        private val afterFirstRollBindingUiInstance : RollingCubesBindingData by lazy {
             RollingCubesBindingData(
            isBtnAheadCallEnabled = true,
            isBtnRollingDicesEnabled = true,
            isAheadCallCalled = false,
            areImagesClickable = true
        )}
        private val afterAheadCallPressedBindingUiInstance : RollingCubesBindingData by lazy {
            RollingCubesBindingData(
                isAheadCallCalled = true ,
                isBtnAheadCallEnabled = false,
                btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_pressed
            )
        }
        private val afterSecondRollBindingUiInstance : RollingCubesBindingData by lazy {
            RollingCubesBindingData(
                isBtnAheadCallEnabled = false,
                isBtnRollingDicesEnabled = false,
                areImagesClickable = false
            )
        }

        fun getBindingObjectBeforeRolling() = beforRollingBindingUiInstance
        fun getBindingObjectAfterFirstRoll(cubes : List<Cube>) : RollingCubesBindingData{
            changePictures(afterFirstRollBindingUiInstance,cubes)
            return afterFirstRollBindingUiInstance
        }
        fun getBindingObjectAfterAheadCallButtonIsPressed(cubes : List<Cube>) : RollingCubesBindingData{
            changePictures(afterAheadCallPressedBindingUiInstance,cubes)
            return afterAheadCallPressedBindingUiInstance
        }
        fun getBindingObjectAfterSecondRoll(cubes : List<Cube>, isAheadCallCalled: Boolean) : RollingCubesBindingData{
            changePictures(afterSecondRollBindingUiInstance,cubes)
            afterSecondRollBindingUiInstance.isAheadCallCalled = isAheadCallCalled
            changeButtonAheadCallBackgroudnAfterSecondRoll(isAheadCallCalled)
            return afterSecondRollBindingUiInstance
        }

        private fun changePictures(bidnigUiInstance : RollingCubesBindingData,cubes : List<Cube>){
            bidnigUiInstance.pictureOne = cubes[0].currentPicture
            bidnigUiInstance.pictureTwo = cubes[1].currentPicture
            bidnigUiInstance.pictureThree = cubes[2].currentPicture
            bidnigUiInstance.pictureFour = cubes[3].currentPicture
            bidnigUiInstance.pictureFive = cubes[4].currentPicture
            bidnigUiInstance.pictureSix = cubes[5].currentPicture
        }
        private fun changeButtonAheadCallBackgroudnAfterSecondRoll(isAheadCallCalled: Boolean){
            if(isAheadCallCalled)
                afterFirstRollBindingUiInstance.btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_pressed
            else
                afterFirstRollBindingUiInstance.btnAheadCallBackgroundResource = R.drawable.btn_ahead_call_not_pressed
        }
    }
*/








