package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {
    lateinit var binding: FragmentGameWonBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentGameWonBinding.inflate(inflater, container, false)
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        setHasOptionsMenu(true)
        Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextMatchButton.setOnClickListener {
            findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareIntent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
       /* return ShareCompat.IntentBuilder.from(activity!!)
            .setText("text/plain")
            .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
            .intent*/
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        return shareIntent
    }

    private fun shareIntent() {
        startActivity(getShareIntent())
    }
}
