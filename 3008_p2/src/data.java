import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

public class data {
    private String url = "C:\\3008/data.csv" ;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public void doIt(BufferedReader r, String id, String action,String account,String feedback,String passWord) throws IOException {
        Deque<String> s = new LinkedList<String>();
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            s.addLast(line);}
        Date date = new Date();
        String new_line = id + "," +  date + "," +  account +"," + action + "," +  feedback + "," + "new_scheme";
        s.addLast(new_line);
        File x = new File(url);
        PrintWriter w = new PrintWriter(new PrintWriter(x));
        for (String text : s) {
            w.println(text);
        }
        w.close();
    }
    public void insertNewData(String id, String action, String account,String feedback,String passWord){
        try {
            BufferedReader r = new BufferedReader(new FileReader(url));
            doIt(r,id,action,account,feedback,passWord);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }
    public String getId(){
        String output = null;
        try {
            BufferedReader r = new BufferedReader(new FileReader(url));
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                output = line;}
            if (output != null) output = output.substring(0,2);
            else return output;

        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
        if(output == null) return "0";
        else return output;
    }
}
