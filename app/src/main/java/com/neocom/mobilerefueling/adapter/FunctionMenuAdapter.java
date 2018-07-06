package com.neocom.mobilerefueling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.neocom.mobilerefueling.R;
import com.neocom.mobilerefueling.bean.ExpandListViewChild;
import com.neocom.mobilerefueling.bean.ExpandListViewGroup;

import java.util.List;



/**
 * Created by admin on 2017/7/18.
 * 功能菜单的适配器
 */

public class FunctionMenuAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {
    private Context context;
    private List<ExpandListViewGroup> groups;

    public FunctionMenuAdapter(Context context, List<ExpandListViewGroup> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildrenCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildItem(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ExpandListViewGroup group = (ExpandListViewGroup) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.function_menu_group, null);
        }
        ImageView groupIcon = convertView.findViewById(R.id.fc_menu_group_iv);
//        groupIcon.setImageResource(R.drawable.a);
        groupIcon.setImageResource(group.getGicon());
        TextView groupText = convertView.findViewById(R.id.fc_menu_group_tv);
        groupText.setText(group.getGname());

        ImageView groupExpandble = convertView.findViewById(R.id.fc_menu_group_ex_iv);

//        Toast.makeText(context, "是否展开：" + isExpanded, Toast.LENGTH_SHORT).show();

        if (isExpanded) {
            groupExpandble.setImageResource(R.mipmap.expand_open);
        } else {
            groupExpandble.setImageResource(R.mipmap.expand_close);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandListViewChild child = groups.get(groupPosition).getChildItem(childPosition);
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.function_menu_child, null);

        }

        ImageView childIcon = convertView.findViewById(R.id.fc_menu_child_iv);

        child.setIcon(R.drawable.a);

        TextView childTv = convertView.findViewById(R.id.fc_menu_child_tv);
        childTv.setText(child.getName());

        return convertView;
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return true;
    }

}
