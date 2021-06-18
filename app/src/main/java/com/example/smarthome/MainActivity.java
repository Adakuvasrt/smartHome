package com.example.smarthome;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.databinding.ActivityMainBinding;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        NavController navController = Navigation.findNavController(this, R.id.fragment);
    }
}