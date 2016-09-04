package magio.ohmypet.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import magio.ohmypet.activity.PetMainActivity;
import magio.ohmypet.model.Model_adopt;

public class Get extends AsyncTask<String, Void, JSONArray> {

    private Context mContext;
    private final String  TAG = "[LettleBoard]";

    public Get (Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        URL url;
        int responseConde;
        String responseStr = null;
        JSONArray responseJSON = null;

        try {
            url = new URL(params[0]);
            Log.d(TAG, "[GetLettle] Request URL : " + params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            responseConde = conn.getResponseCode();
            Log.d(TAG, "[GetLettle] Response Code : " + responseConde);

            OutputStream os   = null;
            InputStream is   = null;
            ByteArrayOutputStream baos = null;

            if(responseConde == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                responseStr = new String(byteData);
                responseJSON = new JSONArray(responseStr);
            }
        }
        catch (IOException e) {
            Log.d(TAG , "[GetLettle] IOException - " + e.toString());
        }
        catch (JSONException e) {
            Log.d(TAG ,"[GetLettle] JSONException - " + e.toString());
        }

        return responseJSON;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        try {
            PetMainActivity mainActivity = (PetMainActivity) mContext;
//            if(mainActivity.myLettle != null) mainActivity.myLettle.clear();

            if(result != null) {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jObj = result.getJSONObject(i);
                    Model_adopt adoptObj = new Model_adopt(jObj);
//                    mainActivity.myLettle.add(myObj);
//                    Log.d(TAG, "[GetLettle] onPostExecute : " + myObj.getReceiversId(0));
                }
                mainActivity.refresh(adoptObj);
            } else {
                Toast.makeText(mContext, "서버 접속에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.d(TAG, "[GetLettle] JSONException - " + e.toString());
            Toast.makeText(mContext, "서버와의 통신에서 알수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
        }

    }
}
