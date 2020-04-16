package in.org.basic;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MSG {

    private static  MainActivity MA;
    void SetMA(MainActivity MA){this.MA=MA;}


    static void Show(String msg, Context context)
    {
        LinearLayout lLayout = new LinearLayout(context);
        TextView tv=new TextView(context);
        tv.setText(msg);
        tv.setTextSize(22);
        tv.setPadding(25,5,25,5);
        tv.setBackgroundColor(Color.WHITE);
        lLayout.addView(tv);

// Toast...
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(lLayout);
        toast.show();
    }


}
