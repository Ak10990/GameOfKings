package com.android.thoughtworks.flows;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.thoughtworks.R;

/**
 * Filter Dialog with 3 option for selecting all or one of the battle types
 */
public class FilterDialog {

    private final FilterDialogCallback callback;
    private AppCompatDialog mDialog;
    private RadioGroup filterChooserGroup;

    public FilterDialog(Context context, FilterDialogCallback callback) {
        this.callback = callback;
        mDialog = new AppCompatDialog(context);
        mDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_filter);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        filterChooserGroup = (RadioGroup) mDialog.findViewById(R.id.filter_chooser_group);

        filterChooserGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onSelected(checkedId);
            }
        });
    }

    public void showDialog() {
        if (mDialog == null) {
            return;
        }
        if (!mDialog.isShowing())
            mDialog.show();
    }

    public void dismissDialog() {
        onDismissDialog();
        if (mDialog == null || !mDialog.isShowing())
            return;
        mDialog.dismiss();
    }

    private void onDismissDialog() {
        filterChooserGroup.clearCheck();
    }

    private void onSelected(int selectedFilterId) {
        if (selectedFilterId > 0) {
            RadioButton radioPickUpButton = (RadioButton) mDialog.findViewById(selectedFilterId);
            if (radioPickUpButton != null) {
                int selectedPickup = Integer.parseInt(radioPickUpButton.getTag().toString());
                callback.onFilterButtonClick(selectedPickup);
            }
            dismissDialog();
        }
    }

}