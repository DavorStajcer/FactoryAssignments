package com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces

enum class RowIndexOfResultElements(val index : Int){
    INDEX_OF_RESULT_ROW_ELEMENT_ONE(6),INDEX_OF_RESULT_ROW_ELEMENT_TWO(9),INDEX_OF_RESULT_ROW_ELEMENT_THREE(15)
}

enum class ScreenValues(val size : Int){
    DEVICE_WIDTH(1080),COLUMN_NUMBER(6)
}

enum class CubeIndexes(val index : Int){
    CUBE_ONE(0),CUBE_TWO(1),CUBE_THREE(2),CUBE_FOUR(3),CUBE_FIVE(4),CUBE_SIX(5)
}

enum class YambConstants(val value : Int){
    COLUMN_NUMBER(6),
}

enum class PopUpState(var position : Int = 0){
    SHOW,HIDE
}

enum class FinishGameState(var totalPoints : Int){
    FINISHED(0),NOT_FINISHED(0)
}
enum class AheadCall{
    CALLED,NOT_CALLED
}

enum class SendingDataToPopUp(var diceRolled : List<Int>,var position: Int){
    ENABLED(listOf(),0),DISABLED(listOf(),0)
}
enum class Recycler{
    ENABLED,DISABLED
}

enum class ButtonForChangingFragmentsState{
    ENABLED,DISABLED
}