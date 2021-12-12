package com.lucas.ximdemo.xim.v1;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:25 下午
 */
public class IMConversationManagerImpl extends IMConversationManager {

    private static class V2TIMConversationManagerImplHolder {
        private static final IMConversationManagerImpl v2TIMConversationManagerImpl = new IMConversationManagerImpl();
    }

    static IMConversationManagerImpl getInstance() {
        return V2TIMConversationManagerImplHolder.v2TIMConversationManagerImpl;
    }

    private ConversationListener mConversationListener;
    private V2TIMConversationListener mV2TIMConversationListener;
    private final List<V2TIMConversationListener> mV2TIMConversationListenerList = new ArrayList<>();

    private IMConversationManagerImpl() {
        initListener();
    }

    private void initListener() {
        if (mConversationListener == null) {
            mConversationListener = new ConversationListener() {
                @Override
                public void onSyncServerStart() {
                    for (V2TIMConversationListener listener : mV2TIMConversationListenerList) {
                        listener.onSyncServerStart();
                    }
                }

                @Override
                public void onSyncServerFinish() {
                    for (V2TIMConversationListener listener : mV2TIMConversationListenerList) {
                        listener.onSyncServerFinish();
                    }
                }

                @Override
                public void onSyncServerFailed() {
                    for (V2TIMConversationListener listener : mV2TIMConversationListenerList) {
                        listener.onSyncServerFailed();
                    }
                }

                @Override
                public void onNewConversation(List<Conversation> conversationList) {
                    List<V2TIMConversation> v2TIMConversationList = new ArrayList<>();
                    for (Conversation conversation : conversationList) {
                        V2TIMConversation v2TIMConversation = new V2TIMConversation();
                        v2TIMConversation.setConversation(conversation);
                        v2TIMConversationList.add(v2TIMConversation);
                    }
                    List<V2TIMConversation> unmodifiableList = Collections.unmodifiableList(v2TIMConversationList);
                    for (V2TIMConversationListener listener : mV2TIMConversationListenerList) {
                        listener.onNewConversation(unmodifiableList);
                    }
                }

                @Override
                public void onConversationChanged(List<Conversation> conversationList) {
                    List<V2TIMConversation> v2TIMConversationList = new ArrayList<>();
                    for (Conversation conversation : conversationList) {
                        V2TIMConversation v2TIMConversation = new V2TIMConversation();
                        v2TIMConversation.setConversation(conversation);
                        v2TIMConversationList.add(v2TIMConversation);
                    }
                    List<V2TIMConversation> unmodifiableList = Collections.unmodifiableList(v2TIMConversationList);
                    for (V2TIMConversationListener listener : mV2TIMConversationListenerList) {
                        listener.onConversationChanged(unmodifiableList);
                    }
                }

                @Override
                public void onTotalUnreadMessageCountChanged(long totalUnreadCount) {
                    for (V2TIMConversationListener listener : mV2TIMConversationListenerList) {
                        listener.onTotalUnreadMessageCountChanged(totalUnreadCount);
                    }
                }
            };
        }
        ConversationManager.getInstance().setConversationListener(mConversationListener);
    }

