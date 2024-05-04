package com.example.lostandfound.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostandfound.R;
import com.example.lostandfound.databinding.FragmentRemoveItemBinding;
import com.example.lostandfound.ui.viewModels.OnItemRemovedListener;
import com.example.lostandfound.ui.viewModels.RemoveItemViewModel;

public class RemoveItemFragment extends Fragment implements OnItemRemovedListener {

    private RemoveItemViewModel mViewModel;
    private static final String ARG_STATUS = "status";
    private static final String ARG_NAME = "name";
    private static final String ARG_DATE = "date";
    private static final String ARG_LOCATION = "location";
    NavController navController;
    public static RemoveItemFragment newInstance() {
        return new RemoveItemFragment();
    }
    private FragmentRemoveItemBinding binding;
    String name ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRemoveItemBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        mViewModel.initiate(getContext(),this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String status = bundle.getString(ARG_STATUS);
            name = bundle.getString(ARG_NAME);
            String date = bundle.getString(ARG_DATE);
            String description = bundle.getString(ARG_LOCATION);
            binding.tvFoundOrLost.setText(status);
            binding.tvName.setText(name);
            binding.tvDate.setText(date);
            binding.tvDescription.setText(description);
        }
        binding.BtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.removeItemByNameAsync(name);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RemoveItemViewModel.class);

    }

    @Override
    public void onItemRemoved() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navController.popBackStack();
            }
        });    }
}