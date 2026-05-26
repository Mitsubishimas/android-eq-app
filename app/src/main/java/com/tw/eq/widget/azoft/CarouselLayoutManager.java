package com.tw.eq.widget.azoft;

import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarouselLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    private boolean a;
    private Integer b;
    private Integer c;
    private final int d;
    private final boolean e;
    private int f;
    private final b g;
    private PostLayoutListener h;
    private final List<OnCenterItemSelectionListener> i;
    private int j;
    private int k;

    @Nullable
    private a l;

    public interface OnCenterItemSelectionListener {
        void onCenterItemChanged(int i);
    }

    public interface PostLayoutListener {
        com.tw.eq.widget.azoft.b transformChild(@NonNull View view, float f, int i);
    }

    protected static class a implements Parcelable {
        public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator<a>() {
            @Override
            public final a createFromParcel(Parcel parcel) {
                return new a(parcel, (byte) 0);
            }
            @Override
            public final a[] newArray(int i) {
                return new a[i];
            }
        };
        private final Parcelable a;
        private int b;

        private a(@NonNull Parcel parcel) {
            this.a = parcel.readParcelable(Parcelable.class.getClassLoader());
            this.b = parcel.readInt();
        }
        a(Parcel parcel, byte b) {
            this(parcel);
        }
        protected a(@Nullable Parcelable parcelable) {
            this.a = parcelable;
        }
        protected a(@NonNull a aVar) {
            this.a = aVar.a;
            this.b = aVar.b;
        }
        @Override
        public final int describeContents() { return 0; }
        @Override
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.a, i);
            parcel.writeInt(this.b);
        }
    }

    private static class b {
        int b;
        c[] c;
        private final List<WeakReference<c>> d = new ArrayList<>();
        int a = 2;

        b() {}

        private void a() {
            c cVar;
            int length = this.c.length;
            for (int i = 0; i < length; i++) {
                if (this.c[i] == null) {
                    c[] cVarArr = this.c;
                    Iterator<WeakReference<c>> it = this.d.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            cVar = new c((byte) 0);
                            break;
                        }
                        cVar = it.next().get();
                        it.remove();
                        if (cVar != null) break;
                    }
                    cVarArr[i] = cVar;
                }
            }
        }

        final void a(int i) {
            if (this.c == null || this.c.length != i) {
                if (this.c != null) {
                    for (c cVar : this.c) {
                        this.d.add(new WeakReference<>(cVar));
                    }
                }
                this.c = new c[i];
                a();
            }
        }

        final void a(int i, int i2, float f) {
            c cVar = this.c[i];
            cVar.a = i2;
            cVar.b = f;
        }
    }

    private static class c {
        int a;
        float b;
        private c() {}
        c(byte b) { this(); }
    }

    public CarouselLayoutManager(int i) {
        this(i, false);
    }

    public CarouselLayoutManager(int i, boolean z) {
        this.g = new b();
        this.i = new ArrayList<>();
        this.j = -1;
        if (i != 0 && 1 != i) {
            throw new IllegalArgumentException("orientation should be HORIZONTAL or VERTICAL");
        }
        this.d = i;
        this.e = z;
        this.f = -1;
    }

    private float a() {
        if (b() == 0) return 0.0f;
        return (this.g.b * 1.0f) / e();
    }

    private static float a(float f, int i) {
        while (0.0f > f) f += i;
        while (Math.round(f) >= i) f -= i;
        return f;
    }

    private float a(int i) {
        float fA = a(a(), this.k);
        if (!this.e) return fA - i;
        float f = fA - i;
        float fAbs = Math.abs(f) - this.k;
        return Math.abs(f) > Math.abs(fAbs) ? Math.signum(f) * fAbs : f;
    }

    private int a(float f) {
        int iC;
        Integer num;
        double dAbs = Math.abs(f);
        double dPow = dAbs > StrictMath.pow(1.0f / ((float) this.g.a), 0.3333333432674408d) ? StrictMath.pow(dAbs / this.g.a, 0.5d) : StrictMath.pow(dAbs, 2.0d);
        if (1 == this.d) {
            iC = d();
            num = this.c;
        } else {
            iC = c();
            num = this.b;
        }
        return (int) Math.round((Math.signum(f) * ((iC - num.intValue()) / 2)) * dPow);
    }

    @CallSuper
    private int a(int i, @NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state) {
        if (this.b == null || this.c == null || getChildCount() == 0 || i == 0) return 0;
        if (this.e) {
            this.g.b += i;
            int iE = e() * this.k;
            while (this.g.b < 0) this.g.b += iE;
            while (this.g.b > iE) this.g.b -= iE;
            this.g.b -= i;
        } else {
            int iB = b();
            if (this.g.b + i < 0) i = -this.g.b;
            else if (this.g.b + i > iB) i = iB - this.g.b;
        }
        if (i != 0) {
            this.g.b += i;
            a(recycler, state);
        }
        return i;
    }

    private int a(int i, RecyclerView.State state) {
        if (i >= state.getItemCount()) i = state.getItemCount() - 1;
        return i * (1 == this.d ? this.c : this.b).intValue();
    }

    private void a(int i, int i2, int i3, int i4, @NonNull c cVar, @NonNull RecyclerView.Recycler recycler, int i5) {
        View viewForPosition = recycler.getViewForPosition(cVar.a);
        addView(viewForPosition);
        measureChildWithMargins(viewForPosition, 0, 0);
        ViewCompat.setElevation(viewForPosition, i5);
        com.tw.eq.widget.azoft.b bVarTransformChild = this.h != null ? this.h.transformChild(viewForPosition, cVar.b, this.d) : null;
        if (bVarTransformChild == null) {
            viewForPosition.layout(i, i2, i3, i4);
            return;
        }
        viewForPosition.layout(Math.round(i + bVarTransformChild.c), Math.round(i2 + bVarTransformChild.d), Math.round(i3 + bVarTransformChild.c), Math.round(i4 + bVarTransformChild.d));
        viewForPosition.setScaleX(bVarTransformChild.a);
        viewForPosition.setScaleY(bVarTransformChild.b);
    }

    private void a(RecyclerView.Recycler recycler, int i, int i2) {
        int iIntValue = (i - this.b.intValue()) / 2;
        int iIntValue2 = iIntValue + this.b.intValue();
        int iIntValue3 = (i2 - this.c.intValue()) / 2;
        int length = this.g.c.length;
        for (int i3 = 0; i3 < length; i3++) {
            c cVar = this.g.c[i3];
            int iA = iIntValue3 + a(cVar.b);
            a(iIntValue, iA, iIntValue2, iA + this.c.intValue(), cVar, recycler, i3);
        }
    }

    private void a(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state) {
        int i;
        b bVar;
        int i2;
        int i3;
        float fA = a();
        this.k = state.getItemCount();
        float fA2 = a(fA, this.k);
        int iRound = Math.round(fA2);
        if (!this.e || 1 >= this.k) {
            i = 0;
            int iMax = Math.max((iRound - this.g.a) - 1, 0);
            int iMin = Math.min(this.g.a + iRound + 1, this.k - 1);
            int i4 = (iMin - iMax) + 1;
            this.g.a(i4);
            for (int i5 = iMax; i5 <= iMin; i5++) {
                if (i5 == iRound) {
                    bVar = this.g;
                    i2 = i4 - 1;
                } else if (i5 < iRound) {
                    bVar = this.g;
                    i2 = i5 - iMax;
                } else {
                    bVar = this.g;
                    i2 = (i4 - (i5 - iRound)) - 1;
                }
                bVar.a(i2, i5, i5 - fA2);
            }
        } else {
            int iMin2 = Math.min((this.g.a * 2) + 3, this.k);
            this.g.a(iMin2);
            int i6 = iMin2 / 2;
            for (int i7 = 1; i7 <= i6; i7++) {
                float f = i7;
                this.g.a(i6 - i7, Math.round((fA2 - f) + this.k) % this.k, (iRound - fA2) - f);
            }
            int i8 = iMin2 - 1;
            for (int i9 = i8; i9 >= i6 + 1; i9--) {
                float f2 = i9;
                float f3 = iMin2;
                this.g.a(i9 - 1, Math.round((fA2 - f2) + f3) % this.k, ((iRound - fA2) + f3) - f2);
            }
            this.g.a(i8, iRound, iRound - fA2);
            i = 0;
        }
        detachAndScrapAttachedViews(recycler);
        for (RecyclerView.ViewHolder viewHolder : new ArrayList<>(recycler.getScrapList())) {
            int adapterPosition = viewHolder.getAdapterPosition();
            c[] cVarArr = this.g.c;
            int length = cVarArr.length;
            int i10 = i;
            while (true) {
                if (i10 >= length) {
                    i3 = i;
                    break;
                }
                if (cVarArr[i10].a == adapterPosition) {
                    i3 = 1;
                    break;
                }
                i10++;
            }
            if (i3 == 0) recycler.recycleView(viewHolder.itemView);
        }
        int iC = c();
        int iD = d();
        if (1 == this.d) a(recycler, iC, iD);
        else b(recycler, iC, iD);
        recycler.clear();
        final int iRound2 = Math.round(a(fA, state.getItemCount()));
        if (this.j != iRound2) {
            this.j = iRound2;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public final void run() {
                    CarouselLayoutManager.this.b(iRound2);
                }
            });
        }
    }

    private int b() { return e() * (this.k - 1); }
    private void b(int i) {
        for (OnCenterItemSelectionListener listener : this.i) listener.onCenterItemChanged(i);
    }
    private void b(RecyclerView.Recycler recycler, int i, int i2) {
        int iIntValue = (i2 - this.c.intValue()) / 2;
        int iIntValue2 = iIntValue + this.c.intValue();
        int iIntValue3 = (i - this.b.intValue()) / 2;
        int length = this.g.c.length;
        for (int i3 = 0; i3 < length; i3++) {
            c cVar = this.g.c[i3];
            int iA = iIntValue3 + a(cVar.b);
            a(iA, iIntValue, iA + this.b.intValue(), iIntValue2, cVar, recycler, i3);
        }
    }
    private int c() { return (getWidth() - getPaddingStart()) - getPaddingEnd(); }
    private int d() { return (getHeight() - getPaddingEnd()) - getPaddingStart(); }
    private int e() { return (1 == this.d ? this.c : this.b).intValue(); }
    protected final int a(@NonNull View view) { return Math.round(a(getPosition(view)) * e()); }

    @Override public boolean canScrollHorizontally() { return getChildCount() != 0 && this.d == 0; }
    @Override public boolean canScrollVertically() { return getChildCount() != 0 && 1 == this.d; }
    @Nullable @Override public PointF computeScrollVectorForPosition(int i) {
        if (getChildCount() == 0) return null;
        int i2 = (int) (-Math.signum(a(i)));
        return this.d == 0 ? new PointF(i2, 0.0f) : new PointF(0.0f, i2);
    }
    @Override public RecyclerView.LayoutParams generateDefaultLayoutParams() { return new RecyclerView.LayoutParams(-2, -2); }
    @Override public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) { super.onAdapterChanged(oldAdapter, newAdapter); removeAllViews(); }
    @Override @CallSuper public void onLayoutChildren(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state) {
        if (state.getItemCount() == 0) { removeAndRecycleAllViews(recycler); b(-1); return; }
        if (this.b == null || this.a) {
            View viewForPosition = recycler.getViewForPosition(0);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
            removeAndRecycleView(viewForPosition, recycler);
            if (this.b != null && (this.b.intValue() != decoratedMeasuredWidth || this.c.intValue() != decoratedMeasuredHeight) && -1 == this.f && this.l == null) this.f = this.j;
            this.b = decoratedMeasuredWidth;
            this.c = decoratedMeasuredHeight;
            this.a = false;
        }
        if (-1 != this.f) {
            int itemCount = state.getItemCount();
            this.f = itemCount == 0 ? -1 : Math.max(0, Math.min(itemCount - 1, this.f));
        }
        if (-1 != this.f) {
            this.g.b = a(this.f, state);
            this.f = -1;
        } else {
            if (this.l == null) {
                if (state.didStructureChange() && -1 != this.j) this.g.b = a(this.j, state);
                a(recycler, state);
            }
            this.g.b = a(this.l.b, state);
        }
        this.l = null;
        a(recycler, state);
    }
    @Override public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) { this.a = true; super.onMeasure(recycler, state, widthSpec, heightSpec); }
    @Override public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof a)) super.onRestoreInstanceState(parcelable);
        else { this.l = (a) parcelable; super.onRestoreInstanceState(this.l.a); }
    }
    @Override public Parcelable onSaveInstanceState() {
        if (this.l != null) return new a(this.l);
        a aVar = new a(super.onSaveInstanceState());
        aVar.b = this.j;
        return aVar;
    }
    @Override public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) { return 1 == this.d ? 0 : a(i, recycler, state); }
    @Override public void scrollToPosition(int i) { if (i < 0) throw new IllegalArgumentException("position can't be less then 0. position is : " + i); this.f = i; requestLayout(); }
    @Override public int scrollVerticallyBy(int i, @NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state) { return this.d == 0 ? 0 : a(i, recycler, state); }
    @Override public void smoothScrollToPosition(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override public int calculateDxToMakeVisible(View view, int i2) { return CarouselLayoutManager.this.canScrollHorizontally() ? CarouselLayoutManager.this.a(view) : 0; }
            @Override public int calculateDyToMakeVisible(View view, int i2) { return CarouselLayoutManager.this.canScrollVertically() ? CarouselLayoutManager.this.a(view) : 0; }
        };
        linearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(linearSmoothScroller);
    }
}
