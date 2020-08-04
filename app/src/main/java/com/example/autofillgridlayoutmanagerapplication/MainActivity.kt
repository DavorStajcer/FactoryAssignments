package com.example.autofillgridlayoutmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.bacanjekockica.FragmentYamb
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentCubes = FragmentCubes()
        val fragmentYamb = FragmentYamb()

        btnZamijeniFragment.isEnabled = false



        var brojac: Int = 0

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmenti, fragmentCubes,"Kockice")
            addToBackStack("Kockice")
            commit()

            fragmentCubes.viewModel.diceRolled.observe(this@MainActivity, Observer {
             //   fragmentYamb.viewModel.fragmentManager = supportFragmentManager
             //   fragmentYamb.viewModel.diceRolled.value = it
                btnZamijeniFragment.isEnabled = true
            })
            fragmentCubes.viewModel.aheadCall.observe(this@MainActivity, Observer {
              //  fragmentYamb.viewModel.aheadCall = it
            })
        }

        btnZamijeniFragment.setOnClickListener {

                brojac++
                if (brojac == 1) {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragmenti, fragmentYamb,"Listic")
                        addToBackStack("Listic")
                        btnZamijeniFragment.text = "KOCKICE"
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

}


