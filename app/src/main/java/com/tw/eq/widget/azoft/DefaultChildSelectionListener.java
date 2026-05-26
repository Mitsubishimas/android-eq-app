package com.tw.eq.widget.azoft;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public final class DefaultChildSelectionListener extends a {
    public interface OnCenterItemClickListener {
        void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View view);
    }
}
