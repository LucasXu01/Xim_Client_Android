package com.lucas.xim.conversation;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  3:24 下午
 */


import com.lucas.xim.BaseConstants;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.BaseManager;
import com.lucas.xim.v1.IMContext;

import java.util.List;

public class ConversationManager {
    private static final String TAG = "ConversationManager";

    private static class ConversationManagerHolder {
        private static final ConversationManager conversationManager = new ConversationManager();
    }

    public static ConversationManager getInstance() {
        return ConversationManager.ConversationManagerHolder.conversationManager;
    }

    private ConversationListener mInternalConversationListener;
    private ConversationListener mConversationListener;

    public void init() {
        initInternalConversationListener();
    }

    private void initInternalConversationListener() {
        if (mInternalConversationListener == null) {
            mInternalConversationListener = new ConversationListener() {
                @Override
                public void onSyncServerStart() {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mConversationListener != null) {
                                mConversationListener.onSyncServerStart();
                            }
                        }
                    });
                }

                @Override
                public void onSyncServerFinish() {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mConversationListener != null) {
                                mConversationListener.onSyncServerFinish();
                            }
                        }
                    });
                }

                @Override
                public void onSyncServerFailed() {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mConversationListener != null) {
                                mConversationListener.onSyncServerFailed();
                            }
                        }
                    });
                }

                @Override
                public void onNewConversation(final List<Conversation> conversationList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mConversationListener != null) {
                                mConversationListener.onNewConversation(conversationList);
                            }
                        }
                    });
                }

                @Override
                public void onConversationChanged(final List<Conversation> conversationList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mConversationListener != null) {
                                mConversationListener.onConversationChanged(conversationList);
                            }
                        }
                    });
                }

                @Override
                public void onTotalUnreadMessageCountChanged(final long totalUnreadCount) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mConversationListener != null) {
                                mConversationListener.onTotalUnreadMessageCountChanged(totalUnreadCount);
                            }
                        }
                    });
                }

            };
        }
    }

    public void setConversationListener(ConversationListener conversationListener) {
        mConversationListener = conversationListener;
    }

    public void getConversationList(long nextSeq, int count, IMCallback<ConversationResult> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeGetConversationList(nextSeq, count, callback);
    }

    public void getConversationList(List<ConversationKey> conversationKeyList, IMCallback<List<Conversation>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeGetConversations(conversationKeyList, callback);
    }

    public void getConversationInfo(ConversationKey conversationKey, IMCallback<Conversation> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeGetConversationInfo(conversationKey, callback);
    }

    public void deleteConversation(ConversationKey conversationKey, boolean clearCloudMessage, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeDeleteConversation(conversationKey, clearCloudMessage, callback);
    }


    public void pinConversation(ConversationKey conversationKey, boolean isPinned, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativePinConversation(conversationKey, isPinned, callback);
    }

    public void getTotalUnreadMessageCount(IMCallback<Long> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeGetTotalUnreadMessageCount(callback);
    }

    public void setCosSaveRegionForConversation(ConversationKey conversationKey, String cosSaveRegion, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeSetCosSaveRegionForConversation(conversationKey, cosSaveRegion, callback);
    }

    public void clearUnreadMessage(boolean clearC2CUnreadMessage, boolean clearGroupUnreadMessage, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeClearUnreadMessage(clearC2CUnreadMessage, clearGroupUnreadMessage, callback);
    }

    protected native void nativeSetConversationListener(ConversationListener listener);

    protected native void nativeGetConversationList(long nextSeq, int count, IMCallback callback);

    protected native void nativeGetConversations(List<ConversationKey> conversationKeyList, IMCallback callback);

    protected native void nativeGetConversationInfo(ConversationKey conversationKey, IMCallback callback);

    protected native void nativeDeleteConversation(ConversationKey conversationKey, boolean clearCloudMessage, IMCallback callback);

    protected native void nativePinConversation(ConversationKey conversationKey, boolean isPinned, IMCallback callback);

    protected native void nativeGetTotalUnreadMessageCount(IMCallback callback);

    protected native void nativeSetCosSaveRegionForConversation(ConversationKey conversationKey, String cosSaveRegion, IMCallback callback);

    protected native void nativeClearUnreadMessage(boolean clearC2CUnreadMessage, boolean clearGroupUnreadMessage, IMCallback callback);
}

