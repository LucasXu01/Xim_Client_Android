package com.lucas.ximdemo.xim.v1;

import com.lucas.ximdemo.xim.message.TextElement;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:16 下午
 */


public class V2TIMTextElem extends V2TIMElem {
    private String text;

    public V2TIMTextElem() {
    }

    public String getText() {
        if (this.getElement() == null) {
            return this.text;
        } else {
            TextElement timTextElem = (TextElement)this.getElement();
            return timTextElem.getTextContent();
        }
    }

    public void setText(String text) {
        if (this.getElement() == null) {
            this.text = text;
        } else {
            TextElement timTextElem = (TextElement)this.getElement();
            timTextElem.setTextContent(text);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("V2TIMTextElem--->").append("text:").append(this.getText());
        return stringBuilder.toString();
    }
}
