import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProfilPanel {
    private JFrame cadre;
    private String utilisateur;

    public ProfilPanel(String utilisateur) {
        this.utilisateur = utilisateur;
        initialiser();
    }

    public void afficher() {
        cadre.setVisible(true);
    }

    private void initialiser() {
        cadre = new JFrame();
        cadre.setBounds(100, 100, 600, 600);
        cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadre.getContentPane().setLayout(new BorderLayout());

        JPanel panneauHaut = new JPanel();
        panneauHaut.setLayout(new FlowLayout(FlowLayout.LEFT));
        panneauHaut.setPreferredSize(new Dimension(600, 60));
        JLabel lblBienvenue = new JLabel("Bienvenue, " + utilisateur + "!");
        lblBienvenue.setFont(new Font("Arial", Font.BOLD, 20));
        panneauHaut.add(lblBienvenue);
        cadre.getContentPane().add(panneauHaut, BorderLayout.NORTH);

        JPanel panneauCentre = new JPanel();
        panneauCentre.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblImageProfil = new JLabel("Image de profil:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panneauCentre.add(lblImageProfil, gbc);

        String cheminImageProfil = UtilisateurManager.getImageProfil(utilisateur);
        JLabel labelImage = new JLabel(new ImageIcon(cheminImageProfil != null ? cheminImageProfil : "default.png"));
        gbc.gridx = 1;
        panneauCentre.add(labelImage, gbc);

        JButton btnChangerImage = new JButton("Changer l'image");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panneauCentre.add(btnChangerImage, gbc);

        btnChangerImage.addActionListener(e -> {
            JFileChooser selecteurFichier = new JFileChooser();
            int resultat = selecteurFichier.showOpenDialog(cadre);
            if (resultat == JFileChooser.APPROVE_OPTION) {
                String cheminFichierSelectionne = selecteurFichier.getSelectedFile().getAbsolutePath();
                UtilisateurManager.mettreAJourImageProfil(utilisateur, cheminFichierSelectionne);
                JOptionPane.showMessageDialog(cadre, "Image de profil mise à jour avec succès");
                labelImage.setIcon(new ImageIcon(cheminFichierSelectionne));
            }
        });

        // Déclaration et ajout du bouton "Changer le mot de passe"
        JButton btnChangerMotDePasse = new JButton("Changer le mot de passe");
        gbc.gridy = 2;
        panneauCentre.add(btnChangerMotDePasse, gbc);

        // Ajout de l'action au bouton
        btnChangerMotDePasse.addActionListener(e -> {
            JFrame fenetreMotDePasse = new JFrame("Changer le mot de passe");
            fenetreMotDePasse.setSize(400, 200);
            fenetreMotDePasse.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fenetreMotDePasse.setLayout(new GridBagLayout());
            GridBagConstraints gbcFenetre = new GridBagConstraints();
            gbcFenetre.insets = new Insets(10, 10, 10, 10);
            gbcFenetre.anchor = GridBagConstraints.CENTER;

            JLabel lblNouveauMotDePasse = new JLabel("Nouveau mot de passe:");
            gbcFenetre.gridx = 0;
            gbcFenetre.gridy = 0;
            fenetreMotDePasse.add(lblNouveauMotDePasse, gbcFenetre);

            JPasswordField champNouveauMotDePasse = new JPasswordField(15);
            gbcFenetre.gridx = 1;
            fenetreMotDePasse.add(champNouveauMotDePasse, gbcFenetre);

            JButton btnConfirmer = new JButton("Confirmer");
            gbcFenetre.gridx = 0;
            gbcFenetre.gridy = 1;
            gbcFenetre.gridwidth = 2;
            fenetreMotDePasse.add(btnConfirmer, gbcFenetre);

            btnConfirmer.addActionListener(e1 -> {
                String nouveauMotDePasse = new String(champNouveauMotDePasse.getPassword());
                if (nouveauMotDePasse.isEmpty()) {
                    JOptionPane.showMessageDialog(fenetreMotDePasse, "Le nouveau mot de passe ne peut pas être vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else if (nouveauMotDePasse.length() < 8) {
                    JOptionPane.showMessageDialog(fenetreMotDePasse, "Le mot de passe doit contenir au moins 8 caractères", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("Mise à jour du mot de passe pour l'utilisateur : " + utilisateur);
                    UtilisateurManager.mettreAJourMotDePasse(utilisateur, nouveauMotDePasse);
                    System.out.println("Mot de passe mis à jour avec succès");
                    JOptionPane.showMessageDialog(fenetreMotDePasse, "Mot de passe mis à jour avec succès");
                    fenetreMotDePasse.dispose();
                }
            });

            fenetreMotDePasse.setVisible(true);
        });

        cadre.getContentPane().add(panneauCentre, BorderLayout.CENTER);

        JPanel panneauBas = new JPanel();
        panneauBas.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton btnDeconnexion = new JButton("Déconnexion");
        panneauBas.add(btnDeconnexion);
        cadre.getContentPane().add(panneauBas, BorderLayout.SOUTH);

        btnDeconnexion.addActionListener(e -> {
            cadre.dispose();
            new ConnexionPanel().getCadre().setVisible(true);
        });
    }
}
