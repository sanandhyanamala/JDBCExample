import java.sql.*;

public class HotelBooking {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotel_db";
        String user = "root";
        String password = "Sanandh@123";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            // 1. Show available rooms
            String query = "SELECT * FROM rooms WHERE status='Available'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.println("Room ID: " + rs.getInt("room_id") +
                                   " Type: " + rs.getString("room_type") +
                                   " Price: " + rs.getDouble("price"));
            }

            // 2. Book a room
            String insert = "INSERT INTO bookings(customer_name, room_id, check_in, check_out) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setString(1, "Ravi Kumar");
            ps.setInt(2, 1); // room_id
            ps.setDate(3, Date.valueOf("2026-08-10"));
            ps.setDate(4, Date.valueOf("2026-08-12"));
            ps.executeUpdate();

            // 3. Update room status
            String update = "UPDATE rooms SET status='Booked' WHERE room_id=?";
            PreparedStatement ps2 = con.prepareStatement(update);
            ps2.setInt(1, 1);
            ps2.executeUpdate();

            System.out.println("Room booked successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}