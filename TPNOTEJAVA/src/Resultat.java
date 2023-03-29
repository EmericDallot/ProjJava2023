import javax.swing.*;
import java.awt.*;

public class Resultat extends JFrame {

    // n Nombre de billes
    // k Nombre de boites
    private int n, k;
    private int[] boites;
    private String strategie;
    private boolean affichageChiffres;

    public Resultat() {
        super("Placement de billes dans les boîtes");
        String nStr = JOptionPane.showInputDialog(null, "Entrez le nombre de billes :", "Nombre de billes", JOptionPane.QUESTION_MESSAGE);
        n = Integer.parseInt(nStr);
        String kStr = JOptionPane.showInputDialog(null, "Entrez le nombre de boîtes :", "Nombre de boîtes", JOptionPane.QUESTION_MESSAGE);
        k = Integer.parseInt(kStr);

        boites = new int[k];
        strategie = "";
        affichageChiffres = true;

        // Menu choix stratégie
        String[] strategieChoix = {"Chaînage", "Double choix", "Adressage ouvert linéaire", "Adressage ouvert quadratique"};
        strategie = (String) JOptionPane.showInputDialog(null, "Choisissez une stratégie :", "Stratégie", JOptionPane.QUESTION_MESSAGE, null, strategieChoix, strategieChoix[0]);

        // Menu choix affichage
        String[] affichageChoix = {"Chiffres", "Graphique"};
        String affichageStr = (String) JOptionPane.showInputDialog(null, "Choisissez un mode d'affichage :", "Affichage", JOptionPane.QUESTION_MESSAGE, null, affichageChoix, affichageChoix[0]);
        if (affichageStr.equals("Graphique")) {
            affichageChiffres = false;
        }

        switch (strategie) {
            case "Chaînage":
                strategieChainage();
                break;
            case "Double choix":
                strategieDoubleChoix();
                break;
            case "Adressage ouvert linéaire":
                strategieAdressageOuvertLineaire();
                break;
            case "Adressage ouvert quadratique":
                strategieAdressageOuvertQuadratique();
                break;
        }

        // Affichage
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout((k/5), k));


        for (int i = 0; i < k; i++) {
            JLabel label = new JLabel();
            if (affichageChiffres) {
                label.setText(String.valueOf(boites[i]));
            } else {
                label.setText(getBilleGraphique(boites[i]) + " " + String.valueOf(boites[i]));
                label.setFont(new Font("Monospaced", Font.PLAIN, 30));
            }
            panel.add(label);
        }

        if( strategie.equals("Chaînage")|| strategie.equals("Double choix") ) {

            JLabel label3 = new JLabel();
            int max = boites[0];
            int laboitemax =0;
            JLabel label2 = new JLabel();
            for (int i = 1; i < boites.length; i++) {
                if (boites[i] > max) {
                    max = boites[i];
                    laboitemax = i;
                }
            }
            label3.setText("boite max est :"+ (laboitemax+1));
            label3.setFont(new Font("Monospaced", Font.PLAIN, 15));
            panel.add(label3);
            label2.setText("valeur max : "+ max );
            label2.setFont(new Font("Monospaced", Font.PLAIN, 15));
            panel.add(label2);


        }
        add(panel);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Resultat();
    }

    private void strategieChainage() {
        for (int i = 0; i < n; i++) {
            int boite = (int) (Math.random() * k);
            boites[boite]++;
        }
    }
    private void strategieDoubleChoix() {
        for (int i = 0; i < n; i++) {
            int boite1 = (int) (Math.random() * k);
            int boite2 = (int) (Math.random() * k);
            if (boites[boite1] < boites[boite2]) {
                boites[boite1]++;
            } else {
                boites[boite2]++;
            }
        }
    }

    private void strategieAdressageOuvertLineaire() {
        int boite = 0;
        int maxBoitesExaminees = 0;
        for (int i = 0; i < n; i++) {
            if (boites[boite] == 0) {
                boites[boite] = 1;
            } else {
                boite = (boite + 1) % k;
                i--;
                maxBoitesExaminees++;
            }
        }
        System.out.println("Nombre maximum de boîtes examinées : " + maxBoitesExaminees);
    }
    private void strategieAdressageOuvertQuadratique() {
        int boite = 0;
        int maxBoitesExaminees = 0;
        for (int i = 0; i < n; i++) {
            if (boites[boite] == 0) {
                boites[boite] = 1;
            } else {
                boite = (boite + (int) Math.pow(i + 1, 2)) % k;
                i--;
                maxBoitesExaminees++;
            }
        }
        System.out.println("Nombre maximum de boîtes examinées : " + maxBoitesExaminees);
    }

    // Méthode pour obtenir la représentation graphique d'une bille
    private String getBilleGraphique(int nombreBilles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nombreBilles; i++) {
            sb.append("•");
        }
        return sb.toString();
    }


}
