package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb.FragmentYambTicket
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb.ViewModelFactory
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IOnGameClickedListener
import com.example.autofillgridlayoutmanagerapplication.savedGames.PastGamesFragment
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.FragmentRollingCubes
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val fragmentCubes = FragmentRollingCubes()
    private val fragmentYamb = FragmentYambTicket()
    private val fragmentPastGamesList = PastGamesFragment()
    private lateinit var viewModel: MainAcitivtyViewModel
    private val fragemetnNames = listOf<String>("PAST GAMES","ROLL DICES","YAMB TICKET")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(GamesPlayedDatabase.getInstanceOfDatabase(this))
        ).get(MainAcitivtyViewModel::class.java)

        val yambFragmentStateAdapter = YambFragmentStateAdapter(
            supportFragmentManager,
            this.lifecycle,
            listOf(fragmentPastGamesList, fragmentCubes, fragmentYamb)
        )

        viewPager.adapter = yambFragmentStateAdapter
        viewPager.setCurrentItem(1,false)

        TabLayoutMediator(tabLayout,viewPager){tab, position ->
                tab.text = fragemetnNames[position]
        }.attach()


        viewModel.disposeOfObservers()
    }



}


