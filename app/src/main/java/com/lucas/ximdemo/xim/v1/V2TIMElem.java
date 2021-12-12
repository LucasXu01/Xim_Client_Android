package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:17 下午
 */

import com.lucas.ximdemo.xim.message.MessageBaseElement;

import java.io.Serializable;
import java.util.List;

public class V2TIMElem implements Serializable {
    private final String TAG = "V2TIMElem";
    private Message message;
    private int elemIndex;

    public V2TIMElem() {
    }

    void setMessage(Message timMessage) {
        this.message = timMessage;
    }

    Message getMessage() {
        return this.message;
    }

    MessageBaseElement getElement() {
        if (this.message == null) {
            return null;
        } else {
            List<MessageBaseElement> messageBaseElementList = this.message.getMessageBaseElements();
            return this.elemIndex >= 0 && messageBaseElementList.size() > this.elemIndex ? (MessageBaseElement)messageBaseElementList.get(this.elemIndex) : null;
        }
    }

    void setElemIndex(int index) {
        this.elemIndex = index;
    }

    public V2TIMElem getNextElem() {
        if (this.message == null) {
            return null;
        } else {
            List<MessageBaseElement> messageBaseElementList = this.message.getMessageBaseElements();
            int nextElemIndex = this.elemIndex + 1;
            if (nextElemIndex >= messageBaseElementList.size()) {
                return null;
            } else {
                V2TIMElem v2TIMElem = new V2TIMElem();
                MessageBaseElement messageBaseElement = (MessageBaseElement)messageBaseElementList.get(nextElemIndex);
                if (messageBaseElement instanceof TextElement) {
                    v2TIMElem = new V2TIMTextElem();
                } else if (messageBaseElement instanceof ImageElement) {
                    v2TIMElem = new V2TIMImageElem();
                } else if (messageBaseElement instanceof VideoElement) {
                    v2TIMElem = new V2TIMVideoElem();
                } else if (messageBaseElement instanceof SoundElement) {
                    v2TIMElem = new V2TIMSoundElem();
                } else if (messageBaseElement instanceof FaceElement) {
                    v2TIMElem = new V2TIMFaceElem();
                } else if (messageBaseElement instanceof FileElement) {
                    v2TIMElem = new V2TIMFileElem();
                } else if (messageBaseElement instanceof CustomElement) {
                    v2TIMElem = new V2TIMCustomElem();
                } else if (messageBaseElement instanceof LocationElement) {
                    v2TIMElem = new V2TIMLocationElem();
                } else if (messageBaseElement instanceof MergerElement) {
                    v2TIMElem = new V2TIMMergerElem();
                } else if (messageBaseElement instanceof GroupTipsElement) {
                    v2TIMElem = new V2TIMGroupTipsElem();
                }

                ((V2TIMElem)v2TIMElem).setMessage(this.message);
                ((V2TIMElem)v2TIMElem).setElemIndex(nextElemIndex);
                return (V2TIMElem)v2TIMElem;
            }
        }
    }

    public void appendElem(V2TIMElem v2TIMElem) {
        if (this.message == null) {
            IMLog.e("V2TIMElem", "appendElem error, must be first elem from message");
        } else {
            if (v2TIMElem instanceof V2TIMTextElem) {
                TextElement textElem = new TextElement();
                textElem.setTextContent(((V2TIMTextElem)v2TIMElem).getText());
                this.message.addElement(textElem);
            } else if (v2TIMElem instanceof V2TIMCustomElem) {
                CustomElement customElem = new CustomElement();
                customElem.setData(((V2TIMCustomElem)v2TIMElem).getData());
                customElem.setDescription(((V2TIMCustomElem)v2TIMElem).getDescription());
                customElem.setExtension(((V2TIMCustomElem)v2TIMElem).getExtension());
                this.message.addElement(customElem);
            } else if (v2TIMElem instanceof V2TIMFaceElem) {
                FaceElement faceElem = new FaceElement();
                faceElem.setFaceIndex(((V2TIMFaceElem)v2TIMElem).getIndex());
                faceElem.setFaceData(((V2TIMFaceElem)v2TIMElem).getData());
                this.message.addElement(faceElem);
            } else {
                if (!(v2TIMElem instanceof V2TIMLocationElem)) {
                    IMLog.e("V2TIMElem", "appendElem error, not support this elem type");
                    return;
                }

                LocationElement locationElem = new LocationElement();
                locationElem.setLatitude(((V2TIMLocationElem)v2TIMElem).getLatitude());
                locationElem.setLongitude(((V2TIMLocationElem)v2TIMElem).getLongitude());
                locationElem.setDescription(((V2TIMLocationElem)v2TIMElem).getDesc());
                this.message.addElement(locationElem);
            }

            v2TIMElem.setMessage(this.message);
            v2TIMElem.setElemIndex(this.message.getMessageBaseElements().size() - 1);
        }
    }

    public class V2ProgressInfo {
        private long currentSize;
        private long totalSize;

        public V2ProgressInfo(long curr, long total) {
            this.currentSize = curr;
            this.totalSize = total;
        }

        public long getCurrentSize() {
            return this.currentSize;
        }

        public long getTotalSize() {
            return this.totalSize;
        }
    }
}
