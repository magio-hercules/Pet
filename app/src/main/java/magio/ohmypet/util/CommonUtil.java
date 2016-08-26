package magio.ohmypet.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mini on 2016-07-06.
 */
public class CommonUtil {
    static public void Toast(Context context, String str) {
        if (str != null) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }
}
