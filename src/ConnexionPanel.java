import javax.swing.*;
import java.awt.event.*;

public class ConnexionPanel {
    private JFrame cadre;
    private JTextField champUtilisateur;
    private JPasswordField champMotDePasse;

    public ConnexionPanel() {
        initialiser();
    }

    public JFrame getCadre() {
        return cadre;
    }

    private void initialiser() {
        cadre = new JFrame();
        cadre.setBounds(100, 100, 600, 600);
        cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadre.getContentPane().setLayout(null);

        JLabel lblUtilisateur = new JLabel("Nom d'utilisateur:");
        lblUtilisateur.setBounds(10, 10, 120, 25);
        cadre.getContentPane().add(lblUtilisateur);

        champUtilisateur = new JTextField();
        champUtilisateur.setBounds(140, 10, 200, 25);
        cadre.getContentPane().add(champUtilisateur);
        champUtilisateur.setColumns(10);

        JLabel lblMotDePasse = new JLabel("Mot de passe:");
        lblMotDePasse.setBounds(10, 45, 120, 25);
        cadre.getContentPane().add(lblMotDePasse);

        champMotDePasse = new JPasswordField();
        champMotDePasse.setBounds(140, 45, 200, 25);
        cadre.getContentPane().add(champMotDePasse);

        JButton btnConnexion = new JButton("Connexion");
        btnConnexion.setBounds(100, 80, 100, 25);
        cadre.getContentPane().add(btnConnexion);

        JButton btnEnregistrer = new JButton("Enregistrer");
        btnEnregistrer.setBounds(210, 80, 100, 25);
        cadre.getContentPane().add(btnEnregistrer);

        btnConnexion.addActionListener(e -> {
            String utilisateur = champUtilisateur.getText();
            String motDePasse = new String(champMotDePasse.getPassword());

            if (UtilisateurManager.verifierConnexion(utilisateur, motDePasse)) {
                JOptionPane.showMessageDialog(cadre, "Connexion réussie");
                ProfilPanel profilPanel = new ProfilPanel(utilisateur);
                profilPanel.afficher();
                cadre.dispose();
            } else {
                JOptionPane.showMessageDialog(cadre, "Nom d'utilisateur ou mot de passe invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEnregistrer.addActionListener(e -> {
            String utilisateur = champUtilisateur.getText();
            String motDePasse = new String(champMotDePasse.getPassword());

            if (utilisateur.isEmpty() || motDePasse.isEmpty()) {
                JOptionPane.showMessageDialog(cadre, "Le nom d'utilisateur ou le mot de passe ne peut pas être vide", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else if (UtilisateurManager.utilisateurExiste(utilisateur)) {
                JOptionPane.showMessageDialog(cadre, "Le nom d'utilisateur existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                UtilisateurManager.enregistrerUtilisateur(utilisateur, motDePasse);
                JOptionPane.showMessageDialog(cadre, "Enregistrement réussi");
            }
        });
    }
}
