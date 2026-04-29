import java.io.*;
import java.util.*;
import java.time.LocalDate;
public class Car {
    static String file_path = "Car.csv";

    private int getLastId(){
        int last = 0;
        try{
            File file = new File(file_path);
            Scanner read = new Scanner(file);
            while (read.hasNextLine()){
                String[] d = read.nextLine().split(",");
                last = Integer.parseInt(d[0]);
            }
            read.close();
            
        }catch(Exception e){
            System.out.println("this is the first entry");
        }
        return last;
    }
    
    public void addCar(String name,String brand,int price) {
        try{

        
        FileWriter fw= new FileWriter(file_path,true);
        int id = getLastId() +1;
        fw.write(id + "," + name +","+brand+","+price+","+"available\n");
        fw.close();
    }catch(IOException e){
        System.out.println(e.getMessage());
    }
    }

    public void viewAvailableCars(String start, String end){
        try{
            LocalDate Start = LocalDate.parse(start);
            LocalDate End = LocalDate.parse(end);

            File file = new File(file_path);
            Scanner read = new Scanner(file);

            while (read.hasNextLine()){
                String[] d = read.nextLine().split(",");

                int carId = Integer.parseInt(d[0]);
                String status = d[4];

                if (d[4].equals("available")){
                    System.out.println(Arrays.toString(d));
                    continue;
                }else{
                    LocalDate bookedStart = LocalDate.parse(d[5]);
                    LocalDate bookedEnd = LocalDate.parse(d[6]);

                    if (End.isBefore(bookedStart)||Start.isAfter(bookedEnd)){
                        System.out.println(Arrays.toString(d));
                    }

                }

                
            
            }
            read.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void updateStatus(int carId,String status, String start, String end){
        try{
            File temp = new File("temp.csv");
            FileWriter fw = new FileWriter(temp);
            

            Scanner sc = new Scanner(new File(file_path));
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String[] data = line.split(",");

                if (Integer.parseInt(data[0]) == carId){
                    data[4] = status;

                    if (status.equals("booked")) {
                        data[5] = start;
                        data[6] = end;
                    } else {
                        data[5] = "---";
                        data[6] = "---";
                    }
                    line = String.join(",",data);
                }
                fw.write(line+"\n");
            }

            sc.close();
            fw.close();

            File f = new File(file_path);
            f.delete();
            temp.renameTo(new File("Car.csv"));


        }catch(IOException e){
            System.out.println(e.getMessage());
            
        }
    }

    public void viewAllCars(){
        try{
            File file = new File(file_path);
            if(!file.exists()){
                System.out.println("No cars found in the database");
            }
            Scanner read = new Scanner(file);
            System.out.println("\nID | Name | Brand | Price/km | Status | Start | End");
            System.out.println("---------------------------------------------------------");
            while(read.hasNextLine()){
                String line = read.nextLine();
                if(line.isEmpty()){
                    continue;
                }
                System.out.println(line.replace(",","|"));
            }
            read.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
