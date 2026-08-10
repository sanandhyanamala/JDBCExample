
import java.sql.*;

public class ComplaintSystem {

    // Database Connection
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/Complaint_system";
        String user = "root";
        String password = "Sanandh@123"; // replace with your MySQL password
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    // CREATE - Register Complaint
    public void registerComplaint(int userId, String description) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO complaints(user_id, description) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, description);
            ps.executeUpdate();
            System.out.println("Complaint registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - View Complaints
    public void viewComplaints() {
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM complaints";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("complaint_id") + " - " +
                                   rs.getString("description") + " - " +
                                   rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE - Assign Officer
    public void assignOfficer(int complaintId, int officerId) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE complaints SET officer_id=?, status='Assigned' WHERE complaint_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, officerId);
            ps.setInt(2, complaintId);
            ps.executeUpdate();
            System.out.println("Officer assigned successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE - Remove Complaint
    public void deleteComplaint(int complaintId) {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM complaints WHERE complaint_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, complaintId);
            ps.executeUpdate();
            System.out.println("Complaint deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Payroll Example - Update Status
    public void updatePayrollStatus(int payrollId, String status) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE payroll SET status=? WHERE payroll_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, payrollId);
            ps.executeUpdate();
            System.out.println("Payroll status updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}