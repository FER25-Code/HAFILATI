package com.map.fer.t_bus.StepView;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.baoyachi.stepview.VerticalStepView;
import com.map.fer.t_bus.R;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;


public class StepView extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);

        VerticalStepView mSetpview0 =  findViewById(R.id.step_view0);
        List<String> list0 = new ArrayList<>();
        list0.add("Station1");
        list0.add("Station2");
        list0.add("Station3");
        list0.add("Station4");
        list0.add("Station5");
        list0.add("Station5");
        list0.add("Station5");
        list0.add("Station5");
        list0.add("Station5");

        mSetpview0.
        setStepsViewIndicatorComplectingPosition(list0.size()-1)//设置完成的步数
                .reverseDraw(false)//default is true
                .setStepViewTexts(list0)//总步骤
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(StepView.this, android.R.color.black))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(StepView.this, R.color.black))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(StepView.this, android.R.color.black))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(StepView.this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(StepView.this, R.drawable.busstop))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(StepView.this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(StepView.this, R.drawable.attention));
    }
}
