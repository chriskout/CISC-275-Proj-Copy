import java.io.*;

public class SerializeHelper {
    static final String FISH_FILE = "src/output/fish_object.txt";
    static final String MATCHING_FILE = "src/output/matching_object.txt";

    static void write(String fileName, Object model){
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(model);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static Object  read(String fileName){
        try{
            FileInputStream fileInputStream
                    = new FileInputStream(fileName);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            Object result =  objectInputStream.readObject();
            objectInputStream.close();
            return result;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
