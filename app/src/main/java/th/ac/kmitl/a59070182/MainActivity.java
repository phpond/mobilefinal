package th.ac.kmitl.a59070182;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import th.ac.kmitl.a59070182.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .addToBackStack(null).commit();
        }
    }
}
