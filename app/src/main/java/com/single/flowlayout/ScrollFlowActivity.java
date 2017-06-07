package com.single.flowlayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.library.flowlayout.FlowAdapter;
import com.library.flowlayout.FlowLayout;

import java.util.Arrays;
import java.util.Random;

public class ScrollFlowActivity extends AppCompatActivity {

    private static final String arrays[] = new String[]{"1.C",
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
            "20.MATLAB", "1.C",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_flow);
        FlowLayout re = (FlowLayout) findViewById(R.id.flow);
        re.setAdapter(new FlowAdapter<String>(this, Arrays.asList(arrays)) {
            @Override
            protected int generateLayout(int position) {
                if (position % 3 == 0) {
                    return R.layout.flow_item_heigher;
                } else {
                    return R.layout.flow_item;
                }
            }

            @Override
            protected void getView(String o, View parent) {
                TextView text = (TextView) parent.findViewById(R.id.flow_text);
                text.setText(o);
                text.setBackgroundDrawable(getBack());
            }

            private Drawable getBack() {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setCornerRadius(8);
                drawable.setColor(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                return drawable;
            }
        });
    }
}
