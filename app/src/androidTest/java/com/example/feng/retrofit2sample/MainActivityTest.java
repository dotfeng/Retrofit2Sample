package com.example.feng.retrofit2sample;

import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by feng on 2015/11/5.
 */
public class MainActivityTest extends InstrumentationTestCase {
    private MainActivity main = null;
    private Button btn_button1 = null;
    private TextView tv_text = null;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent();
        intent.setClassName("com.example.feng.retrofit2sample", MainActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        main = (MainActivity) getInstrumentation().startActivitySync(intent);
        tv_text = (TextView) main.findViewById(R.id.tv_text);
        btn_button1 = (Button) main.findViewById(R.id.btn_button1);
    }

    @Override
    protected void tearDown() throws Exception {
        main.finish();
        super.tearDown();
    }

    public void testActivity() throws Exception {
        SystemClock.sleep(1500);
        getInstrumentation().runOnMainSync(new PerformClick(btn_button1));
        SystemClock.sleep(3000);
        assertEquals("Hello Android", tv_text.getText().toString());
    }

    private class PerformClick implements Runnable {
        Button btn;
        public PerformClick(Button button) {
            btn = button;
        }

        public void run() {
            btn.performClick();
        }
    }

    public void testAdd() throws Exception{
        String tag = "testAdd";
        int test = main.add(1, 1);
        assertEquals(2, test);
    }
}
