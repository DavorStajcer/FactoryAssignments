package com.example.autofillgridlayoutmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.bacanjekockica.FragmentYamb
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity(), IGetDataForPassingToFragmentYamb{

    private val fragmentCubes = FragmentCubes()
    private val fragmentYamb = FragmentYamb()
    val bundle = Bundle()
    lateinit var viewModel : MainAcitivtyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnZamijeniFragment.isEnabled = false

        viewModel = ViewModelProvider(this).get(MainAcitivtyViewModel::class.java)



        var brojac: Int = 0

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmenti, fragmentCubes,"Kockice")
            addToBackStack("Kockice")
            commit()


        }

        btnZamijeniFragment.setOnClickListener {

                brojac++
                if (brojac == 1) {
                    supportFragmentManager.beginTransaction().apply {
                        Log.i("ahead","fragment Transaction ->${viewModel.aheadCall}")
                        bundle.putBoolean("aheadCall",viewModel.aheadCall)
                        bundle.putSerializable("daca",viewModel.getMutableMapOfElements() as Serializable)
                        this@MainActivity.fragmentYamb.arguments = bundle
                        replace(R.id.flFragmenti, fragmentYamb,"Listic")
                        addToBackStack("Listic")
                        btnZamijeniFragment.text = "KOCKICE"
                        this@MainActivity.viewModel.aheadCall = false
                        commit()
                        btnZamijeniFragment.isEnabled = false
                    }
                } else {
                    supportFragmentManager.beginTransaction().apply {
                        supportFragmentManager.popBackStack()
                        btnZamijeniFragment.text = "LISTIC"
                        replace(R.id.flFragmenti, fragmentCubes,"Kockice")
                        brojac = 0
                        commit()
                        btnZamijeniFragment.isEnabled = false
                    }
                }
            }

        }

    override fun getDices(diceRolled : ArrayList<Int>) {
        bundle.putIntegerArrayList("diceRolled",diceRolled)
    }

    override fun enableButtonForChangingFragments() {
        Log.i("tag","button enabled")
        btnZamijeniFragment.isEnabled = true
    }

    override fun getAheadCall(aheadCall: Boolean) {
        this.viewModel.aheadCall = aheadCall
    }

    override fun getMutableMapOfElements(mutablemapOfElements: MutableMap<Int, MutableList<DataModel>>) {
        viewModel.setMutableMapOfElements(mutablemapOfElements)
    }


}


