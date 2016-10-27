package wmlove.bistu.mine.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import wmlove.bistu.R;

public class QueryActivity extends AppCompatActivity {

    private RelativeLayout rl_grade_query;
    private RelativeLayout rl_cte_query;
    private RelativeLayout rl_makeup_query;
    private RelativeLayout rl_exam_query;
    private RelativeLayout rl_empty_room_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        rl_grade_query = (RelativeLayout) findViewById(R.id.rl_grade_query);
        rl_grade_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //webview
            }
        });

        rl_cte_query = (RelativeLayout) findViewById(R.id.rl_cte_query);
        rl_cte_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //webview
            }
        });

        rl_makeup_query = (RelativeLayout) findViewById(R.id.rl_makeup_query);
        rl_makeup_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //webview
            }
        });

        rl_exam_query = (RelativeLayout) findViewById(R.id.rl_exam_query);
        rl_exam_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //webview
            }
        });

        rl_empty_room_query = (RelativeLayout) findViewById(R.id.rl_empty_room_query);
        rl_empty_room_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //webview
            }
        });
    }
}
