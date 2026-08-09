import java.sql.*;

public class Payrollsystem {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/payroll_db";
        String user = "root";
        String password = "Sanandh@123";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            
            // 1. Insert Employee
            String insertEmp = "INSERT INTO employees(name, dept, basic_salary) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(insertEmp);
            ps.setString(1, "Ravi Kumar");
            ps.setString(2, "HR");
            ps.setDouble(3, 30000.00);
            ps.executeUpdate();

            // 2. Calculate Salary (Aggregate Example)
            double basic = 30000.00;
            double tax = basic * 0.10; // 10% tax
            double net = basic - tax;

            String insertPayroll = "INSERT INTO payroll(emp_id, month, gross_salary, tax, net_salary) VALUES(?,?,?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(insertPayroll);
            ps2.setInt(1, 1); // emp_id
            ps2.setString(2, "August");
            ps2.setDouble(3, basic);
            ps2.setDouble(4, tax);
            ps2.setDouble(5, net);
            ps2.executeUpdate();

            // 3. Show Payslip
            String query = "SELECT e.name, p.month, p.gross_salary, p.tax, p.net_salary " +
                           "FROM employees e JOIN payroll p ON e.emp_id = p.emp_id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println("Payslip for " + rs.getString("name"));
                System.out.println("Month: " + rs.getString("month"));
                System.out.println("Gross: " + rs.getDouble("gross_salary"));
                System.out.println("Tax: " + rs.getDouble("tax"));
                System.out.println("Net: " + rs.getDouble("net_salary"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}