package michael.linker.rewater.ui.advanced.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import michael.linker.rewater.databinding.FragmentHomeBinding;
import michael.linker.rewater.ui.advanced.home.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        final TextView textView = mBinding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}