package tip.capstone.mathuto.fragments.lesson9

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import tip.capstone.mathuto.CalculatorActivity
import tip.capstone.mathuto.FullScreenImageAdapter
import tip.capstone.mathuto.FullScreenVideoActivity
import tip.capstone.mathuto.R
import tip.capstone.mathuto.databinding.Lesson9SimulationBinding

class SimulationFragment : Fragment() {

    private lateinit var binding: Lesson9SimulationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Lesson9SimulationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.simulation.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.activity_full_screen_image)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val viewPager: ViewPager = dialog.findViewById(R.id.view_pager)
            val images = listOf(
                R.drawable.simulation_9_1, R.drawable.simulation_9_2, R.drawable.simulation_9_3,
                R.drawable.simulation_9_4, R.drawable.simulation_9_5, R.drawable.simulation_9_6, R.drawable.simulation_9_7,
                R.drawable.simulation_9_8, R.drawable.simulation_9_9, R.drawable.simulation_9_10)
            val adapter = FullScreenImageAdapter(requireContext(), images)
            viewPager.adapter = adapter

            dialog.show()
        }

        binding.btnWatchVideo.setOnClickListener{
            val videoUri = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.simulation_9)
            val intent = Intent(context, FullScreenVideoActivity::class.java)
            intent.putExtra("videoUri", videoUri.toString())
            startActivity(intent)
        }
    }
}