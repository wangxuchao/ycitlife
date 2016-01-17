package cn.wangxuchao.ycitz.message.response;

/**
 * 语音消息
 *
 * @author 王绪超
 */
public class VoiceMessage extends BaseMessage {

	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
