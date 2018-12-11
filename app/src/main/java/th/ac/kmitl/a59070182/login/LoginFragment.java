package th.ac.kmitl.a59070182.login;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import th.ac.kmitl.a59070182.R;
import th.ac.kmitl.a59070182.Users;
import th.ac.kmitl.a59070182.home.HomeFragment;
import th.ac.kmitl.a59070182.register.RegisterFragment;

public class LoginFragment extends Fragment {

    private SQLiteDatabase myDB;
    private ContentValues row;
    private Users user;

    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences = this.getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        Log.d("LOGIN", "Login success with : "+preferences.getString("_userId", "not found!"));
        initLogin();
        initRegister();
    }

    private void initLogin(){
        Button _loginBtn = getView().findViewById(R.id.login_btn_login);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _userId = ((EditText)(getView().findViewById(R.id.user_id_login))).getText().toString();
                String _password = ((EditText)(getView().findViewById(R.id.password_login))).getText().toString();
                try {
                    if(preferences.getString("_userId", _userId) != null && preferences.getString("_userId", _userId) != ""){
                        goToNextpage();
                        Log.d("LOGIN", "Login success with : "+preferences.getString("_userId", _userId));
                    }
                    if(_userId.isEmpty() || _password.isEmpty()) {
                        Toast.makeText(getActivity(), "Please fill out this form", Toast.LENGTH_SHORT).show();
                        Log.d("LOGIN", "user id & password empty");
                    }else{
                        getDataFromSql(_userId);
                        if(user != null){
                            if(_password.equals(user.getPassword())){
                                preferences.edit().putString("_userId", user.getUserId()).apply();
                                preferences.edit().putString("_name", user.getName()).apply();
                                preferences.edit().commit();
                                Log.d("LOGIN", "Login success with : "+preferences.getString("_userId", _userId));
                                goToNextpage();
                            }else{
                                Toast.makeText(getActivity(), "Invalid user or password", Toast.LENGTH_SHORT).show();
                                Log.d("LOGIN", "Invalid user or password");
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("LOGIN", "ERROR : " +e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDataFromSql(String _userId){
        //open db
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        String sql = "SELECT * FROM user WHERE _userId == '"+_userId+"'";
        //query
        Cursor myCursor = myDB.rawQuery(sql, null);
        while(myCursor.moveToNext()){
            String _userId2 = myCursor.getString(0);
            String _name = myCursor.getString(1);
            int _age = myCursor.getInt(2);
            String _password = myCursor.getString(3);
            user = new Users(_userId2, _name, _age, _password);
            Log.d("LOGIN", "Get data Success");
        }
        if(user == null){
            Toast.makeText(getActivity(), "Not found user", Toast.LENGTH_SHORT).show();
            Log.d("LOGIN", "not found");
        }
        myCursor.close();
    }

    private void initRegister(){
        try {
            TextView _registBtn = getView().findViewById(R.id.register_btn_login);
            _registBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).addToBackStack(null).commit();
                    Log.d("LOGIN", "Go tot register");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.d("LOGIN", "ERROR : " +e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void goToNextpage(){
        getFragmentManager().beginTransaction().replace(R.id.main_view, new HomeFragment()).addToBackStack(null).commit();
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        Log.d("REGISTER", "Go tot login");
    }
}
