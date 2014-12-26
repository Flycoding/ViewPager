package com.flyingh.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final int[] imageResources = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g};
    private static final String[] infos = {"A", "B", "C", "D", "E", "F", "G"};
    private InfiniteViewPager viewPager;
    private List<View> imageViews;
    private TextView infoTextView;
    private LinearLayout pointsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointsLinearLayout = (LinearLayout) findViewById(R.id.points);
        infoTextView = (TextView) findViewById(R.id.info);
        viewPager = (InfiniteViewPager) findViewById(R.id.viewPager);
        initViews();
        final InfinitePagerAdapter adapter = new InfinitePagerAdapter(newAdapter());
        viewPager.setAdapter(adapter);
        infoTextView.setText(infos[0]);
        pointsLinearLayout.getChildAt(0).setSelected(true);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            private int lastPosition;

            @Override
            public void onPageSelected(int position) {
                int realCount = adapter.getRealCount();
                infoTextView.setText(infos[position % realCount]);
                pointsLinearLayout.getChildAt(lastPosition % realCount).setSelected(false);
                pointsLinearLayout.getChildAt(position % realCount).setSelected(true);
                lastPosition = position;
            }
        });
    }

    private PagerAdapter newAdapter() {
        return new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = imageViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }
        };
    }

    private void initViews() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageResources.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setImageResource(imageResources[i]);
            imageViews.add(imageView);

            ImageView pointImageView = new ImageView(this);
            pointImageView.setImageResource(R.drawable.point);
            pointImageView.setLayoutParams(params);
            params.leftMargin = 5;
            pointImageView.setSelected(false);
            pointsLinearLayout.addView(pointImageView);
        }
    }

}
