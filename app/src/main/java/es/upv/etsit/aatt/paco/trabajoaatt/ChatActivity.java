package es.upv.etsit.aatt.paco.trabajoaatt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.CustomMessage;
import com.cometchat.pro.models.MediaMessage;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import es.upv.etsit.aatt.paco.trabajoaatt.models.MessageWrapper;
import es.upv.etsit.aatt.paco.trabajoaatt.utils.Constants;


public class ChatActivity extends AppCompatActivity {

    static public String currentGroupId = null;
    static public String currentGroupName = null;

    private static final String TAG = MainActivity.class.getSimpleName();
    MessagesListAdapter<IMessage> adapter;

    public static void start(Context context, String groupId, String name) {
        Intent starter = new Intent(context, ChatActivity.class);
        starter.putExtra(name, groupId);
        context.startActivity(starter);
        currentGroupId = groupId;
        currentGroupName = name;
    }

    private String receiverID = currentGroupId;
    private String messageText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        if (intent != null) {
            intent.getStringExtra(currentGroupId);
        }

        fetchPreviousMessages();
        initViews();
        addListener();
        sendMessage(messageText);
    }

    private void fetchPreviousMessages() {
        MessagesRequest messagesRequest = new MessagesRequest.MessagesRequestBuilder().setGUID(currentGroupId).build();
        messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
            @Override
            public void onSuccess(List<BaseMessage> baseMessages) {
                addMessages(baseMessages);
            }

            @Override
            public void onError(CometChatException e) {

            }
        });
    }

    private void addMessages(List<BaseMessage> baseMessages) {
        List<IMessage> list = new ArrayList<>();
        for (BaseMessage message : baseMessages) {
            if (message instanceof TextMessage) {
                list.add(new MessageWrapper((TextMessage) message));
            }
        }

        adapter.addToEnd(list, true);
    }


    private void addListener(){
        String listenerID = "listener";
        CometChat.addMessageListener(listenerID, new CometChat.MessageListener() {
            @Override
            public void onTextMessageReceived(TextMessage textMessage) {

                addMessage(textMessage);
            }
            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
                Log.d(TAG, "Media message received successfully: " + mediaMessage.toString());
            }
            @Override
            public void onCustomMessageReceived(CustomMessage customMessage) {
                Log.d(TAG, "Custom message received successfully: " +customMessage.toString());     
            }
        });
    }


    private void initViews(){
        MessageInput inputView = findViewById(R.id.input);
        MessagesList messagesList = findViewById(R.id.messagesList);

        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                sendMessage(input.toString());
                return true;
            }
        });

        String senderId = CometChat.getLoggedInUser().getUid();
        Log.d(TAG, "el senderId es: " + senderId);
        ImageLoader imageLoader = (imageView, url, payload) -> Picasso.get().load(url).into(imageView);
        adapter = new MessagesListAdapter<>(senderId, imageLoader);

        messagesList.setAdapter(adapter);
    }


    private void sendMessage(String message){
        String senderId = CometChat.getLoggedInUser().getUid();
        if (message != null) {
            message = senderId.toUpperCase() + ":" + "\n" + message;
        }
        String receiverType = CometChatConstants.RECEIVER_TYPE_GROUP;
        TextMessage textMessage = new TextMessage(receiverID, message, receiverType);

        CometChat.sendMessage(textMessage, new CometChat.CallbackListener <TextMessage> () {
            @Override
            public void onSuccess(TextMessage textMessage) {
                Log.d(TAG, "Message sent successfully: " + textMessage.toString());
                addMessage(textMessage);
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Message sending failed with exception: " + e.getMessage());
            }
        });
    }


    private void addMessage(TextMessage textMessage){
        adapter.addToStart(new MessageWrapper(textMessage), true);
    }
}