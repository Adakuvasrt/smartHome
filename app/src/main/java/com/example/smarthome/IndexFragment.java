package com.example.smarthome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smarthome.databinding.FragmentIndexBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexFragment} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends Fragment {

    private FragmentIndexBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentIndexBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //跳转到具体参数显示页面
        binding.temperatureCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "Temperature");
            float[] data = new float[7];
            bundle.putFloatArray("data", data);
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}