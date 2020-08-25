package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.FillingYambTicketFragment
import com.example.autofillgridlayoutmanagerapplication.view_model_factory.ViewModelFactory
import com.example.autofillgridlayoutmanagerapplication.displaying_saved_games.DisplayingSavedGamesFragment
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.RollingCubesFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.changing_fragments.*


class ChangingFragmentsActivity : AppCompatActivity() {

    private val fragmentCubes = RollingCubesFragment()
    private val fragmentYamb = FillingYambTicketFragment()
    private val fragmentPastGamesList = DisplayingSavedGamesFragment()
    private lateinit var viewModel: ChangingFragmentsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.changing_fragments)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                GamesPlayedDatabase.getInstanceOfDatabase(this)
            )
        ).get(ChangingFragmentsViewModel::class.java)

        val yambFragmentStateAdapter = FragmentStateAdapter(
            supportFragmentManager,
            this.lifecycle,
            listOf(fragmentPastGamesList, fragmentCubes, fragmentYamb)
        )

        viewPager.adapter = yambFragmentStateAdapter
        viewPager.setCurrentItem(1,false)

        TabLayoutMediator(tabLayout,viewPager){tab, position ->
                tab.text = viewModel.fragemetnNames[position]
        }.attach()


    }



}


