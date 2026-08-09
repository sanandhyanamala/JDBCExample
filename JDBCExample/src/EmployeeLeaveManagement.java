import java.sql.*;
import java.util.Scanner;

public class EmployeeLeaveManagement {

    // Database Connection
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/ems"; 
        String user = "root";
        String pass = "Sanandh@123";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    // Employee Registration
    public static void registerEmployee(int id, String name, String dept, int balance) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setInt(4, balance);
            ps.executeUpdate();
            System.out.println("✅ Employee Registered Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Apply Leave
    public static void applyLeave(int leaveId, int empId, String type, String start, String end) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO LeaveMaster VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, leaveId);
            ps.setInt(2, empId);
            ps.setString(3, type);
            ps.setString(4, start);
            ps.setString(5, end);
            ps.setString(6, "Pending");
            ps.executeUpdate();
            System.out.println("✅ Leave Applied Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Approve Leave
    public static void approveLeave(int leaveId) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE LeaveMaster SET status=? WHERE leave_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Approved");
            ps.setInt(2, leaveId);
            ps.executeUpdate();
            System.out.println("✅ Leave Approved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check Leave Balance
    public static void checkLeaveBalance(int empId) {
        try (Connection con = getConnection()) {
            String sql = "SELECT leave_balance FROM Employee WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("📊 Leave Balance: " + rs.getInt("leave_balance"));
            } else {
                System.out.println("❌ Employee not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Employee Leave Management System ===");
            System.out.println("1. Register Employee");
            System.out.println("2. Apply Leave");
            System.out.println("3. Approve Leave");
            System.out.println("4. Check Leave Balance");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Leave Balance: ");
                    int balance = sc.nextInt();
                    registerEmployee(id, name, dept, balance);
                    break;

                case 2:
                    System.out.print("Enter Leave ID: ");
                    int leaveId = sc.nextInt();
                    System.out.print("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Leave Type: ");
                    String type = sc.nextLine();
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String start = sc.nextLine();
                    System.out.print("Enter End Date (YYYY-MM-DD): ");
                    String end = sc.nextLine();
                    applyLeave(leaveId, empId, type, start, end);
                    break;

                case 3:
                    System.out.print("Enter Leave ID to Approve: ");
                    int approveId = sc.nextInt();
                    approveLeave(approveId);
                    break;

                case 4:
                    System.out.print("Enter Employee ID: ");
                    int empCheck = sc.nextInt();
                    checkLeaveBalance(empCheck);
                    break;

                case 5:
                    System.out.println("👋 Exiting System...");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Invalid Choice!");
            }
        }
    }
}