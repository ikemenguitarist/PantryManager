package com.ikemenguitarist.pantrymanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ikemenguitarist.pantrymanager.ItemFragment
import com.ikemenguitarist.pantrymanager.R
import com.ikemenguitarist.pantrymanager.StockViewModel
import com.ikemenguitarist.pantrymanager.StockViewModelFactory
import com.ikemenguitarist.pantrymanager.databinding.FragmentPantryBinding

private const val NUM_TABS = 7

class PantryFragment : Fragment() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    private var _binding: FragmentPantryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantryBinding.inflate(inflater, container, false)

        // Connect the SlidingPaneLayout to the system back button.
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabTitleArray =
            arrayOf(
                getString(R.string.item_fragment_title),

            )
        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        viewPager = binding.pager
        viewPager.adapter = viewPagerAdapter


        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

    }

    class ViewPagerAdapter(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {

        override fun getItemCount(): Int {
            return NUM_TABS
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return ItemFragment()

            }
            return ItemFragment()
        }
    }
}