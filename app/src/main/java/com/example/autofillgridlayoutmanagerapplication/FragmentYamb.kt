package com.example.bacanjekockica

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.*
import kotlinx.android.synthetic.main.fragment_yamb_ticket.*


class FragmentYamb() : Fragment(){

    lateinit var viewModel: FragmentYambViewModel
    private val popUpWhenClicked = PopUpWhenClickedDialog()
    lateinit var adapter : RecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_yamb_ticket,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = arguments

        viewModel = ViewModelProvider(this).get(FragmentYambViewModel::class.java)

        @Suppress("UNCHECKED_CAST")
        viewModel.mutableMapOfElements =  bundle!!.getSerializable("daca") as HashMap<Int, MutableList<DataModel>>

        viewModel.diceRolled = bundle.getIntegerArrayList("diceRolled") as ArrayList<Int>

        Log.i("opet","OPET -> FRAGMENT YAMB")
        adapter = RecyclerAdapter(context!!.applicationContext,viewModel.mutableMapOfElements,bundle.getBoolean("aheadCall")){
            (activity as MainActivity).enableButtonForChangingFragments()
        }

        val layoutManager = AutoFillGridLayoutManager(context!!.applicationContext,ScreenValues.DEVICE_WIDTH.size/ScreenValues.COLUMN_NUMBER.size)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true
        )

        popUpWhenClicked.adapter = this.adapter

        adapter.itemClicked.observe(this, Observer {
            val bundlePopUp = Bundle()
            bundlePopUp.putIntegerArrayList("positions", it)
            bundlePopUp.putIntegerArrayList("diceRolled",viewModel.diceRolled)
            popUpWhenClicked.arguments = bundlePopUp
            viewModel.positionOfLastItemClicked = it[0]
            this.popUpWhenClicked.show(activity!!.supportFragmentManager,"PopUp")
        })

        }

    override fun onDetach() {
        super.onDetach()
        viewModel.updateLastItemClicked()
        (activity as MainActivity).getMutableMapOfElements(viewModel.mutableMapOfElements)
    }
}





