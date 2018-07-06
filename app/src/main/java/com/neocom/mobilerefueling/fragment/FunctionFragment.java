package com.neocom.mobilerefueling.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

//import com.huanyu.itsms.R;
//import com.huanyu.itsms.adapter.FunctionMenuAdapter;
//import com.huanyu.itsms.bean.ExpandListViewChild;
//import com.huanyu.itsms.bean.ExpandListViewGroup;
//import com.huanyu.itsms.utils.UIUtils;

import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.adapter.FunctionMenuAdapter;
import com.neocom.mobilerefueling.bean.ExpandListViewChild;
import com.neocom.mobilerefueling.bean.ExpandListViewGroup;
import com.neocom.mobilerefueling.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
//import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */

public class FunctionFragment extends BaseFragment {


    private View view;
    private ExpandableListView functionMenu;
    private List<ExpandListViewGroup> groups;
//    private LinearLayout mToolBar;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = UIUtils.inflate(R.layout.function_fragment);
        functionMenu = view.findViewById(R.id.expand_listView_function_fg);

        return view;
    }

    @Override
    public void initData() {
        groups = new ArrayList<>();
        ExpandListViewGroup processStartGroup = new ExpandListViewGroup(R.mipmap.process_start_menu_group, "流程");
        processStartGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "发起事件"));
        processStartGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "已办事件"));
        processStartGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "待办事件"));
        groups.add(processStartGroup);

        ExpandListViewGroup assetGroup = new ExpandListViewGroup(R.mipmap.assets_menu, "资产");
        assetGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "资产盘点"));
        assetGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "离线盘点"));
        assetGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "资产登记"));
        assetGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "资产查看"));
        assetGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "出入库"));

        groups.add(assetGroup);
        ExpandListViewGroup monitorGroup = new ExpandListViewGroup(R.mipmap.monitor, "巡检管理");
        monitorGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "巡检"));
        monitorGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "排班"));
        monitorGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "离线巡检"));
        groups.add(monitorGroup);

        ExpandListViewGroup knowledgeGroup = new ExpandListViewGroup(R.mipmap.knowledge_lib_menu, "知识库");
        knowledgeGroup.addChildrenItem(new ExpandListViewChild(R.mipmap.ic_launcher, "知识库查询"));
        groups.add(knowledgeGroup);
    }

    @Override
    public void initView() {
//        mToolBar = view.findViewById(R.id.fc_menu_title);
        FunctionMenuAdapter functionMenuAdapter = new FunctionMenuAdapter(UIUtils.getContext(), groups);
        functionMenu.setAdapter(functionMenuAdapter);

//        for (int i = 0; i < groups.size(); i++) {// 默认展开 功能列表的所有分组
//            functionMenu.expandGroup(i);
//        }

        functionMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                Toast.makeText(UIUtils.getContext(), i + ";" + i1, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        functionMenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                Toast.makeText(UIUtils.getContext(), "分组点击" + i, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }


//    @Override
//    protected void immersionInit() {
//        super.immersionInit();
//        ImmersionBar.with(this)
//                .titleBar(mToolBar)
//                .init();
//
//    }
}
