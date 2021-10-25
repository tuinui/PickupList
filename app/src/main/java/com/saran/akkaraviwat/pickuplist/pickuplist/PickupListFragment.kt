package com.saran.akkaraviwat.pickuplist.pickuplist

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.saran.akkaraviwat.pickuplist.R
import com.saran.akkaraviwat.pickuplist.common.presentation.SingleEventObserver
import com.saran.akkaraviwat.pickuplist.databinding.FragmentPickupListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PickupListFragment : Fragment() {

    companion object {
        fun newInstance() = PickupListFragment()
    }

    private lateinit var binding: FragmentPickupListBinding
    private val viewModel: PickupListViewModel by viewModel()
    private val pickupListRecyclerAdapter: PickupListRecyclerAdapter by lazy { PickupListRecyclerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickupListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadPickupList()
    }

    private fun initView() {
        binding.recyclerviewPickupList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pickupListRecyclerAdapter
        }

        binding.swiperefreshlayoutPickupList.setOnRefreshListener {
            viewModel.loadPickupList()
        }

        binding.toolbarPickupList.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.menuitem_pickup_my_location) {
                onMyLocationClick()
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
    }


    private fun onMyLocationClick() {
        runWithPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) {
            viewModel.loadPickupList()
        }
    }

    private fun initViewModel() {
        viewModel.showLoadingEvent.observe(viewLifecycleOwner, SingleEventObserver { show ->
            binding.swiperefreshlayoutPickupList.isRefreshing = show
        })

        viewModel.showToastEvent.observe(viewLifecycleOwner, SingleEventObserver {
            showSnackbarMessage(it)
        })

        viewModel.pickupList.observe(viewLifecycleOwner, {
            pickupListRecyclerAdapter.replaceData(it)
        })

        viewModel.loadPickupList()
    }

    private fun showSnackbarMessage(message: String?) {
        message ?: return
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
