package com.example.autofillgridlayoutmanagerapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.bacanjekockica.Cube
import kotlinx.android.synthetic.main.fragment_dice_roll.*


class FragmentCubes() : Fragment(R.layout.fragment_dice_roll){


    var diceRolled_listener: ICubesRolledObserver? = null
    var diceRolled = mutableListOf<Int>(0, 0, 0, 0, 0, 0)
    var aheadCallInYamb = false
    val pictures = listOf<Int>(
        R.drawable.cube1,
        R.drawable.cube2,
        R.drawable.cube3,
        R.drawable.cube4, //ovo je lsita slika za kockicce
        R.drawable.cube5,
        R.drawable.cube6
    ) //skup svih slika
    val cubes = listOf<Cube>(
        Cube(),
        Cube(), //lista objkeata za kockice
        Cube(),
        Cube(),
        Cube(),
        Cube()
    ) //skup svih objekata kockica

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var j = 0
        var i = 0 //broji mi koliko je puta stisnut gumb za vrcenje kockica
        var irator = 0 //sluzi mi za prolazak kroz polje

        val imageViews = listOf<ImageView>(ivCube1, ivCube2, ivCube3, ivCube4, ivCube5, ivCube6)

        btnNajava.isClickable = false
        btnNajava.isEnabled = false
        this.aheadCallInYamb = false
        btnVrti.setOnClickListener { it ->
            Log.i("DICE", "Uso u listener ${diceRolled.toString()}")
            i++ //broji koliko sam puta stisnuo gumb

            when (i) {
                1 -> {
                    while (irator < cubes.size) { // rolaju se kockice
                        cubes[irator].DiceRoll(pictures)
                        irator++//ovjde se kocice rolaju jednom

                    }
                    while (j < diceRolled.size) {
                        diceRolled[j] = when (cubes[j].currentPicture) {
                            R.drawable.cube1 -> 1
                            R.drawable.cube2 -> 2
                            R.drawable.cube3 -> 3
                            R.drawable.cube4 -> 4
                            R.drawable.cube5 -> 5
                            R.drawable.cube6 -> 6
                            else -> 7
                        }
                        j++
                    }
                    j = 0
                    displayCubes(imageViews, cubes)
                    Log.i("DICE", "Prvo rolanje ${diceRolled.toString()}")//vamo ih prikazujem
                    addListeners(cubes) //
                    irator = 0
                    btnNajava.isClickable = true
                    btnNajava.isEnabled = true
                }
                2 -> {
                    while (irator < cubes.size) { // rolaju se kockice
                        cubes[irator].DiceRoll(pictures)
                        irator++
                    }
                    displayCubes(imageViews, cubes)
                    while (j < diceRolled.size) {
                        diceRolled[j] = when (cubes[j].currentPicture) {
                            R.drawable.cube1 -> 1
                            R.drawable.cube2 -> 2
                            R.drawable.cube3 -> 3
                            R.drawable.cube4 -> 4
                            R.drawable.cube5 -> 5
                            R.drawable.cube6 -> 6
                            else -> 7
                        }
                        j++
                    }
                   diceRolled_listener!!.updateDiceRolled(diceRolled,aheadCallInYamb)
                    Log.i("DICE", diceRolled.toString())
                    irator = 0
                    j = 0
                    i = 0
                    btnNajava.isEnabled = false
                    btnVrti.isClickable = false
                    btnVrti.isEnabled = false
                }
            }


        } //ovo je zadatak s kockicom za jamb, uglavnom, sad cupokazan onCreate

        btnNajava.setOnClickListener { _ ->
            this.aheadCallInYamb = true
            btnNajava.isClickable = false
            btnNajava.isEnabled = false
           btnNajava.setBackgroundResource(R.drawable.btn_ahead_call_pressed)
        }


    }

    private fun addListeners(cubes: List<Cube>) { //ovako kad ih postavim, radi program
        ivCube1.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[0].pressed = !cubes[0].pressed
        }
        ivCube2.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[1].pressed = !cubes[1].pressed
        }
        ivCube3.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[2].pressed = !cubes[2].pressed
        }
        ivCube4.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[3].pressed = !cubes[3].pressed
        }
        ivCube5.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[4].pressed = !cubes[4].pressed
        }
        ivCube6.setOnClickListener {
            Log.i("MainActivity", "iv6ClickListener")
            cubes[5].pressed = !cubes[5].pressed
        }
    }

    private fun displayCubes(imageViews: List<ImageView>, cubes: List<Cube>) {
        var brojac = 0
        while (brojac < imageViews.size) {
            imageViews[brojac].setImageResource(cubes[brojac].currentPicture)
            brojac++
        }
    }
}


