package com.lucas.ximdemo.xim.adapter;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/14  8:31 下午
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.lucas.xim.conversation.Conversation;
import com.lucas.xim.message.IMMsg;
import com.lucas.ximdemo.R;
import com.lucas.ximdemo.xim.ui.ChatActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OnlineListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;

    public OnlineListAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_onlinelist, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTV.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {

        public TextView mTV;

        public NormalHolder(View itemView) {
            super(itemView);
            mTV = itemView.findViewById(R.id.tv_name);
            mTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mTV.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(mContext, ChatActivity.class);

                    Conversation conversation = new Conversation();
                    conversation.setConversationType(IMMsg.MESSAGE_TYPE_C2C);
                    conversation.setConversationId(mDatas.get(getPosition()));
                    intent.putExtra("conversation", conversation);
                    ActivityUtils.startActivity(intent);
                }
            });
        }

    }
}