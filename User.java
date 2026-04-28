import java.io.*;
import java.util.*;

public class User {
    static String file_path = "User.csv";

    public void register(String name,String email,String password,String role){
        try{
            FileWriter fw = new FileWriter(file_path,true);
            int id = getlastid()+1;
            fw.write(id + "," + name + "," + email + "," + password + "," + role + "\n");
            fw.close();
            System.out.println("Registered");
        }catch(Exception e){
            System.out.println(e);
        }
    }



    public int login(String email, String password) {
    try {
        File file = new File(file_path);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] d = sc.nextLine().split(",");
            if (d[2].equals(email) && d[3].equals(password)) {
                sc.close();
                return Integer.parseInt(d[0]);
            }
        }
        sc.close();
    } catch (Exception e) {
        System.out.println(e);
    }
    return -1;
    }


    public String getRole(int userId) {
    try {
        File file = new File(file_path);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] d = sc.nextLine().split(",");
            if (Integer.parseInt(d[0]) == userId) {
                sc.close();
                return d[4];
            }
        }
        sc.close();
    } catch (Exception e) {
        System.out.println(e);
    }
    return "";
    }


    private int getlastid(){
        int last = 0;
        try{
            
            File file = new File(file_path);
            Scanner read = new Scanner(file);
            while (read.hasNextLine()){
                String[] d = read.nextLine().split("");
                last = Integer.parseInt(d[0]);
            }
            read.close();
            

        }catch(Exception e){
            System.out.println("this is the first entry");
        }
        return last;
    }
}
