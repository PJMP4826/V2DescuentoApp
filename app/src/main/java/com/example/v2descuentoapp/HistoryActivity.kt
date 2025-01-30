import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.v2descuentoapp.databinding.FragmentDashboardBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("calculation_history", android.content.Context.MODE_PRIVATE)
        val history = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()

        for (calculation in history) {
            val textView = TextView(requireContext()).apply {
                text = calculation
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }
            binding.historyContainer.addView(textView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
