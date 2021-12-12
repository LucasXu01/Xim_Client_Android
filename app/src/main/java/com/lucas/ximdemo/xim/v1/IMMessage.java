package com.lucas.ximdemo.xim.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:40 上午
 */
class IMMessage implements Serializable {
    public static final int V2TIM_MSG_STATUS_SENDING = 1;
    public static final int V2TIM_MSG_STATUS_SEND_SUCC = 2;
    public static final int V2TIM_MSG_STATUS_SEND_FAIL = 3;
    public static final int V2TIM_MSG_STATUS_HAS_DELETED = 4;
    public static final int V2TIM_MSG_STATUS_LOCAL_IMPORTED = 5;
    public static final int V2TIM_MSG_STATUS_LOCAL_REVOKED = 6;
    public static final int V2TIM_PRIORITY_DEFAULT = 0;
    public static final int V2TIM_PRIORITY_HIGH = 1;
    public static final int V2TIM_PRIORITY_NORMAL = 2;
    public static final int V2TIM_PRIORITY_LOW = 3;
    public static final int V2TIM_ELEM_TYPE_NONE = 0;
    public static final int V2TIM_ELEM_TYPE_TEXT = 1;
    public static final int V2TIM_ELEM_TYPE_CUSTOM = 2;
    public static final int V2TIM_ELEM_TYPE_IMAGE = 3;
    public static final int V2TIM_ELEM_TYPE_SOUND = 4;
    public static final int V2TIM_ELEM_TYPE_VIDEO = 5;
    public static final int V2TIM_ELEM_TYPE_FILE = 6;
    public static final int V2TIM_ELEM_TYPE_LOCATION = 7;
    public static final int V2TIM_ELEM_TYPE_FACE = 8;
    public static final int V2TIM_ELEM_TYPE_GROUP_TIPS = 9;
    public static final int V2TIM_ELEM_TYPE_MERGER = 10;
    public static final int V2TIM_RECEIVE_MESSAGE = 0;
    public static final int V2TIM_NOT_RECEIVE_MESSAGE = 1;
    public static final int V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE = 2;
    private Message message = new Message();

    public V2TIMMessage() {
        this.message.setClientTime(BaseManager.getInstance().getServerTime());
    }

    void setMessage(Message message) {
        if (message != null) {
            this.message = message;
        }
    }

    Message getMessage() {
        return this.message;
    }

    public String getMsgID() {
        return this.message != null ? this.message.getMsgID() : "";
    }

    public long getTimestamp() {
        return this.message != null ? this.message.getTimestamp() : 0L;
    }

    public String getSender() {
        return this.message != null ? this.message.getSenderUserID() : null;
    }

    public String getNickName() {
        return this.message != null ? this.message.getNickName() : null;
    }

    public String getFriendRemark() {
        return this.message != null ? this.message.getFriendRemark() : null;
    }

    public String getFaceUrl() {
        return this.message != null ? this.message.getFaceUrl() : null;
    }

    public String getNameCard() {
        return this.message != null ? this.message.getNameCard() : null;
    }

    public String getGroupID() {
        return this.message != null && this.message.getMessageType() == Message.MESSAGE_TYPE_GROUP ? this.message.getGroupID() : null;
    }

    public String getUserID() {
        if (this.message != null && this.message.getMessageType() == Message.MESSAGE_TYPE_C2C) {
            return this.message.isMessageSender() ? this.message.getReceiverUserID() : this.message.getSenderUserID();
        } else {
            return null;
        }
    }

    public int getStatus() {
        int status = 1;
        return this.message == null ? status : this.message.getMessageStatus();
    }

    public int getElemType() {
        int elemType = 0;
        if (this.message == null) {
            return elemType;
        } else if (this.message.getMessageBaseElements().size() <= 0) {
            return elemType;
        } else {
            List<MessageBaseElement> messageBaseElementList = this.message.getMessageBaseElements();
            MessageBaseElement messageBaseElement = (MessageBaseElement)messageBaseElementList.get(0);
            if (messageBaseElement instanceof TextElement) {
                elemType = 1;
            } else if (messageBaseElement instanceof ImageElement) {
                elemType = 3;
            } else if (messageBaseElement instanceof VideoElement) {
                elemType = 5;
            } else if (messageBaseElement instanceof SoundElement) {
                elemType = 4;
            } else if (messageBaseElement instanceof FaceElement) {
                elemType = 8;
            } else if (messageBaseElement instanceof FileElement) {
                elemType = 6;
            } else if (messageBaseElement instanceof CustomElement) {
                elemType = 2;
            } else if (messageBaseElement instanceof LocationElement) {
                elemType = 7;
            } else if (messageBaseElement instanceof GroupTipsElement) {
                elemType = 9;
            } else if (messageBaseElement instanceof MergerElement) {
                elemType = 10;
            }

            return elemType;
        }
    }

