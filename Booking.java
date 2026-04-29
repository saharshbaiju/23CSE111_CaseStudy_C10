import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Booking {
    static String file_path = "booking.csv";
    
    public void bookCar(int userId, int carId,String start,String end,int km){
        try{
            LocalDate reqStart = LocalDate.parse(start);
            LocalDate reqEnd = LocalDate.parse(end);

            Scanner sc = new Scanner(new File(Car.file_path));
            boolean canBook = false;
            int pricePerKm = 0;

            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(",");
                if (Integer.parseInt(d[0]) == carId) {
                    pricePerKm = Integer.parseInt(d[3]);
                    String status = d[4];
                    
                    if (status.equals("available")) {
                        canBook = true;
                    } else {
                        LocalDate bookedStart = LocalDate.parse(d[5]);
                        LocalDate bookedEnd = LocalDate.parse(d[6]);

                        if (reqEnd.isBefore(bookedStart) || reqStart.isAfter(bookedEnd)) {
                            canBook = true;
                    }
                    }
                break;
                }
            }
            sc.close();
            if (!canBook) {
            System.out.println("Car not available for selected dates");
            return;
            }

            int totalCost = km*pricePerKm;

            System.out.println("Payement options:");
            System.out.println("[1] Wallet");
            System.out.println("Press 1 to confirm ");
            System.out.print("Any other number to cancel: ");
            Scanner input = new Scanner(System.in);
            String confirm = input.nextLine();

            if (confirm.equals("1")){
                User u = new User();
                int balance = u.getBalance(userId);
                if (balance < totalCost){
                    System.out.println("Oops, Insufficient balance!... please add balance to wallet.");
                    return;
                }
                u.updateBalance(userId, balance-totalCost);
                new Car().updateStatus(carId, "booked", start, end);
                System.out.println("Booking successful! Cost: " + totalCost);
            }else{
                return;
            }
            
            FileWriter fw = new FileWriter(file_path,true);
            int id = getLastId() + 1;
            fw.write(id + "," + userId + "," + carId + ",active," + start + "," + end + "\n");
            fw.close();

        }catch(IOException e){
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
    
    private int getLastId(){
        int last = 0;
        try{
            File f = new File(file_path);
            if (!f.exists()){
                return 0;
            }
            Scanner read = new Scanner(f);
            while(read.hasNextLine()){
                String line = read.nextLine();
                if(!line.isEmpty()){
                    String[] d = line.split(",");
                    last = Integer.parseInt(d[0]);
                }
            }
            read.close();
        }catch(Exception e){
            return 0;
        }
        return last;
    }

    public void cancelBooking(int bookingId){
        try{
            File inputFile = new File(file_path);
            File tempFile = new File("temp_cancel.csv");

            Scanner sc = new Scanner(new File(file_path));
            FileWriter fw = new FileWriter(tempFile);
            boolean found = false;

            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if (line.isEmpty()){
                    continue;
                }
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == bookingId){
                    if(data[3].equals("active")){
                        data[3] = "cancelled";

                        int carId = Integer.parseInt(data[2]);
                        new Car().updateStatus(carId,"available","---","---");
                        line = String.join(",",data);
                        found = true;
                    }else{
                        System.out.println("Booking cannot be cancelled (already " + data[3] + ").");
                    }

                }
                fw.write(line + "\n");
            }
            sc.close();
            fw.close();

            if (found){
                inputFile.delete();
                tempFile.renameTo(inputFile);
            }else {
                tempFile.delete(); 
                System.out.println("Booking ID not found.");
            }
            
        }catch(Exception e){
            System.out.println("Error during cancellation: "+ e.getMessage());
        }
    }

    public void viewAllBookings() {
        try {
            File file = new File(file_path);
            if (!file.exists()) {
                System.out.println("No bookings found.");
                return;
            }
            Scanner read = new Scanner(file);
            System.out.println("\nBID | UID | CID | Status | Start | End");
            System.out.println("-------------------------");
            while (read.hasNextLine()) {
                String line = read.nextLine();
                if (line.isEmpty()){
                     continue;
                }
                System.out.println(line.replace(",", " | "));
            }
            read.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
}
}   
