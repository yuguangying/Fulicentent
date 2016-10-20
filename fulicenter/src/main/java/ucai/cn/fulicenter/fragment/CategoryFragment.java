package ucai.cn.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.adapter.CategoryAdapter;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;
import ucai.cn.fulicenter.net.GoodsDao;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {

    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    CategoryAdapter mcategoryAdapter;
    LinearLayoutManager glm;
    int Grouplist;
    int index =0;
    @Bind(R.id.expandable)
    ExpandableListView mexpandable;

    public CategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initData() {
        GoodsDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<CategoryGroupBean> categoryGroup = ConvertUtils.array2List(result);
                    mGroupList.addAll(categoryGroup);
                    for (;index<mGroupList.size();index++) {
                        GoodsDao.downloadCategoryChild(mContext, mGroupList.get(index).getId(), new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
                            @Override
                            public void onSuccess(CategoryChildBean[] result) {
                                if (result != null && result.length > 0) {
                                    Grouplist++;
                                    ArrayList<CategoryChildBean> categoryGroupBeen = ConvertUtils.array2List(result);
                                    mChildList.add(categoryGroupBeen);
                                }
                                if (Grouplist==mGroupList.size()){
                                    mcategoryAdapter.initData(mGroupList,mChildList);
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {
        mContext = (MainActivity) getContext();
        mChildList = new ArrayList<>();
        mGroupList = new ArrayList<>();
        mexpandable.setGroupIndicator(null );
        mcategoryAdapter = new CategoryAdapter(mContext, mGroupList, mChildList);
        mexpandable.setAdapter(mcategoryAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
