package com.kylediaz.fbu.dipole_news.fragments.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;

import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.activities.login.LoginActivity;
import com.kylediaz.fbu.dipole_news.databinding.FragmentUserBinding;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;
import com.parse.ParseUser;

public class UserFragment extends Fragment {

    private UserViewModel userViewModel;
    private FragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            binding.tvWelcomeMessage.setText("Welcome, " + currentUser.getUsername());
            binding.tvSignOut.setVisibility(View.VISIBLE);
        } else {
            binding.tvWelcomeMessage.setOnClickListener(arg0 -> {
                Intent i = new Intent(UserFragment.this.getContext(), LoginActivity.class);
                startActivity(i);
            });
            binding.tvSignOut.setVisibility(View.GONE);
        }

        binding.tvSignOut.setOnClickListener(arg0 -> {
            DipoleNewsClient.getInstance().logOut();
        });

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new UserFragment.SettingsFragment())
                    .commit();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}