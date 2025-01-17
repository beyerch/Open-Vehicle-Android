package com.openvehicles.OVMS.ui.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.openvehicles.OVMS.R;

/**
 * ProgressOverlay: create and manage a progress_layer view
 */
public class ProgressOverlay implements View.OnClickListener {
	private static final String TAG = "ProgressOverlay";

	private LinearLayout mProgressLayer;
	private TextView mProgressLabel;
	private ProgressBar mProgressBarDeterminate;
	private ProgressBar mProgressBarIndeterminate;
	private Button mProgressCancel;

	public interface OnCancelListener {
		public void onProgressCancel();
	}
	private OnCancelListener mListener;


	public ProgressOverlay(LayoutInflater inflater, ViewGroup rootView) {

		mProgressLayer = (LinearLayout) inflater.inflate(R.layout.progress_layer, rootView, false);
		mProgressLabel = (TextView) mProgressLayer.findViewById(R.id.progress_label);
		mProgressBarDeterminate = (ProgressBar) mProgressLayer.findViewById(R.id.progress_bar_determinate);
		mProgressBarIndeterminate = (ProgressBar) mProgressLayer.findViewById(R.id.progress_bar_indeterminate);
		mProgressCancel = (Button) mProgressLayer.findViewById(R.id.progress_cancel);
		mProgressCancel.setOnClickListener(this);

		hide();

		rootView.addView(mProgressLayer);
	}

	// set label from resource:
	public void setLabel(int resId) {
		mProgressLabel.setText(resId);
	}

	// set label from string:
	public void setLabel(String text) {
		mProgressLabel.setText(text);
	}

	// show indeterminate progress spinner icon:
	public void show() {
		mProgressBarDeterminate.setVisibility(View.GONE);
		mProgressBarIndeterminate.setVisibility(View.VISIBLE);
		mProgressCancel.setVisibility((mListener != null) ? View.VISIBLE : View.GONE);
		mProgressLayer.bringToFront();
		mProgressLayer.setVisibility(View.VISIBLE);
	}

	// show determinate progress bar:
	//   (closes if pos == maxPos)
	public void step(int pos, int maxPos) {
		if (maxPos > 0 && pos == maxPos) {
			hide();
		}
		else {
			mProgressBarDeterminate.setMax(maxPos);
			mProgressBarDeterminate.setProgress(pos);
			mProgressBarDeterminate.setVisibility(View.VISIBLE);
			mProgressBarIndeterminate.setVisibility(View.GONE);
			mProgressCancel.setVisibility((mListener != null) ? View.VISIBLE : View.GONE);
			mProgressLayer.bringToFront();
			mProgressLayer.setVisibility(View.VISIBLE);
		}
	}

	// hide:
	public void hide() {
		mProgressBarDeterminate.setVisibility(View.GONE);
		mProgressBarIndeterminate.setVisibility(View.GONE);
		mProgressCancel.setVisibility(View.GONE);
		mProgressLayer.setVisibility(View.GONE);
	}

	public boolean isVisible() {
		return (mProgressLayer.getVisibility() == View.VISIBLE);
	}


	public void setOnCancelListener(OnCancelListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.progress_cancel && mListener != null) {
			mListener.onProgressCancel();
		}
	}

}
