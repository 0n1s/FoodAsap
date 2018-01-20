package comm.food.asap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static comm.food.asap.SignIN.sharedpreferences;


public class ChatList extends AppCompatActivity
{

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView= (ListView)findViewById(R.id.listview);
        fetch_items();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


                TextView usernmame = (TextView)view.findViewById(R.id.textView27);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("chat_type", "admin");
                editor.putString("chat_user", usernmame.getText().toString());
                editor.commit();


                Intent intent = new Intent(ChatList.this, ActivityChat.class);
                intent.putExtra("chat_user", "");
                startActivity(intent);






            }
        });

    }



    public  void fetch_items()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching chats...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.main_url+"fetchchats.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                        progressDialog.cancel();

                        Log.d("foods", response);
                        JSONObject jsonObject = null;
                        ItemData itemdata;


                        /*


                        {"result":[{"sno":"4","userid":"111","device_id":"25","timestamp":"2018-01-20 21:16:41","success":1},{"sno":"5","userid":"111","device_id":"26","timestamp":"2018-01-20 21:16:41","success":1},{"sno":"6","userid":"111","device_id":"27","timestamp":"2018-01-20 21:16:41","success":1},{"sno":"7","userid":"","device_id":"","timestamp":"2018-01-20 21:18:23","success":1}]}
                         */

//{"sno":"4","userid":"111","device_id":"25","timestamp":"2018-01-20 21:16:41","success":1}

                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray("result");

                            for (int i = 0; i < result.length(); i++)
                            {
                                JSONObject jo = result.getJSONObject(i);
                                String userid=  jo.getString("userid");
                                String device_id = jo.getString("device_id");
                                String timestamp = jo.getString("timestamp");


                                HashMap<String, String> employees = new HashMap<>();
                                employees.put("userid", userid);
                                employees.put("device_id", device_id);
                                employees.put("timestamp", timestamp);
                                list.add(employees);

                            }

                            ListAdapter adapter = new SimpleAdapter(ChatList.this, list, R.layout.chatactivity,
                                    new String[]{"userid","timestamp"}, new int[]{
                                    R.id.textView27, R.id.textView29});
                            listView.setAdapter(adapter);


                        } catch (JSONException e)
                        {


                            Toast.makeText(ChatList.this, String.valueOf(e), Toast.LENGTH_SHORT).show();

                        }














                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                // Toast.makeText(mContext, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }



}