    @Override
    public void setConversationListener(final V2TIMConversationListener listener) {
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mV2TIMConversationListener != null) {
                    mV2TIMConversationListenerList.remove(mV2TIMConversationListener);
                }
                if (listener != null) {
                    mV2TIMConversationListenerList.add(listener);
                }
                mV2TIMConversationListener = listener;
            }
        });
    }

    @Override
    public void addConversationListener(final V2TIMConversationListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mV2TIMConversationListenerList.contains(listener)) {
                    return;
                }
                mV2TIMConversationListenerList.add(listener);
            }
        });
    }

    @Override
    public void removeConversationListener(final V2TIMConversationListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mV2TIMConversationListenerList.remove(listener);
            }
        });
    }

    @Override
    public void getConversationList(long nextSeq, int count, final V2TIMValueCallback<V2TIMConversationResult> callback) {
        V2TIMValueCallback<ConversationResult> v2callback = new V2TIMValueCallback<ConversationResult>() {
            @Override
            public void onSuccess(ConversationResult conversationResult) {
                if (callback != null) {
                    V2TIMConversationResult v2TIMConversationResult = new V2TIMConversationResult();
                    v2TIMConversationResult.setConversationResult(conversationResult);
                    callback.onSuccess(v2TIMConversationResult);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        ConversationManager.getInstance().getConversationList(nextSeq, count, new XIMCallback<ConversationResult>(v2callback) {
            @Override
            public void success(ConversationResult data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });

        BaseManager.getInstance().checkTUIComponent(BaseManager.TUI_COMPONENT_CONVERSATION);
    }

    @Override
    public void getConversationList(List<String> conversationIDList, final V2TIMValueCallback<List<V2TIMConversation>> callback) {
        if (conversationIDList == null || conversationIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "conversationIDList cannot be empty");
            }
            return;
        }
        List<ConversationKey> conversationKeyList = new ArrayList<>();
        for (String conversationID : conversationIDList) {
            ConversationKey conversationKey = getConversationKey(conversationID);
            conversationKeyList.add(conversationKey);
        }
        V2TIMValueCallback<List<Conversation>> v2callback = new V2TIMValueCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                List<V2TIMConversation> v2TIMConversationList = new ArrayList<>();
                for (Conversation conversation : conversations) {
                    V2TIMConversation v2TIMConversation = new V2TIMConversation();
                    v2TIMConversation.setConversation(conversation);
                    v2TIMConversationList.add(v2TIMConversation);
                }
                if (callback != null) {
                    callback.onSuccess(v2TIMConversationList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        ConversationManager.getInstance().getConversationList(conversationKeyList, new XIMCallback<List<Conversation>>(v2callback) {
            @Override
            public void success(List<Conversation> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

//    @Override
//    public void searchConversations(List<String> keywordList, final V2TIMValueCallback<List<V2TIMConversation>> callback) {
//        V2TIMValueCallback<List<Conversation>> v2callback = new V2TIMValueCallback<List<Conversation>>() {
//            @Override
//            public void onSuccess(List<Conversation> conversations) {
//                List<V2TIMConversation> v2TIMConversationList = new ArrayList<>();
//                for (Conversation conversation : conversations) {
//                    V2TIMConversation v2TIMConversation = new V2TIMConversation();
//                    v2TIMConversation.setConversation(conversation);
//                    v2TIMConversationList.add(v2TIMConversation);
//                }
//                if (callback != null) {
//                    callback.onSuccess(v2TIMConversationList);
//                }
//            }
//
//            @Override
//            public void onError(int code, String desc) {
//                if (callback != null) {
//                    callback.onError(code ,desc);
//                }
//            }
//        };
//
//        ConversationManager.getInstance().searchConversations(keywordList, new IMCallback<List<Conversation>>(v2callback) {
//            @Override
//            public void success(List<Conversation> data) {
//                super.success(data);
//            }
//
//            @Override
//            public void fail(int code, String errorMessage) {
//                super.fail(code, errorMessage);
//            }
//        });
//    }

    @Override
    public void getConversation(String conversationID, final V2TIMValueCallback<V2TIMConversation> callback) {
        if (TextUtils.isEmpty(conversationID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "conversationID is empty");
            }
            return;
        }
        ConversationKey conversationKey = getConversationKey(conversationID);

        V2TIMValueCallback<Conversation> v2TIMValueCallback = new V2TIMValueCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                if (callback != null) {
                    V2TIMConversation v2TIMConversation = new V2TIMConversation();
                    v2TIMConversation.setConversation(conversation);

                    callback.onSuccess(v2TIMConversation);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        ConversationManager.getInstance().getConversationInfo(conversationKey, new XIMCallback<Conversation>(v2TIMValueCallback) {
            @Override
            public void success(Conversation data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteConversation(String conversationID, V2TIMCallback callback) {
        if (TextUtils.isEmpty(conversationID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "conversationID is empty");
            }
            return;
        }
        ConversationKey conversationKey = getConversationKey(conversationID);

        ConversationManager.getInstance().deleteConversation(conversationKey, true, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void setConversationDraft(String conversationID, String draftText, V2TIMCallback callback) {
        if (TextUtils.isEmpty(conversationID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "conversationID is empty");
            }
            return;
        }
        ConversationKey conversationKey = getConversationKey(conversationID);
        DraftMessage draftMessage = new DraftMessage();
        if (draftText != null) {
            draftMessage.setUserDefinedData(draftText.getBytes());
        }

        ConversationManager.getInstance().setConversationDraft(conversationKey, draftMessage, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void pinConversation(String conversationID, boolean isPinned, V2TIMCallback callback) {
        if (TextUtils.isEmpty(conversationID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "conversationID is empty");
            }
            return;
        }
        ConversationKey conversationKey = getConversationKey(conversationID);
        ConversationManager.getInstance().pinConversation(conversationKey, isPinned, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getTotalUnreadMessageCount(V2TIMValueCallback<Long> callback) {
        ConversationManager.getInstance().getTotalUnreadMessageCount(new XIMCallback<Long>(callback) {
            @Override
            public void success(Long data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    protected ConversationKey getConversationKey(String conversationID) {
        ConversationKey conversationKey = new ConversationKey();
        String v1ConvID = "";
        int index = conversationID.indexOf(V2TIMConversation.CONVERSATION_C2C_PREFIX);
        if (index == 0) {
            v1ConvID = conversationID.substring(V2TIMConversation.CONVERSATION_C2C_PREFIX.length());
            conversationKey.setConversationType(ConversationKey.TYPE_C2C);
            conversationKey.setConversationID(v1ConvID);
        } else {
            index = conversationID.indexOf(V2TIMConversation.CONVERSATION_GROUP_PREFIX);
            if (index == 0) {
                v1ConvID = conversationID.substring(V2TIMConversation.CONVERSATION_GROUP_PREFIX.length());
                conversationKey.setConversationType(ConversationKey.TYPE_GROUP);
                conversationKey.setConversationID(v1ConvID);
            }
        }
        return conversationKey;
    }
}
