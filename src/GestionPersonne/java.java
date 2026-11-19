
package GestionPersonne;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
class GestionPersonne extends JFrame {

    JTextField txtNom, txtPrenom, txtAge;
    Connection conn;

    public GestionPersonne() {

        setTitle("Formulaire");
        setSize(300, 170);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Connexion à la base de données
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbessai",
                "root", ""
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        // Création du formulaire
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Nom:"));
        txtNom = new JTextField();
        add(txtNom);

        add(new JLabel("Prenom:"));
        txtPrenom = new JTextField();
        add(txtPrenom);

        add(new JLabel("Age:"));
        txtAge = new JTextField();
        add(txtAge);

        // Boutons
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnAfficher = new JButton("Afficher");
        JButton btnModifier = new JButton("Mise à Jour");
        JButton btnSupprimer = new JButton("Supprimer");

        add(btnAjouter);
        add(btnAfficher);
        add(btnModifier);
        add(btnSupprimer);

        // Actions des boutons
        btnAjouter.addActionListener(e -> ajouter());
        btnAfficher.addActionListener(e -> afficher());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());

        setVisible(true);
    }

    // AJOUTER
    public void ajouter() {
        try {
            String sql = "INSERT INTO personne(nom, prenom, age) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, txtNom.getText());
            ps.setString(2, txtPrenom.getText());
            ps.setInt(3, Integer.parseInt(txtAge.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Ajout réussi !");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // AFFICHER

public void afficher() {
    try {
        String sql = "SELECT * FROM personne";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        // Création du tableau
        String[] colonnes = {"Nom", "Prenom", "Age"};

        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        while (rs.next()) {
            Object[] ligne = {
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getInt("age")
            };

            model.addRow(ligne);
        }

        JTable table = new JTable(model);

        // Nouvelle fenêtre
        JFrame fenetre = new JFrame("Liste des personnes");

        fenetre.add(new JScrollPane(table));

        fenetre.setSize(400, 300);
        fenetre.setLocationRelativeTo(this);
        fenetre.setVisible(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}


    // MODIFIER
    public void modifier() {
        try {
            String sql = "UPDATE personne SET prenom=?, age=? WHERE nom=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, txtPrenom.getText());
            ps.setInt(2, Integer.parseInt(txtAge.getText()));
            ps.setString(3, txtNom.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Modification réussie !");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // SUPPRIMER
    public void supprimer() {
        try {
            String sql = "DELETE FROM personne WHERE nom=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, txtNom.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Suppression réussie !");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new GestionPersonne();
    }
}
