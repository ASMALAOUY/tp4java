
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EXempleJdbc {

    void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    Connection newConnection() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/dbessai";
        Connection conn = DriverManager.getConnection(url, "root", "");
        return conn;
    }

    public void listPersons() throws SQLException {
        Connection conn = null;

        try {
            conn = newConnection();
            Statement st = conn.createStatement();

            String query = "SELECT nom, prenom, age FROM personne ORDER BY age";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.printf("%-20s | %-20s | %3d\n",
                    rs.getString(1), rs.getString("prenom"), rs.getInt(3));
            }

            rs.close();
            st.close();

        } finally {
            if (conn != null) conn.close();
        }
    }

    // CREATE : Ajouter une personne
    public void create(String nom, String prenom, int age) throws SQLException {
        Connection conn = newConnection();

        String sql = "INSERT INTO personne(nom, prenom, age) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setInt(3, age);

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    // UPDATE : Modifier une personne
    public void update(String nom, String prenom, int age) throws SQLException {
        Connection conn = newConnection();

        String sql = "UPDATE personne SET prenom=?, age=? WHERE nom=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, prenom);
        ps.setInt(2, age);
        ps.setString(3, nom);

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    // DELETE : Supprimer une personne
    public void delete(String nom) throws SQLException {
        Connection conn = newConnection();

        String sql = "DELETE FROM personne WHERE nom=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nom);

        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}
