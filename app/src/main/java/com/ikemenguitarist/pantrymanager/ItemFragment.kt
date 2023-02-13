package com.ikemenguitarist.pantrymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemListAdapter {
            val action =
                ItemFragmentDirections.actionItemFragmentToItemDetailFragment(it.id)
            this.findNavController().navigate(action)
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
            val action = ItemFragmentDirections.actionItemFragmentToAddItemFragment(
                // getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }
}