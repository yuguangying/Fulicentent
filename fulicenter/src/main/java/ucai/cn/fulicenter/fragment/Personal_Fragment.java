package ucai.cn.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class Personal_Fragment extends Fragment {


    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_name)
    TextView tvName;

    MainActivity context;

    public Personal_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_, container, false);
        ButterKnife.bind(this, view);
        context = (MainActivity) getContext();
        initData();
        return view;
    }

    private void initData() {
        UserAvatar user = FuLiCenterApplication.getUser();
        if (user != null) {
            tvName.setText(user.getMuserNick());
            if (user.getMavatarSuffix()!=null){
                ImageLoader.downloadAvatar(ImageLoader.getAvatar(user),context,ivAvatar);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
