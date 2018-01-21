package comm.food.asap;

/**
 * Created by sikinijs on 8/5/17.
 */
public class ChatMessage

{








    public String getMessage_time() {
        return Message_time;
    }

    public void setMessage_time(String message_time) {
        Message_time = message_time;
    }

    public String getMessge_id() {
        return Messge_id;
    }

    public void setMessge_id(String messge_id) {
        Messge_id = messge_id;
    }

    public String getMessage_type() {
        return Message_type;
    }

    public void setMessage_type(String message_type) {
        Message_type = message_type;
    }

    public String getMessage_text() {
        return Message_text;
    }

    public void setMessage_text(String message_text) {
        Message_text = message_text;
    }

    public String getMessage_sender() {
        return Message_sender;
    }

    public void setMessage_sender(String message_sender) {
        Message_sender = message_sender;
    }

    private String Message_type;
    private String Message_text;

    public ChatMessage(String message_type, String message_text, String message_sender, String message_time, String messge_id,String receiver) {
        Message_type = message_type;
        Message_text = message_text;
        Message_sender = message_sender;
        Message_time = message_time;
        Messge_id = messge_id;
        Receiver = receiver;
    }

    public ChatMessage() {
    }

    private String Message_sender;
    private String Message_time;
    private String Messge_id;

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }


    private String Receiver;

}