import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;

/**
 * Created by Александр on 09.09.2015.
 */
public class ContactsFile {
    static final int ONE_CONTACT_LENGTH = 60;
    private RandomAccessFile raf = null;

    public ContactsFile(String path) {
        try {
            raf = new RandomAccessFile(path, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public long length() throws IOException {
        return raf.length();
    }

    public void addContact(String nick, String address) throws IOException {
        raf.seek(length());

        StringBuilder pattern = new StringBuilder("               ");
        pattern.replace(0, nick.length(), nick);
        raf.writeChars(String.valueOf(pattern));

        pattern = new StringBuilder("               ");
        pattern.replace(0, address.length(), address);
        raf.writeChars(String.valueOf(pattern));

        System.out.println(length());
    }

    public void deleteContact(int pos) throws IOException, ParseException {
        raf.seek((pos + 1)*ONE_CONTACT_LENGTH);
        String str = "";
        //System.out.println(length() - raf.getFilePointer());
        int tmp = (int) (length() - raf.getFilePointer());

        for (int i = 0; i < tmp/2; i++)
            str += new String(String.valueOf(raf.readChar()).getBytes(Constants.CHARSET_NAME), Constants.CHARSET_NAME);

        System.out.println(str);
        raf.seek(pos*ONE_CONTACT_LENGTH);
        raf.writeChars(String.valueOf(str));
        raf.setLength(length() - ONE_CONTACT_LENGTH);

        System.out.println("Contact was deleted successfully");    //POP UP!!!
    }

    public String[] getContacts() throws IOException, ParseException {
        String [] str = new String[(int) (length()/ONE_CONTACT_LENGTH)];
        raf.seek(0);

        for (int i = 0; i < length()/ONE_CONTACT_LENGTH; i++){
            str[i] = "";
            for (int k = 0; k < Constants.NICK_AND_IP_MAX_LENGTH*2; k++)
                str[i] += (new String(String.valueOf(raf.readChar()).getBytes(Constants.CHARSET_NAME), Constants.CHARSET_NAME));
        }

        return str;
    }

    public void close(){
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
