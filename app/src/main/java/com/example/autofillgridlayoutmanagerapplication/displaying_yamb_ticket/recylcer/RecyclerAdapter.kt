package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IUpdateRecyclerState
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.RowIndexOfResultElements
import java.lang.IllegalArgumentException


class RecyclerAdapter(
    private val context : Context,
    private val mutableMapOfColumns: MutableMap<Int,MutableList<DataModel>>,
    var aheadCallInYamb : Boolean = false,
    private val enableButtonForChangingFragments : () -> Unit,
    private val showPopUpDialog : (Int,Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IUpdateRecyclerState {

    val COLUMN_FINAL_RESULT_INDEX = 5



    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val image: ImageView = itemView.findViewById<ImageView>(R.id.ivGridElement)

        internal fun bind(position: Int){
            image.setImageResource(mutableMapOfColumns[position%mutableMapOfColumns.size]!![position/mutableMapOfColumns.size].data as Int)    //postavlja izvor slike koji treba bit na toj određenoj poziciji u RecyclerViewu
        }

    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById<TextView>(R.id.tvGridElement)

        internal fun bind(position: Int) {
            text.text = null
            text.background = null

            Log.i("opet","OPET -> BIND FUNKCIJA")


            val currentItem = getCurrentItem(position)
            val columnIndex = getColumnAndRowIndex(position)[0]
            val rowIndex = getColumnAndRowIndex(position)[1]


            if (columnIndex == 4 && aheadCallInYamb  && !currentItem.isValueSet)
                mutableMapOfColumns[columnIndex]!![rowIndex].clickable = true          //ako je zvana najava, stavi mi najavu na klikabilno,



            if(!checkIsRowANotResult(rowIndex)){
                text.text = ((currentItem.data ?:0).toString())     //ako je trenutno red rezultat , postavi text da nije klikabilan
                getCurrentItem(position).clickable = false
                return
            }

            if(aheadCallInYamb){
                for((column,arrayDataModel) in mutableMapOfColumns){
                    for((row,datModel) in arrayDataModel.withIndex()){              //ako je najava zvana, psotavi sve ostalo na ne klikabilno
                        if(column != 4)
                            mutableMapOfColumns[column]!![row].clickable = false
                    }
                }
            }


            if (currentItem.isValueSet) {
                text.text = getCurrentItem(position).data.toString()
                text.setBackgroundResource(R.drawable.text_view_value_set)          //ako je vrijednsot postavljena, prikazi mi i promijeni pozadinu
            }
            if (currentItem.clickable && !currentItem.isValueSet) {
                text.setBackgroundResource(R.drawable.text_view_for_input)    //ako je klikabilno, promijeni pozadinu
                text.setOnClickListener {
                    showPopUpDialog(position,rowIndex)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.grid_layout_element_text_view -> TextViewHolder(LayoutInflater.from(context).inflate(
                R.layout.grid_layout_element_text_view,parent,false))
            R.layout.grid_layout_element_image -> ImageViewHolder(LayoutInflater.from(context).inflate(
                R.layout.grid_layout_element_image,parent,false))
            else -> throw IllegalArgumentException("Unknown layout type.")
        }
    }

    override fun getItemCount(): Int {

        var numberOfElements = 0
        for((key,member) in mutableMapOfColumns){
            numberOfElements += member.count()
        }
        return numberOfElements
    }

    override fun getItemViewType(position: Int): Int {
        return getCurrentItem(position).layoutId // [0][0],[1][0],[2][0],[3][0],[4][0],[5][0], [0][1] ..
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val columnIndex = getColumnAndRowIndex(position)[0]
        val rowIndex = getColumnAndRowIndex(position)[1]



        when(mutableMapOfColumns[columnIndex]!![rowIndex].layoutId ){
            R.layout.grid_layout_element_text_view -> { (holder as TextViewHolder).bind(position)
            }
            R.layout.grid_layout_element_image -> {(holder as ImageViewHolder).bind(position)
            }
            else -> throw IllegalArgumentException("View type is out of bounds")
        }

    }

    override fun updateRecyclerState(positionOfItemSelectedInRecylcer: Int, valueForInput : Int) {
        aheadCallInYamb = false

        setCurrentItemStats(positionOfItemSelectedInRecylcer,valueForInput)
        setResultRowValues(positionOfItemSelectedInRecylcer,valueForInput)
        disableItemClicks()
       notfyRecyclerOfChanges(positionOfItemSelectedInRecylcer)
        enableButtonForChangingFragments()
    }

    private fun getColumnAndRowIndex (position: Int) : MutableList<Int>{
        val columnIndex = position%mutableMapOfColumns.size
        val rowIndex = position/mutableMapOfColumns.size
        return mutableListOf(columnIndex,rowIndex)
    }
    private fun getItemPosition(numberOfColumns : Int,columnIndex : Int, rowIndex :Int) : Int{
        return numberOfColumns*rowIndex + columnIndex
    }
    private fun getCurrentItem(position: Int) : DataModel {
        return mutableMapOfColumns[getColumnAndRowIndex(position)[0]]!![getColumnAndRowIndex(position)[1]]
    }
    private fun checkIsRowANotResult(rowIndex: Int) : Boolean{
        return  rowIndex != RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index &&
                rowIndex != RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index &&
                rowIndex != RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index
    }
    private fun getResultItemPosition(currentItemPosition : Int) : Int{

        val rowIndex = getColumnAndRowIndex(currentItemPosition)[1]

        return when(true){
            rowIndex < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index -> {
                getItemPosition(mutableMapOfColumns.size,currentItemPosition%mutableMapOfColumns.size,
                    RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index)
            }
            rowIndex < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index && rowIndex > RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index-> {
                getItemPosition(mutableMapOfColumns.size,currentItemPosition%mutableMapOfColumns.size,
                    RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index)
            }
            else -> {
                getItemPosition(mutableMapOfColumns.size,currentItemPosition%mutableMapOfColumns.size,
                    RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index)
            }
        }
    }
    private fun setResultRowValues( positionOfItemSelectedInRecylcer : Int, valueForInput: Int){

        val resultColumnIndex = getColumnAndRowIndex(positionOfItemSelectedInRecylcer)[0]
        val rowIndex = getColumnAndRowIndex(positionOfItemSelectedInRecylcer)[1]
        val resultRowIndex = getColumnAndRowIndex(getResultItemPosition(positionOfItemSelectedInRecylcer))[1]

        mutableMapOfColumns[resultColumnIndex]!![resultRowIndex].data = (
                (mutableMapOfColumns[resultColumnIndex]!![resultRowIndex].data ?: 0).toString().toInt() + //povecaj rezultanti element tog stupca
                        valueForInput
                ).toString()

        mutableMapOfColumns[COLUMN_FINAL_RESULT_INDEX]!![resultRowIndex].data = (
                (mutableMapOfColumns[COLUMN_FINAL_RESULT_INDEX]!![resultRowIndex].data ?: 0).toString().toInt() + //povecaj ukupan zbroj rezultata za vrijednost dodanog elementa
                        (mutableMapOfColumns[resultColumnIndex]!![rowIndex].data ?:0).toString().toInt()
                ).toString()
    }
    private fun setCurrentItemStats(positionOfItemSelectedInRecylcer: Int, valueForInput: Int) {
        val columnIndex = getColumnAndRowIndex(positionOfItemSelectedInRecylcer)[0]
        val rowIndex = getColumnAndRowIndex(positionOfItemSelectedInRecylcer)[1]
        mutableMapOfColumns[columnIndex]!![rowIndex].data = valueForInput.toString() // Stavi vrijednost elementa na kliknutoj poziciji na vrijednost koju treba upisati
        mutableMapOfColumns[columnIndex]!![rowIndex].isValueSet = true
    }
    private fun notfyRecyclerOfChanges(positionOfItemSelectedInRecylcer: Int) {
        val resultRowIndex = getColumnAndRowIndex(positionOfItemSelectedInRecylcer)[1]
        notifyItemChanged(positionOfItemSelectedInRecylcer)                                      // Obavijesti o promjenama
        val resultItemPosition = getResultItemPosition(positionOfItemSelectedInRecylcer)
        notifyItemChanged(resultItemPosition)
        notifyItemChanged(getItemPosition(mutableMapOfColumns.size,COLUMN_FINAL_RESULT_INDEX,resultRowIndex))
    }
    private fun disableItemClicks() {
        for((column,row) in mutableMapOfColumns){
            for((index,dataModel) in row.withIndex())
                mutableMapOfColumns[column]!![index].clickable = false
        }
        notifyDataSetChanged()
    }






}