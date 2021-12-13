package com.lucas.xim.message;


import com.lucas.xim.v1.IMMessage;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/13  3:29 下午
 */

public class CustomElement extends MessageBaseElement {
    private byte[] data;
    private byte[] extension;
    private byte[] sound;
    private String description;

    public CustomElement() {
        setElementType(IMMessage.V2TIM_ELEM_TYPE_CUSTOM);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getExtension() {
        return extension;
    }

    public void setExtension(byte[] extension) {
        this.extension = extension;
    }

    public byte[] getSound() {
        return sound;
    }

    public void setSound(byte[] sound) {
        this.sound = sound;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
