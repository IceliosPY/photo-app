import java.awt.EventQueue;

public class Application {

    /**
     * Lancer l'application.
     */
    public static void main(String[] args) {
        UtilisateurManager.chargerDonneesUtilisateurs();
        EventQueue.invokeLater(() -> {
            try {
                ConnexionPanel connexionPanel = new ConnexionPanel();
                connexionPanel.getCadre().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
