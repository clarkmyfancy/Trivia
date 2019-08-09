/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        // use the generated action class to get the arguments from the arguemtn bundle
//        var args = GameWonFragmentArgs.fromBundle(arguments!!) !!!!!@!@!@!@ Just moved this over to getShareIntent
        // add toast to ensure the arguments got passed successfully
//        Toast.makeText(context,
//                "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}",
//                Toast.LENGTH_LONG).show()

        setHasOptionsMenu(true) // AA1 (for creating a menu thing in action bar)
        return binding.root
    }

    // AA2
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        // added below
        inflater?.inflate(R.menu.winner_menu, menu)
        //(CCC) check if the activity resolves to
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // the package manager knows about every acticvity trhat is registed in the android manifest actross
            // every application, so it can be used to see if our implicit intent will resolve to something


            // hide the menu item if it doesn't resolve
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    // creates share intent
    private fun getShareIntent() : Intent {
        // moved from onCreateView
        var args = GameWonFragmentArgs.fromBundle(arguments!!)
//bb1        // tells android we want activities that are registyered with intent-filter that are ok to send an action
//bb1        val shareIntent = Intent(Intent.ACTION_SEND)
//bb1        // android uses this mime type of the acticvity to locate which activities this can share to
//bb1        shareIntent.setType("text/plain")
//bb1            .putExtra(Intent.EXTRA_TEXT, // intent extra is a key value data struct
//bb1                getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
//bb1        return shareIntent
        // instead of the above bb's, will use shareCompat to improve readability
        // followeing is like bb2
        return ShareCompat.IntentBuilder.from(activity)
                .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
                .setType("text/plain") // there is a problem! if there is no app available that can share
                // the app will crash. to fix this (CCC) hide the share item button if there is no activity that handles the share
                .intent

    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // this time, we are not navigating within out app witha  navigation controller but inste4ad
            // to another app using as custom intent
        when (item!!.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
