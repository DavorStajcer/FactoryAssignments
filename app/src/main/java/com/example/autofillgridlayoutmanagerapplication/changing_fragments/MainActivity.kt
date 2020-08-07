package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        viewModel.changeFragmentToCubes.observe(this, Observer {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments, fragmentCubes,"Cubes")
                btnChangeFragments.text = "YAMB"
                commit()
            }
            setButtonForChangingFragments(false)
        })

        viewModel.changeFragmentToYamb.observe(this, Observer {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments, fragmentYamb,"Yamb")
                btnChangeFragments.text = "ROLL CUBES"
                commit()
            }
            setButtonForChangingFragments(false)
        })

        viewModel.isButtonEnabled.observe(this, Observer {
            btnChangeFragments.isEnabled = it
        })


        btnChangeFragments.setOnClickListener {
                viewModel.changeFragments()
            }

        }

    override fun setButtonForChangingFragments(value :Boolean) {
        viewModel.changeButtonForChangingFragmentsState(value)
    }
}


