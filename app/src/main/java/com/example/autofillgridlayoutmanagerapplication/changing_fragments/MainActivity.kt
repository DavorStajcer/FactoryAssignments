package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ICubeDataReciver
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IFinishedGameListener
import com.example.autofillgridlayoutmanagerapplication.pastGames.PastGamesFragment
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.FragmentCubes
import com.example.bacanjekockica.FragmentYamb
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragmet_cubes_layout.*


class MainActivity : AppCompatActivity(),
    ICubeDataReciver ,IFinishedGameListener {

    private val fragmentCubes = FragmentCubes()
    private val fragmentYamb = FragmentYamb()
    private val fragmentPastGamesList = PastGamesFragment()
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

        viewModel?.isButtonForChangingFragmentsEnabled?.observe(this, Observer {
            btnChangeFragments.isEnabled = it
        })

        viewModel?.displayFinishScreen?.observe(this, Observer {
            displayHiddenScreen(it)
        })

        viewModel?.totalPointsText?.observe(this, Observer {
            tvGameOverPointsResult.text = it
        })

        viewModel!!.isButtonForViewingGamesPlayedEnabled.observe(this, Observer {
            btnViewGamesPlayed.isEnabled = it
        })

        viewModel!!.fragmentToDisplayWithViewPastGamesButton.observe(this, Observer {
            startFragmentTransaction(it)
        })



        btnChangeFragments.setOnClickListener {
            viewModel!!.changeFragmentsWithButtonForDisplayingYamb()
        }

        btnViewGamesPlayed.setOnClickListener {
            viewModel!!.changeFragmentsWithButtonForPastGames()
            viewModel!!.changeButtonForChangingFragmentsState(false)
        }

    }

    private fun displayHiddenScreen(screenDisplayState : FinishedScreen) {

        if(FinishedScreen.DISPLAY == screenDisplayState){



            setListeners()
            ivArrowOnwards.visibility = View.VISIBLE
            ivPreviosResluts.visibility = View.VISIBLE
            tvGameOverPoints.visibility = View.VISIBLE
            tvGameOverPointsResult.visibility = View.VISIBLE
            flFragments.alpha = 0.2f
            btnChangeFragments.alpha = 0.2f
            Log.i("buttonFragments","DISPLAY HIDDEN SCREEN")
            viewModel?.changeButtonForChangingFragmentsState(false)
        }else{
            flFragments.alpha = 1f
            btnChangeFragments.alpha = 1f
            ivArrowOnwards.visibility = View.GONE
            ivPreviosResluts.visibility = View.GONE
            tvGameOverPoints.visibility = View.GONE
            tvGameOverPointsResult.visibility = View.GONE
        }
    }

    private fun setListeners() {

        ivArrowOnwards.setOnClickListener {
            fragmentYamb.changeRecyclerEnabledState()
            fragmentYamb.resetItems()
            viewModel?.changeDisplayFinishedScreenStatus()
            viewModel?.changeFragmentsWithButtonForDisplayingYamb()
        }

       ivPreviosResluts.setOnClickListener {
            fragmentYamb.saveGame()
        }

    }

    private fun startFragmentTransaction(fragments: Fragments) {

            val fragment: Fragment = when (fragments) {
                Fragments.FRAGMENT_CUBES ->fragmentCubes
                Fragments.FRAGMENT_YAMB -> fragmentYamb
                Fragments.FRAGMENT_PAST_GAMES -> fragmentPastGamesList
            }

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments, fragment)

                if(isFragmentPastGames(fragments)){
                    btnChangeFragments.text = fragments.buttonText
                    btnViewGamesPlayed.text = Fragments.FRAGMENT_PAST_GAMES.buttonText
                }
                else
                    btnViewGamesPlayed.text = fragments.buttonText

                commit()
            }

        }

    private fun isFragmentPastGames(fragments: Fragments) : Boolean{
        return fragments != Fragments.FRAGMENT_PAST_GAMES
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

    override fun changeViewPastGamesButtonState(state : Boolean) {
        viewModel!!.changeButtonForViewingGamesPlayedState(state)
    }

    override fun changeResultScreenState() {
       viewModel?.changeDisplayFinishedScreenStatus()
}

    override fun changeTotalPointsState(totalPoints: Int) {
        viewModel?.changeTotalPoints(totalPoints = totalPoints)
    }

}


