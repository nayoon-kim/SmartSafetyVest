package com.example.smartvest;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PopupFragment extends DialogFragment {
    public static final String TAG_POPUP_DIALOG = "dialog_event";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup, container);

        ImageView logo_close_popup;
        TextView text_popup_title;
        TextView text_popup_content;
        text_popup_title = view.findViewById(R.id.text_popup_title);
        text_popup_content = view.findViewById(R.id.text_popup_content);
        logo_close_popup = view.findViewById(R.id.logo_close_popup);

        // setCancelable(false);
        logo_close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String content = getArguments().getString("content");
            text_popup_title.setText(title);
            text_popup_content.setText(content);
        }

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // set popup size
        Window window = getDialog().getWindow();
        Display display = window.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        window.setLayout((int)(width*0.8), (int)(height*0.8));

        // prevents a page from closing when touch outside
        // set a background transparent
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
    }
}
