package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb.Fragment_YambTicket
import com.example.autofillgridlayoutmanagerapplication.savedGames.PastGamesFragment
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.FragmentRollingCubes
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private val fragmentCubes = FragmentRollingCubes()
    private val fragmentYamb = Fragment_YambTicket()
    private val fragmentPastGamesList = PastGamesFragment()
    private var viewModel: MainAcitivtyViewModel? = null
    private val fragemetnNames = listOf<String>("PAST GAMES","ROLL DICES","YAMB TICKET")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = viewModel ?: ViewModelProvider(this).get(MainAcitivtyViewModel::class.java)
        viewModel!!.context = this
        viewModel!!.initializeDataAboutRolledCubes()

        val yambFragmentStateAdapter = YambFragmentStateAdapter(supportFragmentManager,this.lifecycle,
            listOf(fragmentPastGamesList,fragmentCubes,fragmentYamb))
        viewPager.adapter = yambFragmentStateAdapter

        TabLayoutMediator(tabLayout,viewPager){tab, position ->
                tab.text = fragemetnNames[position]
        }.attach()



    }



}


