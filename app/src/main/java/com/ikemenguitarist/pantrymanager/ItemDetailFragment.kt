package com.ikemenguitarist.pantrymanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ikemenguitarist.pantrymanager.data.Item
import com.ikemenguitarist.pantrymanager.databinding.FragmentItemDetailBinding
import com.ikemenguitarist.pantrymanager.ui.PantryManagerApplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemDetailFragment : Fragment() {
    lateinit var item: Item

    private val viewModel:StockViewModel by activityViewModels {
        StockViewModelFactory(
            (activity?.application as PantryManagerApplication).database.itemDao()
        )
    }
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private fun bind(item:Item){
        binding.apply {
            itemName.text = item.itemName
            itemLimit.text = item.limit.toString()
            itemCount.text = item.itemQuantity.toString()
            //sellItem.isEnabled = viewModel.isStockAvailable(item)
            //sellItem.setOnClickListener { viewModel.sellItem(item) }
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    private fun editItem() {
        viewModel.updateCurrentState(getString(R.string.edit_fragment_title))
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<AddItemFragment>(R.id.detail_container)
            // If we're already open and the detail pane is visible,
            // crossfade between the fragments.
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

    /**
     * Called when fragment is destroyed.
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner){selectItem -> item=selectItem
            bind(item)}
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}