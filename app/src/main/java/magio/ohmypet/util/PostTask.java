package magio.ohmypet.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import magio.ohmypet.activity.AdoptPostActivity;
import magio.ohmypet.model.Model_adopt;

/**
 * Created by kjmhe on 2016-09-04.
 */
public class PostTask extends AsyncTask<String, Void, Integer> {

    public Context mContext;
    private final String  TAG = "[LettleBoard]";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        URL url;
        int responseCode = 0;
        try {
            url = new URL(params[0]);
            Log.d(TAG, "[PostLettle] Request URL : " + params[0]);
            Log.d(TAG, "[PostLettle] Receiver : " + params[1]);
            Log.d(TAG, "[PostLettle] Title : " + params[2]);
            Log.d(TAG, "[PostLettle] Message : " + params[3]);

//            Model_adopt newLettle = new Model_adopt(params[1], params[2], params[3]); // user, pet, title
            Model_adopt newPost = new Model_adopt();

            Log.d(TAG, "[PostLettle] newLettle to JSON : " + newPost.toJSON());

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(dStream, "UTF-8");
            osw.write(newPost.toJSON());
            osw.flush();
            osw.close();
            responseCode = connection.getResponseCode();
            Log.d(TAG, "[PostLettle] Response Code : " + responseCode);

        }
        catch (IOException e) {
            Log.d(TAG , "[PostLettle] IOException - " + e.toString());
        }
        return responseCode;
    }

    @Override
    protected void onPostExecute(Integer result) {
        AdoptPostActivity NewMessageActivity = (AdoptPostActivity) mContext;
        Toast.makeText(mContext, "전송완료", Toast.LENGTH_SHORT).show();
        NewMessageActivity.finish();
    }
}
