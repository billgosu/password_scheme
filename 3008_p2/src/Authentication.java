import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class Authentication {
    private ArrayList<String> list = new ArrayList<String>();

    public String generateNonPicturePassWord (){
        Random random = new Random();
        byte[] array = new byte[256];
        random.nextBytes(array);
        StringBuffer r = new StringBuffer();
        String randomString = new String(array, Charset.forName("UTF-8"));
        int n = 5;
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }
        return r.toString();
    }

    public String generatePicturePassWord (){
        ArrayList list = new ArrayList<Integer>();
        while (true){
            int num = (int)(Math.random()* 20);
            if(!list.contains(num) && list.size() < 5 && num != 0) list.add(num);
            if(list.size() == 5) break;
        }
        String output = "";
        for(int i =0; i < 5; i++) {
            if((int)list.get(i) < 10) output += "0" + list.get(i).toString();
            else
                output += list.get(i).toString();}
        return output;
    }

    public String generateSoundPassWord(){
        list = new ArrayList<String>();
        list.add("catt");list.add("dolp");list.add("hors");list.add("lamb");list.add("lion");
        int random = (int) (Math.random()*5);
        return list.get(random);
    }
    public ArrayList<String> getAnimals() {return  list;}

}
