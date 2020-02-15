package com.dargoz.extendedbottomnavigationview.menu;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dargoz.extendedbottomnavigationview.R;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BaseMenuLayout implements MenuLayout {
    private MenuOnClickListener menuOnClickListener;
    private Context context;

    public BaseMenuLayout(Context context) {
        this.context = context;
    }

    @Override
    public LinearLayout constructMenu(final Menu menu, int itemIndex) {
        TextView titleText = new TextView(context);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 0, (float) 0.3);
        titleParams.setMargins(
                0, context.getResources().getDimensionPixelSize(R.dimen.baseline_2dp),
                0, 0);
        titleText.setLayoutParams(titleParams);
        titleText.setText(menu.getItem(itemIndex).getTitle());
        titleText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        titleText.setTextSize(TypedValue.DENSITY_DEFAULT, 26);
        titleText.setTypeface(Typeface.DEFAULT_BOLD);
        titleText.setSelected(false);


        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, WRAP_CONTENT);
        imageView.setLayoutParams(imageParams);
        imageView.setImageDrawable(menu.getItem(itemIndex).getIcon());
        imageView.setSelected(false);

        return buildMenuItemLayout(menu, context, titleText, imageView, itemIndex);
    }

    @Override
    public void setOnMenuClickListener(MenuOnClickListener listener) {
        menuOnClickListener = listener;
    }

    @NonNull
    private LinearLayout buildMenuItemLayout(final Menu menu, Context context,
                                             TextView titleText, ImageView imageView, final int itemIndex) {
        LinearLayout menuItemContainer = new LinearLayout(context);
        menuItemContainer.setOrientation(LinearLayout.VERTICAL);
        menuItemContainer.addView(imageView);
        menuItemContainer.addView(titleText);
        menuItemContainer.setId(View.generateViewId());
        menuItemContainer.setGravity(Gravity.CENTER);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout
                .LayoutParams(0, WRAP_CONTENT);
        menuItemContainer.setLayoutParams(layoutParams);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.baseline_15dp);
        menuItemContainer.setPadding(0, padding, 0, padding);
        menuItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOnClickListener != null) {
                    menuOnClickListener.onMenuItemClick(menu, itemIndex);
                }
            }
        });
        return menuItemContainer;
    }
}
