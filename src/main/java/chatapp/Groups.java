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
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Groups extends JFrame {
    static JLabel display;
    static String[] oldAr = new String[0];
    static JButton enter;
    static JButton enter1;
    static JComboBox<String> c1;

    Groups(String getname) throws IOException {
        JFrame jf = new JFrame("Group management window");
        JPanel jp = new JPanel();
        Font def = new Font("Agency FB", Font.BOLD, 18);
        JLabel label = new JLabel("<html><font color='white'>Enter </font><font color='green'>Group</font></html>");
        label.setFont(def);
        jp.add(label);
        JButton newGroup = new JButton("");
        final JTextField groupname = new JTextField("Group-Name");
        groupname.setFont(def);
        groupname.setBackground(Color.blue);
        groupname.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (groupname.getText().equals("Group-Name")) {
                    groupname.setText("");
                }

            }
        });
        jp.add(groupname);
        newGroup.setText("<html> <font color='blue'>Create your new</font><font color='green'> Group </font></html>");
        newGroup.setBackground(Color.black);
        newGroup.addActionListener((clicked) -> {
            String groupName = groupname.getText();

            try {
                DataExtractor.createGuild(groupName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InterruptedException ex;
            String reason;
            int retryAfter;
            if (DataExtractor.getLast_status_code() == 406) {
                reason = DataExtractor.getLast_request_response().getReason();

                try {
                    this.showAlert("\tALERT : " + reason, 5000);
                } catch (InterruptedException var11) {
                    ex = var11;
                    ex.printStackTrace();
                }
            } else if (DataExtractor.getLast_status_code() == 409) {
                reason = DataExtractor.getLast_request_response().getReason();

                try {
                    this.showAlert("\tALERT : " + reason, 5000);
                } catch (InterruptedException var10) {
                    ex = var10;
                    ex.printStackTrace();
                }
            } else if (DataExtractor.getLast_status_code() == 429) {
                retryAfter = (int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds"));

                try {
                    this.showAlert("\tALERT : You are being rate limited, retry after " + retryAfter + "s.", 5000);
                } catch (InterruptedException var9) {
                    ex = var9;
                    ex.printStackTrace();
                }
            }

            try {
                oldAr = DataExtractor.getUserGuildInfo().getGuilds();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (DataExtractor.getLast_status_code() == 429) {
                retryAfter = (int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds"));

                try {
                    this.showAlert("\tALERT : You are being rate limited, retry after " + retryAfter + "s.", 5000);
                } catch (InterruptedException var6) {
                    ex = var6;
                    ex.printStackTrace();
                }

                int finalRetryAfter = retryAfter;
                CompletableFuture.runAsync(() -> {
                    InterruptedException e1;
                    try {
                        Thread.sleep(finalRetryAfter + 1);
                    } catch (InterruptedException var6) {
                        e1 = var6;
                        e1.printStackTrace();
                    }

                    try {
                        oldAr = DataExtractor.getUserGuildInfo().getGuilds();
                    } catch (IOException var5) {
                        var5.printStackTrace();
                    }

                    for(int index = 0; index < oldAr.length; ++index) {
                        oldAr[index] = this.splitAndFormat(oldAr[index]);
                    }

                    c1.setModel((new JComboBox(oldAr)).getModel());

                    try {
                        this.showAlert(String.format("\t(OLD) Successfully added the group %s!", groupName), "green", 5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });
            } else {
                for(retryAfter = 0; retryAfter < oldAr.length; ++retryAfter) {
                    oldAr[retryAfter] = this.splitAndFormat(oldAr[retryAfter]);
                }

                c1.setModel((new JComboBox(oldAr)).getModel());

                try {
                    this.showAlert(String.format("\tSuccessfully added the group %s!", groupName), "green", 5000);
                } catch (InterruptedException var7) {
                    var7.printStackTrace();
                }

            }
        });
        jp.add(newGroup);
        JLabel label1 = new JLabel("<html><font color='white'>Select a new </font><font color='green'>Group</font></html>");
        label1.setFont(def);
        jp.add(label1);

        int retryAfter;
        try {
            oldAr = DataExtractor.getUserGuildInfo().getGuilds();

            for(retryAfter = 0; retryAfter < oldAr.length; ++retryAfter) {
                oldAr[retryAfter] = this.splitAndFormat(oldAr[retryAfter]);
            }
        } catch (Exception var13) {
        }

        if (DataExtractor.getLast_status_code() == 429) {
            retryAfter = (int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds"));

            try {
                this.showAlert("\tALERT : You are being rate limited, retry after " + retryAfter + "s.", 5000);
            } catch (InterruptedException var12) {
                InterruptedException e = var12;
                e.printStackTrace();
            }

        } else {
            c1 = new JComboBox(oldAr);
            c1.addActionListener((clicked) -> {
                String sel = (String)c1.getSelectedItem();
                String btnText = String.format("<html> <font color='blue'>View </font><font color='green'>%s</font></html>", sel);
                enter.setText(btnText);

                try {
                    enter1.setEnabled(DataExtractor.checkAdmin(sel).getAdmin());
                } catch (IOException var4) {
                    IOException e = var4;
                    e.printStackTrace();
                }

            });
            c1.setForeground(Color.white);
            c1.setFont(def);
            c1.setBackground(Color.black);
            jp.add(c1);
            enter = new JButton("<html> <font color='blue'>View your </font><font color='green'> Group </font></html>");
            enter.setFont(def);
            enter.setBackground(Color.black);
            enter.addActionListener((clicked) -> {
                Object selItem = c1.getSelectedItem();
                if (selItem == null) {
                    try {
                        this.showAlert("\tALERT : Create a new group, you have no groups!", 5000);
                    } catch (InterruptedException var7) {
                        InterruptedException e1x = var7;
                        e1x.printStackTrace();
                    }

                } else {
                    String sel = (String)selItem;

                    try {
                        this.showAlert(sel + " has been selected!", "green", 5000);
                        jf.setVisible(false);
                        new Group(this.splitAndUnformat(sel), getname);
                    } catch (IOException | InterruptedException var8) {
                        Exception e1 = var8;
                        e1.printStackTrace();
                    }

                }
            });
            jp.add(enter);
            enter1 = new JButton("<html> <font color='blue'>Manage your </font><font color='green'> Group </font></html>");
            enter1.setFont(def);
            enter1.setBackground(Color.black);
            enter1.addActionListener((clicked) -> {
                Object selItem = c1.getSelectedItem();
                if (selItem == null) {
                    try {
                        this.showAlert("\tALERT : Create a new group, you have no groups!", 5000);
                    } catch (InterruptedException var5) {
                        InterruptedException e1 = var5;
                        e1.printStackTrace();
                    }

                } else {
                    new GroupManagement(this.splitAndUnformat((String)selItem), getname);
                }
            });
            jp.add(enter1);
            display = new JLabel("<html> <font color='green'></font></html>");
            display.setFont(def);
            jp.add(display);
            jp.setBackground(Color.black);
            jp.setSize(900, 900);
            jp.setLayout(new GridLayout(10, 1, 0, 25));
            jf.add(jp);
            jf.setSize(900, 900);
            jf.setDefaultCloseOperation(3);
            jf.setVisible(true);
        }
    }

    private String splitAndUnformat(String text) {
        StringTokenizer sz = new StringTokenizer(text, " ");
        StringBuilder sb = new StringBuilder();

        for(boolean firstWord = true; sz.hasMoreTokens(); firstWord = false) {
            String currentToken = sz.nextToken();
            if (firstWord) {
                sb.append(currentToken.toLowerCase());
            } else {
                sb.append("_" + currentToken.toLowerCase());
            }
        }

        return sb.toString();
    }

    private String splitAndFormat(String text) {
        StringTokenizer sz = new StringTokenizer(text, "_");
        StringBuilder sb = new StringBuilder();

        for(boolean firstWord = true; sz.hasMoreTokens(); firstWord = false) {
            String currentToken = sz.nextToken();
            char var10001;
            if (firstWord) {
                var10001 = Character.toUpperCase(currentToken.charAt(0));
                sb.append("" + var10001 + currentToken.substring(1).toLowerCase());
            } else {
                var10001 = Character.toUpperCase(currentToken.charAt(0));
                sb.append(" " + var10001 + currentToken.substring(1).toLowerCase());
            }
        }

        return sb.toString();
    }

    private void showAlert(String text, String color, int delay) throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            display.setText(this.setColor(color, text));

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var5) {
                InterruptedException e = var5;
                e.printStackTrace();
            }

            display.setText("");
        });
    }

    private void showAlert(String text, int delay) throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            display.setText(this.setColor("red", text));

            try {
                Thread.sleep((long)delay);
            } catch (InterruptedException var4) {
                InterruptedException e = var4;
                e.printStackTrace();
            }

            display.setText("");
        });
    }

    private String setColor(String color, String text) {
        return String.format("<html> <font color='%s'>%s</font></html>", color, text);
    }
}
