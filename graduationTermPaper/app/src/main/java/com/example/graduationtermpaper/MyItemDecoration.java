package com.example.graduationtermpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint = new Paint();
    private LinearLayoutManager linearLayoutManager;
    private List<String> bigTitle;
    private List<Integer> bigTitlePosition;


    MyItemDecoration(LinearLayoutManager myLayoutManager, List<String> myBigTitle, List<Integer> myBigTitlePosition) {
        linearLayoutManager = myLayoutManager;
        bigTitle = myBigTitle;
        bigTitlePosition = myBigTitlePosition;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        int count = parent.getChildCount();     //获得当前RecyclerView数量
//        paint.setColor(Color.RED);              //设置画笔为红色
//        paint.setTextSize(20);                  //设置文字大小
//        for (int i = 0; i < count; i++) {       //遍历全部item View
//            View view = parent.getChildAt(i);
//            int top = view.getTop();            //获得这个item View的top位置
//            int bottom = view.getBottom();
//            int left = view.getLeft();
//            int right = view.getRight();
//            c.drawText("第" + i, left, top, paint);
//        }
        int first = linearLayoutManager.findFirstVisibleItemPosition();
        int last = linearLayoutManager.findLastVisibleItemPosition();
        paint.setColor(Color.parseColor("#9C27B0"));              //Color.RED设置画笔为红色
        paint.setTextSize(75);

//        if(1>=first&&1<=last){
//            int relativePosition = 1 - first;
//            View view = parent.getChildAt(relativePosition);
//
//            int top = view.getTop();
//            int bottom = view.getBottom();
//            int left = view.getLeft();
//            int right = view.getRight();
//            c.drawText("aaa", left, top, paint);
//        }

        for (int i = 0; i < bigTitlePosition.size(); i++) {
            if (bigTitlePosition.get(i) >= first && bigTitlePosition.get(i) <= last) {
                int relativePosition = bigTitlePosition.get(i) - first;
                View view = parent.getChildAt(relativePosition);
                int top = view.getTop();
                int left = view.getLeft();
                c.drawText(bigTitle.get(i), left, top, paint);
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //if(parent.getChildAdapterPosition(view)==0 || parent.getChildAdapterPosition(view)==1){
        //int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int position = parent.getChildAdapterPosition(view);
//        if(position==1 || position==5){
//            outRect.top = 100;
//        }else{
//            outRect.set(0, 0, 0, 0);
//        }
        if (position == bigTitlePosition.get(0) || position == bigTitlePosition.get(1) ||
                position == bigTitlePosition.get(2) || position == bigTitlePosition.get(3) ||
                position == bigTitlePosition.get(4) || position == bigTitlePosition.get(5)) {
            outRect.top = 75;
        } else {
            outRect.set(0, 0, 0, 0);
        }


    }
}
