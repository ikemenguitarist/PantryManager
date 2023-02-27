package com.ikemenguitarist.pantrymanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ikemenguitarist.pantrymanager.databinding.FragmentItemBinding
import com.ikemenguitarist.pantrymanager.ui.ItemListAdapter


class ItemFragment : Fragment() {
    private val viewModel: StockViewModel by activityViewModels()

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemBinding.inflate(inflater, container, false)

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

        val adapter = ItemListAdapter {
            viewModel.updateCurrentState(getString(R.string.item_detail_fragment_title))
            // Navigate to the details screen
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddItemFragment>(R.id.detail_container)
                // If we're already open and the detail pane is visible,
                // crossfade between the fragments.
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
            binding.slidingPaneLayout.openPane()
        }
        binding.itemGrid.layoutManager = LinearLayoutManager(this.context)
        binding.itemGrid.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.updateCurrentState(getString(R.string.add_fragment_title))
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddItemFragment>(R.id.detail_container)
                // If we're already open and the detail pane is visible,
                // crossfade between the fragments.
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

            }
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