package com.motivator.services.AlldataService;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.motivator.common.WebServices;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.database.PrefData;
import com.motivator.database.PutData;
import com.motivator.wecareyou.Splash;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.motivator.common.GeneralUtility.mContext;

/**
 * Created by sunil on 26-12-2016.
 */

public class GetAllReminderDesc {
    private final static String TAG="getallreminder";
    private final static String REMINDER_DESC_DETAILS = "http://www.funhabits.co/aaha/getreminder_desc_detail.php";

    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllReminderDesc(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


    public class GettingReminderDescriptionDetails extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(REMINDER_DESC_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("reminder_desc");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);


                        String rem_desc_id = object.optString("rem_desc_id");
                        String rem_desc_user_id = object.optString("rem_desc_user_id");
                        String rem_desc_user_name = object.optString("rem_desc_user_name");
                        String rem_desc_time = object.optString("rem_desc_time");
                        String rem_desc_day = object.optString("rem_desc_day");
                        String rem_desc_on_off = object.optString("rem_desc_on_off");
                        String rem_desc_stamp = object.optString("rem_desc_stamp");
                        String rem_desc_rem_id = object.optString("rem_desc_rem_id");

                        putData.addReminderDescFromGet(rem_desc_user_name,rem_desc_time,rem_desc_day,
                                rem_desc_on_off,rem_desc_stamp,rem_desc_rem_id);

                    }
                    activity.startActivity(new Intent(activity, Splash.class));
                    activity.finish();

                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                    activity.startActivity(new Intent(activity, Splash.class));
                    activity.finish();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }

}
