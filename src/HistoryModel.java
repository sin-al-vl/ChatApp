import java.util.Date;
import java.util.Observable;

/**
 * Created by Александр on 05.12.2015.
 */
public class HistoryModel extends Observable {

    public HistoryModel() {

    }

    public void	addMessage(Message m){

    }

    public void	addMessage(String nick, java.util.Date date, String text){
        addMessage(new Message(nick, date, text));
    }

    public void	clear(){

    }

    public Message	getMessage(int pos){
     return null;
    }

    public int getSize(){
        return 0;
    }

    class Message{
        private String nick, text;
        private Date date;

        public Message(String nick, Date date, String text) {
            this.nick = nick;
            this.date = date;
            this.text = text;
        }

        public Date	getDate(){
            return date;
        }

        public java.lang.String	getNick(){
            return nick;
        }

        public java.lang.String	getText(){
            return text;
        }
    }
}
