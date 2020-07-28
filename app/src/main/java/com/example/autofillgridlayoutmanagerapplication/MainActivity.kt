package com.example.autofillgridlayoutmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bacanjekockica.FragmentYamb
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ISetButtonClickableState {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentOne = FragmentCubes()
        val fragmentListic = FragmentYamb()
        fragmentOne.diceRolled_listener = fragmentListic
        fragmentListic.fragmentManagerListic = supportFragmentManager
        var brojac: Int = 0

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmenti, fragmentOne,"Kockice")
            addToBackStack("Kockice")
            commit()
        }
        btnZamijeniFragment.setOnClickListener {

            if (!fragmentOne.diceRolled.contains(0)) {


                brojac++
                if (brojac == 1) {
                    supportFragmentManager.beginTransaction().apply {
                        fragmentListic.diceRolled = fragmentOne.diceRolled
                        replace(R.id.flFragmenti, fragmentListic,"Listic")
                        addToBackStack("Listic")
                        btnZamijeniFragment.text = "KOCKICE"
                        commit()
                        btnZamijeniFragment.isEnabled = false
                    }
                } else {
                    supportFragmentManager.beginTransaction().apply {
                        supportFragmentManager.popBackStack()
                        fragmentOne.diceRolled = mutableListOf(0,0,0,0,0,0)
                        btnZamijeniFragment.text = "LISTIC"
                        replace(R.id.flFragmenti, fragmentOne,"Kockice")
                        brojac = 0

                        commit()
                    }
                }
            }

        }


    }

    override fun updateIsButtonClickable(keepItem: Boolean) {
        if(keepItem)
            btnZamijeniFragment.isEnabled = true
    }


}


