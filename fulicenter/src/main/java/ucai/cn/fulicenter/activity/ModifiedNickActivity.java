package ucai.cn.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.utils.MFGT;

public class ModifiedNickActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_modify_new_nick)
    EditText tvModifyNewNick;
    @Bind(R.id.btn_true)
    Button btntrue;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_nick);
        ButterKnife.bind(this);
        context = this;
    }

    @OnClick({R.id.back, R.id.btn_true})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                MFGT.finish(this);
                break;
            case R.id.btn_true:
                String newNick = tvModifyNewNick.getText().toString();
                MFGT.modifiedGoTOPersonalData(this,newNick);
                MFGT.finish(this);
                break;
        }
    }
}
