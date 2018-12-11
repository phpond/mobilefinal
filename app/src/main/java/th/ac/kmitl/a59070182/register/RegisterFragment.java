package th.ac.kmitl.a59070182.register;

import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Toast;

import th.ac.kmitl.a59070182.R;
import th.ac.kmitl.a59070182.Users;
import th.ac.kmitl.a59070182.login.LoginFragment;

public class RegisterFragment extends Fragment {

    private SQLiteDatabase myDB;
    private ContentValues row;
    private Users user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Register");
        super.onActivityCreated(savedInstanceState);
        initRegister();
    }

    private void initRegister(){
        Button _regisBtn = getView().findViewById(R.id.register_btn_register);
        Log.d("REGISTER", "btn : "+_regisBtn);
        _regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _userId = ((EditText)(getView().findViewById(R.id.user_id_register))).getText().toString();
                String _name = ((EditText)(getView().findViewById(R.id.name_register))).getText().toString();
                String _age = ((EditText)(getView().findViewById(R.id.age_register))).getText().toString();
                String _password = ((EditText)(getView().findViewById(R.id.password_register))).getText().toString();
                try {
                    int age = 0;
                    try {
                        if(!_age.isEmpty()){
                            age = Integer.parseInt(_age);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("REGISTER", "ERROR : " +e.getMessage());
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(_userId.isEmpty() || _name.isEmpty() || _age.isEmpty() || _password.isEmpty()) {
                        Toast.makeText(getActivity(), "Please fill out this form", Toast.LENGTH_SHORT).show();
                        Log.d("REGISTER", "user id & password empty");
                    } else if ( _userId.length() < 6 || _userId.length() > 12) {
                        Toast.makeText(getActivity(), "User Id has length 6-12 char", Toast.LENGTH_SHORT).show();
                        Log.d("REGISTER", "user id < 6 || > 12 input : "+_userId.length());
                    } else if (_name.indexOf(" ") == -1 || _name.split(" ").length > 2 || _name.charAt(_name.length()-1) == ' '){
                        /*name space 1 only*/
                        Toast.makeText(getActivity(), "name a-z space a-z only ", Toast.LENGTH_SHORT).show();
                        Log.d("REGISTER", "not matches");
                    }else if (age < 10 || age > 80) {
                        Toast.makeText(getActivity(), "Age has 10-8 ", Toast.LENGTH_SHORT).show();
                        Log.d("REGISTER", "Age <10 || > 80");
                    } else if (_password.length() <= 6) {
                        Toast.makeText(getActivity(), "Password more than 6 char", Toast.LENGTH_SHORT).show();
                        Log.d("REGISTER", "Password <= 6");
                    }else{
                        Log.d("REGISTER", "success... + "+_name.indexOf(" ")+" split"+_name.split(" ").length
                        +_name.lastIndexOf(" "));
                        user = new Users(_userId, _name, age, _password);
                        addToSQL();
                        goToNextpage();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("REGISTER", "ERROR : " +e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToSQL(){
        //open
        myDB = getActivity().openOrCreateDatabase("my.db",Context.MODE_PRIVATE, null);
        //create
        try{
            myDB.execSQL("CREATE TABLE IF NOT EXISTS user(_userId VARCHAR(12) PRIMARY KEY," +
                    " _name VARCHAR(30), " +
                    "_age INTEGER, " +
                    "_password VARCHAR(20))");
            Log.d("REGISTER", "Create db Success");

            //create obj
            row = new ContentValues();
            user.setContentValues();
            row = user.getContentValues();
            Log.d("REGISTER", "Create obj Success : "+ user.getUserId());

            //insert
            myDB.insert("user", null, row);
            Log.d("REGISTER", "Insert on db Success");

        }catch (Exception e){
            e.printStackTrace();
            Log.d("REGISTER", "error : "+e.toString());
        }
    }

    private void goToNextpage(){
        getFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        Log.d("REGISTER", "Go tot login");
    }
}
