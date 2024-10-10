import java.io.*;
import java.util.*;

public class UtilisateurManager {
    private static Map<String, String> baseUtilisateurs = new HashMap<>();
    private static Map<String, String> imagesDeProfil = new HashMap<>();
    private static final String FICHIER_DONNEES_UTILISATEUR = "donnees_utilisateur.ser";

    @SuppressWarnings("unchecked")
    public static void chargerDonneesUtilisateurs() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FICHIER_DONNEES_UTILISATEUR))) {
            baseUtilisateurs = (Map<String, String>) in.readObject();
            imagesDeProfil = (Map<String, String>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Le fichier peut ne pas exister initialement, ignorer l'exception
        }
    }

    public static void sauvegarderDonneesUtilisateurs() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHIER_DONNEES_UTILISATEUR))) {
            out.writeObject(baseUtilisateurs);
            out.writeObject(imagesDeProfil);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifierConnexion(String utilisateur, String motDePasse) {
        return baseUtilisateurs.containsKey(utilisateur) && baseUtilisateurs.get(utilisateur).equals(motDePasse);
    }

    public static boolean utilisateurExiste(String utilisateur) {
        return baseUtilisateurs.containsKey(utilisateur);
    }
    public static void mettreAJourMotDePasse(String utilisateur, String nouveauMotDePasse) {
        if (utilisateurExiste(utilisateur)) {
            baseUtilisateurs.put(utilisateur, nouveauMotDePasse);
            sauvegarderDonneesUtilisateurs();
        } else {
            throw new IllegalArgumentException("L'utilisateur n'existe pas.");
        }
    }

    public static void enregistrerUtilisateur(String utilisateur, String motDePasse) {
        baseUtilisateurs.put(utilisateur, motDePasse);
        imagesDeProfil.put(utilisateur, "default.png");
        sauvegarderDonneesUtilisateurs();
    }

    public static String getImageProfil(String utilisateur) {
        return imagesDeProfil.get(utilisateur);
    }

    public static void mettreAJourImageProfil(String utilisateur, String cheminImage) {
        imagesDeProfil.put(utilisateur, cheminImage);
        sauvegarderDonneesUtilisateurs();
    }
}
