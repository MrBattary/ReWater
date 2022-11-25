package michael.linker.rewater.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import michael.linker.rewater.databinding.ActivitySignBinding;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignBinding binding = ActivitySignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}