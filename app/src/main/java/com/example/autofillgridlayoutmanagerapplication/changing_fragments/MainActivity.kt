package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ICubeDataReciver
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IFinishedGameListener
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.FragmentCubes
import com.example.bacanjekockica.FragmentYamb
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(),
    ICubeDataReciver ,IFinishedGameListener {

    private val fragmentCubes = FragmentCubes()
    private val fragmentYamb = FragmentYamb()
    private var viewModel: MainAcitivtyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentYamb.onValueInsertedListener = this
        fragmentYamb.onGameFinishedListener = this

        viewModel = viewModel ?: ViewModelProvider(this).get(MainAcitivtyViewModel::class.java)

        viewModel?.fragmentToDisplay?.observe(this, Observer {
            startFragmentTransaction(it)
            setButtonForChangingFragments(false)
        })

        viewModel?.isButtonEnabled?.observe(this, Observer {
            Log.i("buttonFragments","observer -> ${it}")
            btnChangeFragments.isEnabled = it
        })

        viewModel?.displayFinishScreen?.observe(this, Observer {
            displayHiddenScreen(it)
        })

        viewModel?.totalPointsText?.observe(this, Observer {
            tvGameOverPointsResult.text = it
        })


        btnChangeFragments.setOnClickListener {
            viewModel?.changeFragments()
        }


    }

    private fun displayHiddenScreen(screenDisplayState : FinishedScreen) {
        if(FinishedScreen.DISPLAY == screenDisplayState){
            setListener()
            arrowOnwards.visibility = View.VISIBLE
            previousResults.visibility = View.VISIBLE
            tvGameOverPoints.visibility = View.VISIBLE
            tvGameOverPointsResult.visibility = View.VISIBLE
            flFragments.alpha = 0.2f
            btnChangeFragments.alpha = 0.2f
            Log.i("buttonFragments","DISPLAY HIDDEN SCREEN")
            viewModel?.changeButtonForChangingFragmentsState(false)
        }else{
            flFragments.alpha = 1f
            btnChangeFragments.alpha = 1f
            arrowOnwards.visibility = View.GONE
            previousResults.visibility = View.GONE
            tvGameOverPoints.visibility = View.GONE
            tvGameOverPointsResult.visibility = View.GONE
        }
    }

    private fun setListener() {
        arrowOnwards.setOnClickListener {
            fragmentYamb.changeRecyclerEnabledState()
            fragmentYamb.resetItems()
            viewModel?.changeDisplayFinishedScreenStatus()
            viewModel?.changeFragments()
        }
    }

    private fun startFragmentTransaction(fragments: Fragments) {

            val fragment: Fragment = when (fragments) {
                Fragments.FRAGMENT_CUBES -> fragmentCubes
                Fragments.FRAGMENT_YAMB -> fragmentYamb
            }

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments, fragment)
                btnChangeFragments.text = fragments.buttonText
                commit()
            }

        }

    override fun setButtonForChangingFragments(value: Boolean) {
            viewModel?.changeButtonForChangingFragmentsState(value)
        }

    override fun setDiceRolledInYamb(diceRolled: List<Int>) {
            this.fragmentYamb.getDiceRolled(diceRolled)
        }

    override fun setAheadCallInYamb(aheadCall: Boolean) {
            this.fragmentYamb.getAheadCall(aheadCall)
        }

    override fun changeResultScreenState() {
       viewModel?.changeDisplayFinishedScreenStatus()
}

    override fun changeTotalPointsState(totalPoints: Int) {
        viewModel?.changeTotalPoints(totalPoints = totalPoints)
    }

}