    public V2TIMTextElem getTextElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 1) {
            V2TIMTextElem v2TIMTextElem = new V2TIMTextElem();
            v2TIMTextElem.setMessage(this.message);
            v2TIMTextElem.setElemIndex(0);
            return v2TIMTextElem;
        } else {
            return null;
        }
    }

    public V2TIMCustomElem getCustomElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 2) {
            V2TIMCustomElem v2TIMElem = new V2TIMCustomElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMImageElem getImageElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 3) {
            V2TIMImageElem v2TIMElem = new V2TIMImageElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMSoundElem getSoundElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 4) {
            V2TIMSoundElem v2TIMElem = new V2TIMSoundElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMVideoElem getVideoElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 5) {
            V2TIMVideoElem v2TIMElem = new V2TIMVideoElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMFileElem getFileElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 6) {
            V2TIMFileElem v2TIMElem = new V2TIMFileElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMLocationElem getLocationElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 7) {
            V2TIMLocationElem v2TIMElem = new V2TIMLocationElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMFaceElem getFaceElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 8) {
            V2TIMFaceElem v2TIMElem = new V2TIMFaceElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMMergerElem getMergerElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 10) {
            V2TIMMergerElem v2TIMElem = new V2TIMMergerElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public V2TIMGroupTipsElem getGroupTipsElem() {
        if (this.getElemType() == 0) {
            return null;
        } else if (this.getElemType() == 9) {
            V2TIMGroupTipsElem v2TIMElem = new V2TIMGroupTipsElem();
            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(0);
            return v2TIMElem;
        } else {
            return null;
        }
    }

    public String getLocalCustomData() {
        return this.message == null ? "" : this.message.getLocalCustomString();
    }

    public void setLocalCustomData(String localCustomData) {
        if (this.message != null) {
            this.message.setLocalCustomString(localCustomData);
        }
    }

    public int getLocalCustomInt() {
        return this.message == null ? 0 : this.message.getLocalCustomNumber();
    }

    public void setLocalCustomInt(int localCustomInt) {
        if (this.message != null) {
            this.message.setLocalCustomNumber(localCustomInt);
        }
    }

    public void setCloudCustomData(String data) {
        if (this.message != null) {
            this.message.setCloudCustomString(data);
        }
    }

    public String getCloudCustomData() {
        return this.message == null ? "" : this.message.getCloudCustomString();
    }

    public boolean isSelf() {
        return this.message != null ? this.message.isMessageSender() : true;
    }

    public boolean isRead() {
        return this.message != null ? this.message.isSelfRead() : true;
    }

    public boolean isPeerRead() {
        int status = this.getStatus();
        if (status != 1 && status != 3) {
            return this.message != null ? this.message.isPeerRead() : false;
        } else {
            return false;
        }
    }

    public int getPriority() {
        return this.message != null ? this.message.getPriority() : 0;
    }

    public V2TIMOfflinePushInfo getOfflinePushInfo() {
        if (this.message == null) {
            return null;
        } else {
            MessageOfflinePushInfo offlinePushInfo = this.message.getOfflinePushInfo();
            V2TIMOfflinePushInfo v2TIMOfflinePushInfo = new V2TIMOfflinePushInfo();
            v2TIMOfflinePushInfo.setMessageOfflinePushInfo(offlinePushInfo);
            return v2TIMOfflinePushInfo;
        }
    }

    public List<String> getGroupAtUserList() {
        if (this.message == null) {
            return new ArrayList();
        } else {
            List<MessageAtInfo> messageAtInfoList = this.message.getMessageGroupAtInfoList();
            List<String> userIDList = new ArrayList();
            Iterator var3 = messageAtInfoList.iterator();

            while(var3.hasNext()) {
                MessageAtInfo messageAtInfo = (MessageAtInfo)var3.next();
                userIDList.add(messageAtInfo.getAtUserID());
            }

            return userIDList;
        }
    }

    protected void setGroupAtUserList(List<String> userIDList) {
        if (this.message != null && userIDList != null) {
            List<MessageAtInfo> messageAtInfoList = new ArrayList();
            Iterator var3 = userIDList.iterator();

            while(var3.hasNext()) {
                String userID = (String)var3.next();
                MessageAtInfo messageAtInfo = new MessageAtInfo();
                messageAtInfo.setAtUserID(userID);
                messageAtInfoList.add(messageAtInfo);
            }

            this.message.setMessageGroupAtInfoList(messageAtInfoList);
        }
    }

    public long getSeq() {
        return this.message == null ? 0L : this.message.getSeq();
    }

    public long getRandom() {
        return this.message == null ? 0L : this.message.getRandom();
    }

    public boolean isExcludedFromUnreadCount() {
        return this.message == null ? false : this.message.isExcludedFromUnreadCount();
    }

    public void setExcludedFromUnreadCount(boolean excludedFromUnreadCount) {
        if (this.message != null) {
            this.message.setExcludedFromUnreadCount(excludedFromUnreadCount);
        }
    }

    public boolean isExcludedFromLastMessage() {
        return this.message == null ? false : this.message.isExcludedFromLastMessage();
    }

    public void setExcludedFromLastMessage(boolean excludedFromLastMessage) {
        if (this.message != null) {
            this.message.setExcludedFromLastMessage(excludedFromLastMessage);
        }
    }
}
