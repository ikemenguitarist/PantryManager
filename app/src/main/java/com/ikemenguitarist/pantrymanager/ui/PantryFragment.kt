package com.ikemenguitarist.pantrymanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.ikemenguitarist.pantrymanager.R
import com.ikemenguitarist.pantrymanager.StockViewModel
import com.ikemenguitarist.pantrymanager.databinding.FragmentItemBinding
import com.ikemenguitarist.pantrymanager.databinding.FragmentPantryBinding
import com.ikemenguitarist.pantrymanager.ui.ItemListAdapter
import com.ikemenguitarist.pantrymanager.ui.home.HomeFragmentDirections


class ItemFragment : Fragment() {
    private val viewModel: StockViewModel by activityViewModels()

    private var _binding: FragmentPantryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantryBinding.inflate(inflater, container, false)

        val slidingPaneLayout = binding.slidingPaneLayout

        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            ItemListOnBackPressedCallback(slidingPaneLayout)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StockListAdapter {
            val action = HomeFragmentDirections.actionNavigationPantryToItemFragment()
                // getString(R.string.add_fragment_title)
            this.findNavController().navigate(action)
        }
        binding.stockGrid.layoutManager = LinearLayoutManager(this.context)
        binding.stockGrid.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allStockers.observe(this.viewLifecycleOwner) { stockers ->
            stockers.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.updateCurrentState(getString(R.string.item_detail_fragment_title))
            // Navigate to the details screen
            binding.slidingPaneLayout.openPane()
        }
    }
}
class ItemListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }
}