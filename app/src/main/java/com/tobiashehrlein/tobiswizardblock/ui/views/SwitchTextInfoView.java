package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.tobiapplications.thutils.GeneralUtils;
import com.tobiapplications.thutils.dialog.DialogBuilderUtil;
import com.tobiashehrlein.tobiswizardblock.R;

/**
 * Created by Tobias Hehrlein on 02.12.2017.
 */

public class SwitchTextInfoView extends LinearLayout {

    private TextView textView;
    private Switch switchControl;
    private LinearLayout infoLayout;
    private ImageView infoImage;
    private AlertDialog infoDialog;
    private Context context;

    public SwitchTextInfoView(Context context) {
        super(context);
        init(context);
    }

    public SwitchTextInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwitchTextInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(HORIZONTAL);
        setMinimumHeight(50);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int topMargin = GeneralUtils.pxFromDp(context, 24);
        params.setMargins(0, topMargin, 0, 0);
        setLayoutParams(params);

        textView = new TextView(context);
        textView.setTextSize(18);
        switchControl = new Switch(context);
        infoLayout = new LinearLayout(context);
        infoImage = new ImageView(context);
        infoImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_info));
        infoLayout.addView(infoImage);
        infoLayout.setGravity(Gravity.CENTER);

        textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4));
        switchControl.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        addView(textView);
        addView(switchControl);
        addView(infoLayout);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setChecked(boolean checked) {
        switchControl.setChecked(checked);
    }

    public void setInfoText(String text) {
        infoImage.setVisibility(View.VISIBLE);
        String title = context.getString(R.string.title_info_dialog);
        infoDialog = DialogBuilderUtil.createOneButtonDialog(context, title, text);
        infoImage.setOnClickListener(view -> infoDialog.show());
    }

    public void addOnSwitchCheckListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        switchControl.setOnCheckedChangeListener(checkedChangeListener);
    }

    public boolean isChecked() {
        return switchControl.isChecked();
    }
}
