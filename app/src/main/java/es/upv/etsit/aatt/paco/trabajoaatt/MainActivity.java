package es.upv.etsit.aatt.paco.trabajoaatt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.helpers.Logger;
import com.cometchat.pro.models.Group;
import com.cometchat.pro.models.GroupMember;
import com.cometchat.pro.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.upv.etsit.aatt.paco.trabajoaatt.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCometChat();
        initViews();

    }

    private void initCometChat() {

        String appID = Constants.APP_ID; // Replace with your App ID
        String region = Constants.REGION; // Replace with your App Region ("eu" or "us")

        AppSettings appSettings= new AppSettings.AppSettingsBuilder()
                .subscribePresenceForAllUsers()
                .setRegion(region)
                // .autoEstablishSocketConnection(true)
                .build();

        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d(TAG, "Initialization completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Initialization failed with exception: " + e.getMessage());
            }
        });

    }


    private void initViews() {
        EditText userIdEditText = findViewById(R.id.userIdEditText);
        Button submitButton = findViewById(R.id.submitButton);
        /*
        CometChat.logout(new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d(TAG, "Logout completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Logout failed with exception: " + e.getMessage());
            }
        });*/

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            Log.d(TAG, "excepcion temporal" + e.getMessage());
        }

        /*
        // crear usuario
        User newUser = new User();
        User newUser2 = new User();
        User newUser3 = new User();

        try {

            newUser.setUid("jorge5");
            newUser.setName("leon");
            newUser2.setUid("gorka");
            newUser2.setName("caballo");
            // prueba
            newUser3.setUid("pedro");
            newUser3.setName("pez");

        } catch (NullPointerException e) {
            Log.d(TAG, "User creation failed with exception: " + e.getMessage());
        }

        CometChat.createUser(newUser2, Constants.API_KEY, new CometChat.CallbackListener() {

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG,"Gorka Created successfully " + newUser2.getName());
            }

            @Override
            public void onError(CometChatException e) {
                Log.d(TAG,"Could not create user gorka, " + e.getMessage());

            }
        });

        String gorkaUID = "gorka";
        String authKey = Constants.API_KEY;

        if (CometChat.getLoggedInUser()==null){

            CometChat.login(gorkaUID, authKey, new CometChat.CallbackListener<User>() {
                @Override
                public void onSuccess(User user) {
                    Log.d(TAG, "Login Successful : " + user.toString());
                }

                @Override
                public void onError(CometChatException e) {
                    Log.d(TAG, "Login failed with exception: " + e.getMessage());
                }
            });
        }else {
        // user already logged-in perform your action
        }

        CometChat.createUser(newUser, Constants.API_KEY, new CometChat.CallbackListener() {

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG,"Jorge Created successfully " + newUser.getName());
            }

            @Override
            public void onError(CometChatException e) {
                Log.d(TAG,"Could not create user jorge, " + e.getDetails());

            }
        });

        String jorgeUID = "jorge5";

        if (CometChat.getLoggedInUser()==null){

            CometChat.login(jorgeUID, authKey, new CometChat.CallbackListener<User>() {
                @Override
                public void onSuccess(User user) {
                    Log.d(TAG, "Login Successful : " + user.toString());
                }

                @Override
                public void onError(CometChatException e) {
                    Log.d(TAG, "Login failed with exception: " + e.getMessage());
                }
            });
        }else {
            // user already logged-in perform your action
        }

        CometChat.createUser(newUser3, Constants.API_KEY, new CometChat.CallbackListener() {

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG,"Pedro Created successfully " + newUser3.getName());
            }

            @Override
            public void onError(CometChatException e) {
                Log.d(TAG,"Could not create user pedro, " + e.getMessage());

            }
        });

        String pedroUID = "pedro";

        if (CometChat.getLoggedInUser()==null){

            CometChat.login(jorgeUID, authKey, new CometChat.CallbackListener<User>() {
                @Override
                public void onSuccess(User user) {
                    Log.d(TAG, "Login Successful : " + user.toString());
                }

                @Override
                public void onError(CometChatException e) {
                    Log.d(TAG, "Login failed with exception: " + e.getMessage());
                }
            });
        }else {
            // user already logged-in perform your action
        }*/

        submitButton.setOnClickListener(view -> CometChat.login(userIdEditText.getText().toString(), Constants.API_KEY, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Login Successful : " + user.toString());
                redirectToGroupListScreen();
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Login failed with exception: " + e.getMessage());
            }
        }));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            Log.d(TAG, "excepcion temporal" + e.getMessage());
        }

        /*
        String groupID = "grupoprofesores";
        String groupName = "Profesores ATELEM";
        String groupType = CometChatConstants.GROUP_TYPE_PRIVATE;
        String password = "";


        Group group = new Group(GUID, groupName, groupType, password);

        CometChat.createGroup(group, new CometChat.CallbackListener<Group>(){
            @Override
            public void onSuccess(Group group) {
                Log.d(TAG, "Group created successfully: " + group.toString());
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Group creation failed with exception: " + e.getMessage());
            }
        });*/

        /*
        List<GroupMember> members = new ArrayList<>();
        members.add(new GroupMember("jorge",CometChatConstants.SCOPE_ADMIN));
        members.add(new GroupMember("gorka", CometChatConstants.SCOPE_PARTICIPANT));
        members.add(new GroupMember("pedro", CometChatConstants.SCOPE_PARTICIPANT));


        CometChat.joinGroup(groupID, groupType, "", new CometChat.CallbackListener<Group>() {
            @Override
            public void onSuccess(Group joinedGroup) {
                Log.d(TAG, joinedGroup.toString());
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Group joining failed with exception: " + e.getMessage());
            }
        });

        List<GroupMember> members = new ArrayList<>();
        /*
        members.add(new GroupMember("jorge",CometChatConstants.SCOPE_ADMIN));
        members.add(new GroupMember("gorka", CometChatConstants.SCOPE_ADMIN));
        members.add(new GroupMember("pedro", CometChatConstants.SCOPE_ADMIN));
        members.add(new GroupMember("jelopez",CometChatConstants.SCOPE_ADMIN));
        members.add(new GroupMember("pbeneit", CometChatConstants.SCOPE_ADMIN));
        members.add(new GroupMember("fjmartin", CometChatConstants.SCOPE_ADMIN));

        CometChat.addMembersToGroup(groupID, members, null, new CometChat.CallbackListener<HashMap<String, String>>(){
            @Override
            public void onSuccess(HashMap<String, String> successMap) {
                Log.d(TAG, "Usuarios añadidos correctamente");
            }

            @Override
            public void onError(CometChatException e) {
                Log.d(TAG,"Algo ha fallado a la hora de crear el grupo: "+ e.getMessage());
            }
        });


        /*
        String newGUID = "newGUID";
        String newGroupName = "Chat entre dos personas";
        String newGroupType = CometChatConstants.GROUP_TYPE_PUBLIC;
        String newPassword = "";

        Group newGroup = new Group(newGUID, newGroupName, newGroupType, newPassword);

        CometChat.createGroup(newGroup, new CometChat.CallbackListener<Group>(){
            @Override
            public void onSuccess(Group newGroup) {
                Log.d(TAG, "Group created successfully: " + newGroup.toString());
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Group creation failed with exception: " + e.getMessage());
            }
        });

        List<GroupMember> newMembers = new ArrayList<>();
        newMembers.add(new GroupMember("jorge5",CometChatConstants.SCOPE_PARTICIPANT));
        newMembers.add(new GroupMember("gorka", CometChatConstants.SCOPE_PARTICIPANT));
        newMembers.add(new GroupMember("pedro", CometChatConstants.SCOPE_PARTICIPANT));

        CometChat.addMembersToGroup("newGUID", newMembers, null, new CometChat.CallbackListener<HashMap<String, String>>(){
            @Override
            public void onSuccess(HashMap<String, String> successMap) {
                Log.d(TAG, "Usuarios añadidos correctamente al nuevo grupo");
            }

            @Override
            public void onError(CometChatException e) {
                Log.d(TAG,"Algo ha fallado a la hora de crear el nuevo grupo: "+ e.getMessage());
            }
        });*/


    }

    private void redirectToGroupListScreen() {
        GroupListActivity.start(this);
    }

}
