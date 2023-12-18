package com.example.smartsiren;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewDecorationActivity extends RecyclerView.ItemDecoration {

    private final int divHeight;

    public RecyclerViewDecorationActivity(int divHeight)
    {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = divHeight;
    }
}
