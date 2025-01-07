//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GroupManagement extends JFrame {
    JLabel display;

    GroupManagement(String groupName, String userName) {
        JFrame jf = new JFrame();
        JPanel jp = new JPanel();
        Font def = new Font("Agency FB", 1, 18);
        JLabel label = new JLabel("<html><font color='white'>Manage </font><font color='green'>" + groupName + "</font></html>");
        label.setFont(def);
        jp.add(label);
        final JTextField message = new JTextField("Participant name here");
        message.setFont(def);
        message.setBackground(Color.blue);
        message.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (message.getText().equals("Participant name here")) {
                    message.setText("");
                }

            }
        });
        jp.add(message);
        JButton enter = new JButton("<html> <font color='blue'>Remove</font><font color='green'> member</font></html>");
        enter.setEnabled(true);
        enter.setBackground(Color.black);
        enter.addActionListener((clicked) -> {
            try {
                DataExtractor.removeMember(groupName, message.getText().trim());
                this.showAlert(String.format("\tSuccessfully removed member %s from guild %s.", message.getText().trim(), groupName), "green", 5000);
            } catch (IOException var7) {
                try {
                    this.showAlert(String.format("\tALERT : Could not remove %s!", message.getText().trim()), "red", 5000);
                } catch (InterruptedException var6) {
                }
            } catch (InterruptedException var8) {
            }

        });
        jp.add(enter);
        JButton enter1 = new JButton("<html> <font color='blue'>Add</font><font color='green'> member</font></html>");
        enter1.setEnabled(true);
        enter1.setBackground(Color.black);
        enter1.addActionListener((clicked) -> {
            try {
                DataExtractor.addMember(groupName, message.getText().trim());
                this.showAlert(String.format("\tSuccessfully added member %s to guild %s.", message.getText().trim(), groupName), "green", 5000);
            } catch (IOException var7) {
                try {
                    this.showAlert(String.format("\tALERT : Could not add %s!", message.getText().trim()), "red", 5000);
                } catch (InterruptedException var6) {
                }
            } catch (InterruptedException var8) {
            }

        });
        jp.add(enter1);
        final JTextField message1 = new JTextField("Icon here");
        message1.setFont(def);
        message1.setBackground(Color.blue);
        message1.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (message1.getText().equals("Icon here")) {
                    message1.setText("");
                }

            }
        });
        jp.add(message1);
        JButton enter2 = new JButton("<html> <font color='blue'>Change</font><font color='green'> icon</font></html>");
        enter2.setEnabled(true);
        enter2.setBackground(Color.black);
        enter2.addActionListener((clicked) -> {
            try {
                this.showAlert("\tALERT : Feature coming soon in a future update!", "blue", 10000);
            } catch (InterruptedException var3) {
            }

        });
        jp.add(enter2);
        this.display = new JLabel("<html> <font color='green'></font></html>");
        this.display.setFont(def);
        this.display.setSize(this.display.getPreferredSize());
        jp.add(this.display);
        jp.setBackground(Color.black);
        jf.setTitle(" Log - in Chat Account ");
        jp.setSize(900, 900);
        jp.setLayout(new GridLayout(10, 1, 0, 25));
        jf.add(jp);
        jf.setSize(1000, 1000);
        jf.setDefaultCloseOperation(1);
        jf.setVisible(true);
    }

    private void showAlert(String text, String color, int delay) throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            this.display.setText(this.setColor(color, text));

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var5) {
                InterruptedException e = var5;
                e.printStackTrace();
            }

            this.display.setText("");
        });
    }

    private void showAlert(String text, int delay) {
        CompletableFuture.runAsync(() -> {
            this.display.setText(this.setColor("red", text));

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var4) {
                InterruptedException e = var4;
                e.printStackTrace();
            }

            this.display.setText("");
        });
    }

    private String setColor(String color, String text) {
        return String.format("<html> <font color='%s'>%s</font></html>", color, text);
    }
}
