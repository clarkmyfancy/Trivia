package com.example.android.navigation


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false)

// 3        binding.playButton.setOnClickListener { view: View ->
//// 1           Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_gameFragment)
//            // but since using android Jetpack can use...
//// 2           view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
//        }

        // 5 have to switch to an anonymous function to use the nav directions
        binding.playButton.setOnClickListener {view: View ->
            view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        //4        binding.playButton.setOnClickListener(
        //                Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        //        )






        // tell android that we will have a menu associated with this fragment
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    // when a menu is selected, this method is called
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // in order to navigate us to the about fragment, navigation has a function that is useful
        // if the navigationUI does not handle the selection, then it will be handled by onOptionsItemSelected
        return NavigationUI.onNavDestinationSelected(item!!,
                view!!.findNavController()) || super.onOptionsItemSelected(item)
                            // super.onoptionsitremselected(iytem) allows app to direclty handle the menu iutem
                                // without navigating
    }
}
