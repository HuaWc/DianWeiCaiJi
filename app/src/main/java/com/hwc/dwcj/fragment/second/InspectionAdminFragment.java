package com.hwc.dwcj.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.InspectionAdminAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.entity.second.InspectionAdmin;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InspectionAdminFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_xjzt)
    TextView tvXjzt;
    @BindView(R.id.tc_jjcd)
    TextView tcJjcd;
    @BindView(R.id.tv_cszt)
    TextView tvCszt;
    @BindView(R.id.tv_kssj)
    TextView tvKssj;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private List<InspectionAdmin> mList;
    private InspectionAdminAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection_admin, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getContext().getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
    }

    private void initAdapter(){
        mList = new ArrayList<>();
        adapter = new  InspectionAdminAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        getData();
    }

    private void getData(){
        for(int i = 0;i<10;i++){
            InspectionAdmin inspectionAdmin = new InspectionAdmin();
            inspectionAdmin.setName("外场巡查（正常）");
            mList.add(inspectionAdmin);
        }
        adapter.notifyDataSetChanged();
    }

    private void initClick(){

    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
