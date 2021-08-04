package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.KnowledgeAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.Knowledge;
import com.hwc.dwcj.entity.second.KnowledgeClass;
import com.hwc.dwcj.entity.second.KnowledgeType;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 知识库列表页
 */
public class KnowledgeBaseListActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_sjfldy)
    TextView tvSjfldy;
    @BindView(R.id.tv_gzlx)
    TextView tvGzlx;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;
    private List<Knowledge> mList;
    private KnowledgeAdapter adapter;
    private List<KnowledgeType> typeList;

    private List<KnowledgeClass> classList;
    private List<String> stringOptions1;
    private List<String> stringOptions2;

    private int pageSize = 10;
    private int page = 1;

    private String select1;
    private String select2;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_knowledge_base_list);

    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
    }

    private void initAdapter() {
        typeList = new ArrayList<>();
        classList = new ArrayList<>();
        stringOptions1 = new ArrayList<>();
        stringOptions2 = new ArrayList<>();
        mList = new ArrayList<>();
        adapter = new KnowledgeAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                toTheActivity(LookUpKnowledgeBaseActivity.class, bundle);
            }
        });
        getData(false);
        getSelectData1();
    }

    private void getSelectData1() {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 10);
        ApiClient.requestNetGet(this, AppConfig.OpKnowledgeManagerList, "", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<KnowledgeType> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "knowledgeTypes"), KnowledgeType.class);
                if (list != null && list.size() != 0) {
                    typeList.addAll(list);
                    initSelect1();
                }
            }

            @Override
            public void onFailure(String msg) {


            }
        });
    }

    private void initSelect1() {
        stringOptions1.add("全部");
        for (KnowledgeType t : typeList) {
            stringOptions1.add(t.getDataName());
        }
    }


    private void getData(boolean more) {
        if (more) {
            page++;
        } else {
            page = 1;
            mList.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        if (!StringUtil.isEmpty(select1)) {
            params.put("knowledgeClass", select1);
        }
        if (!StringUtil.isEmpty(select2)) {
            params.put("appearance", select2);
        }
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("keywords", etSearch.getText().toString().trim());
        }
        ApiClient.requestNetGet(this, AppConfig.OpKnowledgeManagerList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<Knowledge> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), Knowledge.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                } else {
                    if (page > 1) {
                        page--;
                    }
                }
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(String msg) {
                if (page > 1) {
                    page--;
                }
                ToastUtil.toast(msg);
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();

            }
        });
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
        tvSjfldy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelect1();
            }
        });
        tvGzlx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelect2();
            }
        });
        ivSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(false);
            }
        });

    }

    private void showSelect1() {
        if (stringOptions1 == null || stringOptions1.size() == 0) {
            ToastUtil.toast("条件为空或获取失败，请稍后再试！");
            return;
        }
        PickerViewUtils.selectOptions(this, "分类", stringOptions1, null, null, new PickerViewSelectOptionsResult() {
            @Override
            public void getOptionsResult(int options1, int options2, int options3) {
                tvSjfldy.setText(stringOptions1.get(options1));
                select2 = "";
                tvGzlx.setText("");
                stringOptions2.clear();
                if (options1 == 0) {
                    select1 = "";
                    classList.clear();
                } else {
                    select1 = typeList.get(options1-1).getDataValue();
                    getSelectData2();
                }
                getData(false);
            }
        });
    }

    private void showSelect2() {
        if (StringUtil.isEmpty(tvSjfldy.getText().toString().trim())) {
            ToastUtil.toast("请先选择事件分类，再选择类型！");
            return;
        }
        if (stringOptions2 == null || stringOptions2.size() == 0) {
            ToastUtil.toast("条件为空或获取失败，请稍后再试！");
            return;
        }
        PickerViewUtils.selectOptions(this, "类型", stringOptions2, null, null, new PickerViewSelectOptionsResult() {
            @Override
            public void getOptionsResult(int options1, int options2, int options3) {
                tvGzlx.setText(stringOptions2.get(options1));
                select2 = classList.get(options1).getDataValue();
                getData(false);

            }
        });
    }


    private void getSelectData2() {
        classList.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("dataValue", select1);
        ApiClient.requestNetPost(this, AppConfig.getPtDictDatasForSelect, "加载中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<KnowledgeClass> list = FastJsonUtil.getList(json, KnowledgeClass.class);
                classList.addAll(list);
                initSelect2();

            }

            @Override
            public void onFailure(String msg) {


            }
        });
    }

    private void initSelect2() {
        stringOptions2.clear();
        if (classList != null) {
            for (KnowledgeClass c : classList) {
                stringOptions2.add(c.getDataName());
            }
        }
    }

    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.REFRESH_KNOWLEDGE_LIST:
                getData(false);
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
