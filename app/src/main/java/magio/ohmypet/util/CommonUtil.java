package magio.ohmypet.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by mini on 2016-07-06.
 */
public class CommonUtil {

    static public void Toast(Context context, String str) {
        if (str != null) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }

    static public DecimalFormat DecimalFormat() {
        // 세자리로 끊어서 쉼표 보여주고, 소숫점 셋째짜리까지 보여준다.
        DecimalFormat df = new DecimalFormat("###,###.####");
        return df;
    }

}
