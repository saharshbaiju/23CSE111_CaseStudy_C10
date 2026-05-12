# Online Car Rental Management System

A console-based Java application that simulates an online car rental platform, allowing customers to browse, book, and manage car rentals — and admins to manage the fleet and bookings.

> **Course:** 23CSE111 Object Oriented Programming — S2 B.Tech CSE  
> **Institution:** Amrita School of Computing, Amritapuri Campus  
> **Group No:** 10

---

## Team Members

| Roll No | Name |
|---|---|
| AM.SC.U4CSE25212 | Dev Narayan U |
| AM.SC.U4CSE25245 | Saharsh Baiju |
| AM.SC.U4CSE25227 | Kashyap S |
| AM.SC.U4CSE25244 | Rishik Raj P |

---


## Overview

Traditional car rental businesses rely on phone calls and paper logs, leading to double bookings, outdated availability information, and billing confusion. This system provides a centralized digital platform to manage vehicles, customers, and rental transactions — all through a simple command-line interface.

---

## Features

### Customer
- Register and log in with a username and password
- View cars available for a specific date range
- Book a car by selecting dates and estimated kilometers
- Pay via an in-app wallet
- View personal booking history
- Cancel an active booking
- Top up wallet balance

### Admin
- Log in with a special admin access code
- Add new cars to the fleet (name, brand, price per km)
- View all cars with their current availability status
- View all bookings across all customers

---

## 📁 Project Structure

```
CarRentalSystem/
│
├── CarRentalSystem.java   # Main entry point; handles menus and user interaction
├── User.java              # User registration, login, role management, wallet
├── Car.java               # Car inventory, availability checking, status updates
├── Booking.java           # Booking creation, cancellation, booking history
│
├── User.csv               # Persistent user data (auto-generated)
├── Car.csv                # Persistent car data (auto-generated)
└── booking.csv            # Persistent booking data (auto-generated)
```

---

## Data Storage

All data is stored in plain CSV files in the project root directory. These files are created automatically on first use.

**User.csv** — `id, name, email, password, role, wallet_balance`

**Car.csv** — `id, name, brand, price_per_km, status, booking_start, booking_end`

**booking.csv** — `id, user_id, car_id, username, status, start_date, end_date`

> Dates are stored and entered in `yyyy-MM-dd` format.

---

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- A terminal / command prompt

### Compile

```bash
javac CarRentalSystem.java User.java Car.java Booking.java
```

### Run

```bash
java CarRentalSystem
```

---

## 🖥️ Usage

### First-Time Setup

On first launch, register as an **admin** to add cars to the system:

1. Select `[1] Register`
2. Enter your username, email, and password
3. When prompted, enter `y` for admin access
4. Use the admin code: *(provided separately by the system administrator)*
5. Log in and use `[1] Add Car` to populate the fleet

### Customer Flow

```
Register / Login
    └── View Available Cars (enter date range)
        └── Book a Car (enter Car ID + km)
            └── Confirm payment from wallet
                └── View / Cancel Bookings
```

### Admin Flow

```
Login (with admin role)
    ├── Add Car
    ├── View All Cars
    └── View All Bookings
```

---

## Class Design

### `User`
Manages all user-related operations.

| Member | Type | Description |
|---|---|---|
| `file_path` | `static String` | Path to `User.csv` |
| `register()` | method | Creates a new user account |
| `login()` | method | Authenticates credentials, returns user ID |
| `getRole()` | method | Returns the role (`admin` / `customer`) for a user ID |
| `getBalance()` | method | Fetches the current wallet balance |
| `updateBalance()` | method | Writes updated wallet balance to file |

### `Car`
Manages the car inventory and availability.

| Member | Type | Description |
|---|---|---|
| `file_path` | `static String` | Path to `Car.csv` |
| `addCar()` | method | Adds a new car entry |
| `viewAvailableCars()` | method | Lists cars not booked in a given date range |
| `updateStatus()` | method | Marks a car as `booked` or `available` |
| `viewAllCars()` | method | Displays the full fleet with status |

### `Booking`
Handles the lifecycle of a rental booking.

| Member | Type | Description |
|---|---|---|
| `file_path` | `static String` | Path to `booking.csv` |
| `bookCar()` | method | Creates a booking after checking availability and deducting wallet balance |
| `cancelBooking()` | method | Cancels an active booking and frees the car |
| `viewAllBookings()` | method | Admin view of all bookings |
| `viewUserBookings()` | method | Customer view of their own bookings |

### `CarRentalSystem`
The main driver class containing `main()`. Renders menus, handles user input, and delegates to the appropriate classes.

---
