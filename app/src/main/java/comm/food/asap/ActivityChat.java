package comm.food.asap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

import static comm.food.asap.SignIN.MyPREFERENCES;
import static comm.food.asap.SignIN.sharedpreferences;
import static comm.food.asap.SignIN.useruser;
import static comm.food.asap.URLs.device_id;


public class ActivityChat extends Activity
{

    ChatView mChatView;
    String msgfrom, msgto="joseph", chattype="";
    String who;
    String chat_type;
    public static  String user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        if(database==null)
        {
            database.setPersistenceEnabled(true);
            myRef= database.getReference();
        }
        else
        {
            myRef= database.getReference();
        }


        getActionBar().setTitle("Food Asap");

        Intent intent= getIntent();
        user=sharedpreferences.getString("user", "user");
        msgfrom=user;
        //chat_type
        chat_type= sharedpreferences.getString("chat_type","null");

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

              //  Toast.makeText(ActivityChat.this, "Data changed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(ActivityChat.this, String.valueOf(databaseError), Toast.LENGTH_SHORT).show();
            }
        });
        myRef.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {


                ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);

                //ChatMessage send_msg = new ChatMessage(chat_type, sms, useruser , String.valueOf(message_id) , String.valueOf(message_id),receiver);



               // Toast.makeText(ActivityChat.this, "Data received!", Toast.LENGTH_SHORT).show();

                String message_sender=chat.getMessage_sender();

                String chatType=chat.getMessage_type();

                String Message_text=chat.getMessage_text();

                String message_receiver = chat.getReceiver();

                //Toast.makeText(ActivityChat.this, chat_type, Toast.LENGTH_SHORT).show();

                if(chat_type.equals("admin"))
                {


                    String chat_user= sharedpreferences.getString("chat_user",  "null");

                    Log.d("chat_data", "starting here!...");

                    Log.d("chat_data","Mesage sender -> "+ String.valueOf(chat.getMessage_sender()));

                    Log.d("chat_data","Mesage receiver -> "+ String.valueOf(chat.getReceiver()));

                    Log.d("chat_data","Mesage  -> "+ String.valueOf(chat.getMessage_text()));

                    Log.d("chat_data","Chat_type  -> "+ String.valueOf(chat.getMessage_type()));

                    Log.d("chat_data", "charted_user->" +chat_user);


                    if(message_receiver.equals(chat_user) || message_sender.equals(chat_user))
                    {
                        if(message_sender.equals(chat_user))

                        {
                            Bitmap yourIcon = null;
                            User you = new User(2, "", yourIcon);
                            Message receivedMessage = new Message.Builder()
                                    .setUser(you)
                                    .setRightMessage(false)
                                    .setMessageText(Message_text)
                                    .hideIcon(true)
                                    .build();
                            mChatView.receive(receivedMessage);
                        }
                        else
                            {




                            Bitmap myIcon = null;
                            User me = new User(1, "", myIcon);
                            Message message = new Message.Builder()
                                    .setUser(me)
                                    .setRightMessage(true)
                                    .setMessageText(Message_text)
                                    .hideIcon(true)
                                    .build();
                            mChatView.send(message);
                        }
                    }




                }
                else
                {


                    Log.d("chat_data2", "starting here!...");

                    Log.d("chat_data2","Mesage sender -> "+ String.valueOf(chat.getMessage_sender()));

                    Log.d("chat_data2","Mesage receiver -> "+ String.valueOf(chat.getReceiver()));

                    Log.d("chat_data2","Mesage  -> "+ String.valueOf(chat.getMessage_text()));

                    Log.d("chat_data2","Chat_type  -> "+ String.valueOf(chat.getMessage_type()));

                    Log.d("chat_data2", "current user ->"+useruser);

                    if(message_receiver.equals(useruser) || message_sender.equals(useruser))
                    {
                        if(message_sender.equals("admin@gmail.com"))
                        {


                            Bitmap yourIcon = null;
                            User you = new User(2,"" , yourIcon);
                            Message receivedMessage = new Message.Builder()
                                    .setUser(you)
                                    .setRightMessage(false)
                                    .setMessageText(Message_text)
                                    .hideIcon(true)
                                    .build();
                            mChatView.receive(receivedMessage);

                        }
                        else
                        {




                            Bitmap myIcon = null;
                            User me = new User(1, "", myIcon);
                            Message message = new Message.Builder()
                                    .setUser(me)
                                    .setRightMessage(true)
                                    .setMessageText(Message_text)
                                    .hideIcon(true)
                                    .build();
                            mChatView.send(message);
                        }
                    }


                }




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               // Toast.makeText(ActivityChat.this, "child chnaged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
              //  Toast.makeText(ActivityChat.this, "child removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
              //  Toast.makeText(ActivityChat.this, "Child moved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ActivityChat.this, "Database error!", Toast.LENGTH_SHORT).show();

            }
        });

        mChatView = (ChatView)findViewById(R.id.chat_view);
        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan900));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);
        mChatView.setOnClickSendButtonListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String sms= mChatView.getInputText();
                mChatView.setInputText("");
                Date d= new Date();
                Long message_id = new Date().getTime();
                String receiver;
                if(useruser=="admin@gmail.com")
                {
                   receiver= sharedpreferences.getString("chat_user", "null");
                }
                else
                {
                    receiver="admin@gmail.com";
                }

               // Toast.makeText(ActivityChat.this, receiver, Toast.LENGTH_SHORT).show();
                ChatMessage send_msg = new ChatMessage(chat_type, sms, useruser , String.valueOf(message_id) , String.valueOf(message_id),receiver);
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(send_msg);


                if(chat_type.equals("client"))
                {
                    insert ( useruser, device_id);

                }
            }



        });



    }

    public void insert (final String username, final String device_id)
    {
        class GetJSON extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("username", username);
                paramms.put("device_id",device_id);
                String s = rh.sendPostRequest(URLs.main_url+"save.php", paramms);
                return s;
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                Toast.makeText(ActivityChat.this, "Message sent!", Toast.LENGTH_SHORT).show();
            }

        }
        GetJSON jj = new GetJSON();
        jj.execute();


    }
}

