package com.lucas.xim.message;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:17 下午
 */
import java.io.Serializable;

public class MessageBaseElement implements Serializable {
    public static int UUID_TYPE_VIDEO_THUMB = 0;
    public static int UUID_TYPE_FILE        = 1;
    public static int UUID_TYPE_VIDEO       = 2;
    public static int UUID_TYPE_AUDIO       = 3;

    protected int elementType;

    public int getElementType() {
        return elementType;
    }

    public void setElementType(int elementType) {
        this.elementType = elementType;
    }

    public boolean update(MessageBaseElement element) { return false; }
}
