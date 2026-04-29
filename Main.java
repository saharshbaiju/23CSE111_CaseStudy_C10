import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = new User();
        Car car = new Car();
        Booking b = new Booking();

        while (true){
            System.out.println("\n[1] Register\n[2] Login\n[3] Exit");
            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1){
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                System.out.print("[y] Have access to admin code \n[n] continue as customer \nEnter:");
                String admin_access = sc.nextLine();
                if (admin_access.equals("y")){
                    System.out.print("Enter admin code:");
                    String code = sc.nextLine();
                    if (code.equals("canttellyoupassword")){
                        user.register(name,email,pass,"admin"); 
                        System.out.print("Registration successfull, Registered as admin please login");

                    }else{
                        System.out.print("Admin code incorrect, Registration failed please try againn ...");
                        continue;
                    }          
                }else{
                    user.register(name,email,pass,"customer");
                    System.out.print("Registration successfull, please login ...");

                }
            }

            else if (ch == 2){
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();

                int uid = user.login(email,pass);

                if (uid == -1){
                    System.out.println("Invalid credentials!! please try again....");
                    continue;
                }

                String role = user.getRole(uid);
                // ADMIN
                if (role.equals("admin")){
                    while (true){
                        System.out.println("\n|---- \\\\\\\\ ADMIN MENU /////// ----|");
                        System.out.println("[1] Add Car\n[2] View all Bookings\n[3] View all cars\n[4] Logout");

                        int op = sc.nextInt();

                        if (op == 1){
                            sc.nextLine();
                            System.out.print("Car name: ");
                            String name = sc.nextLine();
                            System.out.print("Brand: ");
                            String brand = sc.nextLine();
                            System.out.print("Price per km: ");

                            int price = sc.nextInt();

                            car.addCar(name,brand,price);
                        }else if (op == 2) {
                            b.viewAllBookings();

                        }else if(op == 3){
                            car.viewAllCars();
                        }
                        else{
                            break;
                        }
                    }
                }
                // CUSTOMER
                else{
                    System.out.print("Welcome back..." );
                    while (true){
                        
                        System.out.println("\n|----  Services  ----|");
                        System.out.println("[1] Book a car\n[2] Cancel Booking\n[3] View available cars\n[4] Add balance to wallet\n[5] Exit\nEnter: ");
                        int choice = sc.nextInt();

                        switch(choice){
                            case 1: 

                                sc.nextLine();

                                System.out.print("Enter start date (yyyy-MM-dd): ");
                                String s = sc.nextLine();

                                System.out.print("Enter end date (yyyy-MM-dd): ");
                                String e = sc.nextLine();

                                System.out.println("\nAvailable Cars:");
                                car.viewAvailableCars(s, e);

                                System.out.print("\nEnter Car ID: ");
                                int carId = sc.nextInt();

                                System.out.print("Enter KM: ");
                                int km = sc.nextInt();

                                b.bookCar(uid, carId, s, e, km);

                                break;

                            case 2: 
                                System.out.print("Enter Booking ID: ");
                                int bid = sc.nextInt();
                                b.cancelBooking(bid);
                                break;

                            case 3:
                                sc.nextLine();
                                System.out.print("\nEnter journey start data in [yyyy-mm-dd] format: ");
                                String start = sc.nextLine();
                                System.out.print("\nEnter journey end data in [yyyy-mm-dd] format: ");
                                String end = sc.nextLine();
                                car.viewAvailableCars(start, end);
                                break;

                            case 4: 
                                System.out.print("Enter amount to add: ");
                                int amount = sc.nextInt();

                                int current = user.getBalance(uid);
                                user.updateBalance(uid, current + amount);

                                System.out.println("Balance updated! New balance: " + (current + amount));
                                break;

                            case 5: 
                                System.out.println("Logging out...");
                                break;


                            default:
                                System.out.println("Invalid choice!");
                            }
                        }



                    }
                }
            
            }
        }
    }


