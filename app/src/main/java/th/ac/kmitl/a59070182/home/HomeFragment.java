package th.ac.kmitl.a59070182.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import th.ac.kmitl.a59070182.FriendFragment;
import th.ac.kmitl.a59070182.ProfileFragment;
import th.ac.kmitl.a59070182.R;
import th.ac.kmitl.a59070182.login.LoginFragment;

public class HomeFragment extends Fragment {

    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_ragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("HOME");
        preferences = this.getActivity().getSharedPreferences("USER",Context.MODE_PRIVATE);
        initMyfriend();
        initProfile();
        initSignOut();
    }

    private void initProfile(){
        Button button = getView().findViewById(R.id.profile_set_btn_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_view, new ProfileFragment())
                        .addToBackStack(null).commit();
            }
        });
    }
    private void initMyfriend(){
        Button button = getView().findViewById(R.id.my_friend_btn_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_view, new FriendFragment())
                        .addToBackStack(null).commit();
            }
        });
    }
    private void initSignOut(){

    }
}
