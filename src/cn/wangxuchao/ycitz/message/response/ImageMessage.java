package cn.wangxuchao.ycitz.message.response;

/**
 * 图片消息
 */
public class ImageMessage extends BaseMessage {

    // 图片
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
