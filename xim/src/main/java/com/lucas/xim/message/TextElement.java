package com.lucas.xim.message;


import com.lucas.xim.v1.IMMessage;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:18 下午
 */

public class TextElement extends MessageBaseElement {
    private String textContent;

    public TextElement() {
        setElementType(IMMessage.V2TIM_ELEM_TYPE_TEXT);
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}