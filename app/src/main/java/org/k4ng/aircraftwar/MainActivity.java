package org.k4ng.aircraftwar;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置全屏 + 隐藏标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        AircraftWar airwar = new AircraftWar(this);
        setContentView(airwar);
    }
}