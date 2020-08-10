package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ISetButtonForChangingFragments
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.FragmentCubes
import com.example.bacanjekockica.FragmentYamb
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(),
    ISetButtonForChangingFragments{

    private val fragmentCubes = FragmentCubes()
    private val fragmentYamb = FragmentYamb()
    lateinit var viewModel : MainAcitivtyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainAcitivtyViewModel::class.java)

        viewModel.fragmentToDisplay.observe(this, Observer {
            startFragmentTransaction(it)
            setButtonForChangingFragments(false)
        })

        viewModel.isButtonEnabled.observe(this, Observer {
            btnChangeFragments.isEnabled = it
        })


        btnChangeFragments.setOnClickListener {
                viewModel.changeFragments()
            }

        }

    private fun startFragmentTransaction(fragments: Fragments){

        val fragment : Fragment = when(fragments){
            Fragments.FRAGMENT_CUBES -> fragmentCubes
            Fragments.FRAGMENT_YAMB -> fragmentYamb
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragments,fragment)
            btnChangeFragments.text = fragments.buttonText
            commit()
        }

    }

    override fun setButtonForChangingFragments(value :Boolean) {
        viewModel.changeButtonForChangingFragmentsState(value)
    }

    override fun setDiceRolledInYamb(diceRolled: List<Int>) {
        this.fragmentYamb.getDiceRolled(diceRolled)
    }

    override fun setAheadCallInYamb(aheadCall: Boolean) {
        fragmentYamb.mainActivity = this
        this.fragmentYamb.getAheadCall(aheadCall)
    }
}


