package com.single.flowlayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.library.flowlayout.FlowAdapter;
import com.library.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NormalLoadActivity extends AppCompatActivity {

    private static final String arrays[] = new String[]{
            "1.C",
            "2.Java",
            "3.Objective-C",
            "4.C++",
            "5.PHP",
            "6.C#",
            "7.(Visual) Basic",
            "8.Python",
            "9.Perl",
            "10.JavaScript",
            "11.Ruby",
            "12.Visual Basic .NET",
            "13.Transact-SQL",
            "14.Lisp",
            "15.Pascal",
            "16.Bash",
            "17.PL/SQL",
            "18.Delphi/Object Pascal",
            "19.Ada",
            "20.MATLAB",

            "1.C",
            "2.Java",
            "3.Objective-C",
            "4.C++",
            "5.PHP",
            "6.C#",
            "7.(Visual) Basic",
            "8.Python",
            "9.Perl",
            "10.JavaScript",
            "11.Ruby",
            "12.Visual Basic .NET",
            "13.Transact-SQL",
            "14.Lisp",
            "15.Pascal",
            "16.Bash",
            "17.PL/SQL",
            "18.Delphi/Object Pascal",
            "19.Ada",
            "20.MATLAB",

            "1.C",
            "2.Java",
            "3.Objective-C",
            "4.C++",
            "5.PHP",
            "6.C#",
            "7.(Visual) Basic",
            "8.Python",
            "9.Perl",
            "10.JavaScript",
            "11.Ruby",
            "12.Visual Basic .NET",
            "13.Transact-SQL",
            "14.Lisp",
            "15.Pascal",
            "16.Bash",
            "17.PL/SQL",
            "18.Delphi/Object Pascal",
            "19.Ada",
            "20.MATLAB"};

    private ProgressBar pb;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
        FlowLayout re = (FlowLayout) findViewById(R.id.flow);
        pb = (ProgressBar) findViewById(R.id.load);
        final ScrollView sv = (ScrollView) findViewById(R.id.sv);

        final List<String> list = new ArrayList<>();
        final MyFlowAdapter myFlowAdapter = new MyFlowAdapter(this, list);
        re.setAdapter(myFlowAdapter);
        //模拟网络
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> strings = Arrays.asList(arrays);
                list.addAll(strings);
                myFlowAdapter.notifyDataChanged();
                sv.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
            }
        }, 2000);
    }

    class MyFlowAdapter extends FlowAdapter<String> {

        public MyFlowAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        protected int generateLayout(int position) {
            return R.layout.flow_item;
        }

        @Override
        protected void getView(final String o, View parent) {
            TextView text = (TextView) parent.findViewById(R.id.flow_text);
            text.setText(o);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(NormalLoadActivity.this, o, Toast.LENGTH_SHORT).show();
                }
            });
            text.setBackgroundDrawable(getBack());
        }

        private Drawable getBack() {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(8);
            drawable.setColor(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
            return drawable;
        }
    }
}
