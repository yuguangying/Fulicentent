package ucai.cn.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import ucai.cn.fulicenter.R;

public class MainActivity extends AppCompatActivity {
    RadioButton mnew_goods,mboutique,mcategory,mpersonal_center,mcart;
    TextView mcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
