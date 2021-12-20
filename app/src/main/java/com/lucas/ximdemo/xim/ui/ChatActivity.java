package com.lucas.ximdemo.xim.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.lucas.xim.XIMManager;
import com.lucas.xim.conversation.Conversation;
import com.lucas.xim.message.IMMsg;
import com.lucas.xim.message.IMMsgManagerImpl;
import com.lucas.xim.v1.MessageListener;
import com.lucas.ximdemo.R;
import com.lucas.ximdemo.xim.adapter.Adapter_ChatMessage;
import com.lucas.ximdemo.xim.modle.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText et_content;
    private ListView listView;
    private Button btn_send;
    private List<ChatMessage> chatMessageList = new ArrayList<>();//消息列表
    private Adapter_ChatMessage adapter_chatMessage;

    public Conversation conversation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);
        mContext = ChatActivity.this;

        conversation = (Conversation) getIntent().getSerializableExtra("conversation");


        listView = findViewById(R.id.chatmsg_listView);
        btn_send = findViewById(R.id.btn_send);
        et_content = findViewById(R.id.et_content);
        btn_send.setOnClickListener(this);


        initView();

        XIMManager.getInstance().addConversationListener(conversation, new MessageListener() {
            @Override
            public void onReceiveNewMessage(IMMsg imMsg) {
                super.onReceiveNewMessage(imMsg);

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setContent(imMsg.getContent());
                chatMessage.setIsMeSend(0);
                chatMessage.setIsRead(1);
                chatMessage.setTime(System.currentTimeMillis() + "");
                chatMessageList.add(chatMessage);
                initChatMsgListView();

            }
        });

    }


    private void initView() {
        //监听输入框的变化
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_content.getText().toString().length() > 0) {
                    btn_send.setVisibility(View.VISIBLE);
                } else {
                    btn_send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String content = et_content.getText().toString();
                if (content.length() <= 0) {
                    ToastUtils.showLong("消息不能为空哟");
                    return;
                }

                //发送消息
                IMMsg imMsg = IMMsgManagerImpl.getInstance().createC2CMsg( conversation.getConversationId(), content);
                XIMManager.getInstance().send2User(imMsg);


                //暂时将发送的消息加入消息列表，实际以发送成功为准（也就是服务器返回你发的消息时）
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setContent(content);
                chatMessage.setIsMeSend(1);
                chatMessage.setIsRead(1);
                chatMessage.setTime(System.currentTimeMillis() + "");
                chatMessageList.add(chatMessage);
                initChatMsgListView();
                et_content.setText("");

                break;
            default:
                break;
        }
    }

    private void initChatMsgListView() {
        adapter_chatMessage = new Adapter_ChatMessage(mContext, chatMessageList);
        listView.setAdapter(adapter_chatMessage);
        listView.setSelection(chatMessageList.size());
    }


}
