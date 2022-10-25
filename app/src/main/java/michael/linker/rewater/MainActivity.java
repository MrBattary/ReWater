package michael.linker.rewater;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import michael.linker.rewater.ui.navigation.INavigation;
import michael.linker.rewater.ui.navigation.Navigation;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        INavigation navigation = new Navigation(this);
    }
}