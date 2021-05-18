package com.example.android.camera2basic;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DetectingAreaFragment extends DialogFragment {
    int mNum;
    //CustomView customView1, customView2, customView3, customView4, customView5;
    CustomView customView1;
    int screenWidth, screenHeight;

    FrameLayout framelayout;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static DetectingAreaFragment newInstance(int num) {
        DetectingAreaFragment f = new DetectingAreaFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
        System.out.println("screen height:"+screenHeight+", screen Width:"+screenWidth);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_dialog, container, false);

        framelayout = (FrameLayout) v.findViewById(R.id.framelayout);

        return v;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {


        //위에서 커스텀뷰 클래스 객세를 생성하고 그려질 뷰 위치를 선택해준다.
        customView1 = new CustomView(getActivity().getApplicationContext(),
                new Point(50,20), new Point(250,20),
                new Point(250,120), new Point(50,120),
                screenWidth, screenHeight);

        framelayout.addView(customView1);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}