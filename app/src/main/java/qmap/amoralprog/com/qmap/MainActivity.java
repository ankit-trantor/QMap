package qmap.amoralprog.com.qmap;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import qmap.amoralprog.com.qmap.service.MyLocationService;
import qmap.amoralprog.com.qmap.ui.MapFragment;
import qmap.amoralprog.com.qmap.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MainFragment())
                    .commitNow();


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            startForegroundService(new Intent(this, MyLocationService.class));
        } else {
            startService(new Intent(this, MyLocationService.class));

        }




    }
}
