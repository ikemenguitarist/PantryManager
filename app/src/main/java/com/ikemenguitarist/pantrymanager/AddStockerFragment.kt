package com.ikemenguitarist.pantrymanager

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ikemenguitarist.pantrymanager.data.Item
import com.ikemenguitarist.pantrymanager.databinding.FragmentAddItemBinding

class AddStockerFragment : Fragment() {
    private val viewModel:StockViewModel by activityViewModels()



    lateinit var item: Item

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        if(id>0){
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner){selectItem ->
                item = selectItem
                bind(item)}
        }else{binding.saveAction.setOnClickListener { addNewItem() }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
    private fun isEntryValid():Boolean{
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemLimit.text.toString(),
            binding.itemCount.text.toString()
        )
    }
    private fun addNewItem(){
        if(isEntryValid()){
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                SimpleDateFormat("yyyy/MM/dd").parse( binding.itemLimit.text.toString()),
                binding.itemCount.text.toString()
            )
        }
        val action = AddItemFragmentDirections.actionAddItemFragmentToItemFragment()
        findNavController().navigate(action)
    }
    private fun bind(item: Item){
        binding.apply {
            itemName.setText(item.itemName, TextView.BufferType.SPANNABLE)
            itemLimit.setText(item.limit.toString(), TextView.BufferType.SPANNABLE)
            itemCount.setText(item.itemQuantity.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }
    private fun updateItem(){
        if(isEntryValid()){
            viewModel.updateItem(
                this.navigationArgs.itemId,
                this.binding.itemName.text.toString(),
                SimpleDateFormat("yyyy/MM/dd").parse(this.binding.itemLimit.text.toString()),
                this.binding.itemCount.text.toString()
            )
        }
        val action = AddItemFragmentDirections.actionAddItemFragmentToItemFragment()
        findNavController().navigate(action)
    }
}