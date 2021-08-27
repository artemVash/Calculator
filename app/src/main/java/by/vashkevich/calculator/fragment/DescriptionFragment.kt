package by.vashkevich.calculator.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.vashkevich.calculator.R

class DescriptionFragment : Fragment() {

    lateinit var btnFurther: TextView
    lateinit var mainText: TextView
    lateinit var secondText: TextView
    lateinit var secondTextItem1: TextView
    lateinit var secondTextItem2: TextView
    lateinit var secondTextItem3: TextView
    lateinit var secondTextAdditionally: TextView
    lateinit var backToast: Toast
    var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        onBackPressed()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        btnFurther = view.findViewById(R.id.btn_further)
        mainText = view.findViewById(R.id.main_text_description)
        secondText = view.findViewById(R.id.second_text_description)
        secondTextItem1 = view.findViewById(R.id.second_text_description_item1)
        secondTextItem2 = view.findViewById(R.id.second_text_description_item2)
        secondTextItem3 = view.findViewById(R.id.second_text_description_item3)
        secondTextAdditionally = view.findViewById(R.id.second_text_description_additionally)

        mainText.alpha = 0f
        secondText.alpha = 0f
        secondTextItem1.alpha = 0f
        secondTextItem2.alpha = 0f
        secondTextItem3.alpha = 0f
        secondTextAdditionally.alpha = 0f
        btnFurther.alpha = 0f

        animText(mainText, 100, 500)
        animText(secondText, 120, 500)
        animText(secondTextItem1, 140, 500)
        animText(secondTextItem2, 160, 500)
        animText(secondTextItem3, 180, 500)
        animText(secondTextAdditionally, 200, 500)
        animText(btnFurther, 220, 500)

        btnFurther.setOnClickListener {
            findNavController().navigate(R.id.showCalculatorFragment2)
        }
    }

    private fun animText(view: View, delay: Long, duration: Long) {
        ViewCompat.animate(view)
            .setStartDelay(delay)
            .setDuration(duration)
            .alpha(1f)
    }

    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        backToast.cancel()
                        activity?.finish()
                    } else {
                        backToast = Toast.makeText(
                            context,
                            getString(R.string.pressed_exit),
                            Toast.LENGTH_LONG
                        )
                        backToast.show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }

            })
    }
}