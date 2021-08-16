package by.vashkevich.calculator.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.vashkevich.calculator.R
import kotlinx.coroutines.*

class IntroFragment : Fragment(){

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageLogo = view.findViewById<ImageView>(R.id.logo)

        val extraDescriptionFragment = FragmentNavigatorExtras(
            Pair(imageLogo,"logo")
        )

        ioScope.launch {
            delay(3000)
            withContext(Dispatchers.Main){
                findNavController().navigate(R.id.showDescriptionFragment,null,null,extraDescriptionFragment)
            }
        }
    }
}