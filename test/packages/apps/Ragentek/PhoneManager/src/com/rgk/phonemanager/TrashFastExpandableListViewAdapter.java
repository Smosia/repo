package com.rgk.phonemanager;

import java.util.List;

import com.rgk.phonemanager.util.StrUtils;
import com.rgk.phonemanager.util.TrashItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TrashFastExpandableListViewAdapter extends
		BaseExpandableListAdapter {

	private List<TrashItem> mGroupTypes = null;
	private List<List<TrashItem>> mGroupMembers = null;
	private LayoutInflater mLayoutInflater;
	private OnChildItemClickListener mOnChildItemClickListener;
	private OnGroupItemCheckedClickListener mGroupItemCheckedClickListener;

	public TrashFastExpandableListViewAdapter(Context context,
			List<TrashItem> mGroupTypes, List<List<TrashItem>> mGroupMembers) {
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.mGroupTypes = mGroupTypes;
		this.mGroupMembers = mGroupMembers;
	}

	@Override
	public int getGroupCount() {
		if (mGroupTypes != null)
			return mGroupTypes.size();
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupTypes.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (mGroupMembers.size() == 0
				|| mGroupMembers.get(groupPosition) == null)
			return 0;

		return mGroupMembers.get(groupPosition).size();

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mGroupMembers.get(groupPosition).get(childPosition);
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
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder groupViewHolder;
		if (convertView == null) {
			groupViewHolder = new GroupViewHolder();

			convertView = mLayoutInflater.inflate(R.layout.trash_group_item,
					null);
			groupViewHolder.mGroupInfo = (TextView) convertView
					.findViewById(R.id.trash_group_item_info);
			groupViewHolder.mGroupSize = (TextView) convertView
					.findViewById(R.id.trash_group_item_size);
			groupViewHolder.mGroupIsChecked = (ImageView) convertView
					.findViewById(R.id.trash_group_item_choose_icon);
			groupViewHolder.mGroupProgressBar = (ProgressBar) convertView
					.findViewById(R.id.trash_pb);

			convertView.setTag(groupViewHolder);
		} else {
			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}

		if (mGroupTypes.get(groupPosition).isIsShowProgressBar()) {
			groupViewHolder.mGroupProgressBar.setVisibility(View.VISIBLE);
			groupViewHolder.mGroupIsChecked.setVisibility(View.INVISIBLE);
		} else {
			groupViewHolder.mGroupProgressBar.setVisibility(View.INVISIBLE);
			groupViewHolder.mGroupIsChecked.setVisibility(View.VISIBLE);

			if (mGroupTypes.get(groupPosition).isIsChecked()) {
				groupViewHolder.mGroupIsChecked
						.setImageResource(R.drawable.checked);
			} else {
				groupViewHolder.mGroupIsChecked
						.setImageResource(R.drawable.unchecked);
			}
		}

		groupViewHolder.mGroupIsChecked
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						mGroupItemCheckedClickListener
								.onGroupItemCheckedClickd(groupPosition);
					}
				});

		groupViewHolder.mGroupInfo.setText(mGroupTypes.get(groupPosition)
				.getmGroupInfo());
		groupViewHolder.mGroupSize.setText(StrUtils.convertStorage(mGroupTypes
				.get(groupPosition).getmGroupSize()));
		return convertView;
	}

	private final class GroupViewHolder {
		TextView mGroupInfo;
		TextView mGroupSize;
		ImageView mGroupIsChecked;
		ProgressBar mGroupProgressBar;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ChildViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.trash_child_item,
					null);
			viewHolder.mChildItemLayout = (RelativeLayout) convertView
					.findViewById(R.id.paylist_item);
			viewHolder.imageView_1 = (ImageView) convertView
					.findViewById(R.id.imageView_1);
			viewHolder.mChildInfo = (TextView) convertView
					.findViewById(R.id.trash_item_info);
			viewHolder.mChildSize = (TextView) convertView
					.findViewById(R.id.trash_item_size);
			viewHolder.mChildIsChecked = (ImageView) convertView
					.findViewById(R.id.trash_item_choose_icon);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) convertView.getTag();
		}

		viewHolder.imageView_1.setImageDrawable(mGroupMembers
				.get(groupPosition).get(childPosition).getmGroupIcon());
		viewHolder.mChildInfo.setText(mGroupMembers.get(groupPosition)
				.get(childPosition).getmGroupInfo());
		viewHolder.mChildSize.setText(StrUtils.convertStorage(mGroupMembers
				.get(groupPosition).get(childPosition).getmGroupSize()));
		if (mGroupMembers.get(groupPosition).get(childPosition).isIsChecked()) {
			viewHolder.mChildIsChecked.setImageResource(R.drawable.checked);
		} else {
			viewHolder.mChildIsChecked.setImageResource(R.drawable.unchecked);
		}

		viewHolder.mChildItemLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mOnChildItemClickListener.onChildItemClicked(groupPosition,
						childPosition);
			}
		});
		return convertView;
	}

	private final class ChildViewHolder {
		RelativeLayout mChildItemLayout;
		ImageView imageView_1;
		TextView mChildInfo;
		TextView mChildSize;
		ImageView mChildIsChecked;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public interface OnChildItemClickListener {
		public void onChildItemClicked(int groupPosition, int childPosition);
	}

	public void setOnChildItemClickListener(
			OnChildItemClickListener onChildItemClickListener) {
		mOnChildItemClickListener = onChildItemClickListener;
	}

	public interface OnGroupItemCheckedClickListener {
		public void onGroupItemCheckedClickd(int groupPosition);
	}

	public void setOnGroupItemCheckedClickListener(
			OnGroupItemCheckedClickListener onGroupItemCheckedClickListener) {
		mGroupItemCheckedClickListener = onGroupItemCheckedClickListener;
	}
}
