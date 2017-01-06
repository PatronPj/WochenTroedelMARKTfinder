package de.htw_berlin.cihanozturk.wochentroedelmarktfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements OnClickListener {

    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_start:
                Intent inten = new Intent(this,HauptActivity.class);
               startActivity(inten);
                break;

            default:
                break;
        }
    }
}
