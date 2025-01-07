//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class Group extends JFrame {
    private final String name;
    static JLabel display;
    static String[] ar;
    long startMessage = 0L;
    long endMessage = 5L;
    long lastMessageCount = 0L;
    final JScrollBar s;
    final JButton enter;
    private final Guild currentGroup;
    private boolean firstLoad = true;
    private boolean rateLimited = false;
    long retryAfter;
    long endingTime;
    String text;

    private String getNameFromEmail(String email) {
        int index = email.lastIndexOf(64);
        return index != -1 ? email.substring(0, email.lastIndexOf(64)) : email;
    }

    private void updateMessages() throws InterruptedException {
        if (!this.rateLimited) {
            try {
                ar = DataExtractor.getMessages(this.name).getMessages();
            } catch (Exception var15) {
                System.out.println(DataExtractor.getLast_status_code());
                int time = (int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds"));
                System.out.println(time);
                this.showTimerAlert(time);
                return;
            }

            this.lastMessageCount = (long)ar.length;
            this.s.setEnabled(true);
            StringBuilder dText = new StringBuilder();
            long count = 0L;
            String[] var4 = ar;
            int var5 = var4.length;

            int var6;
            for(var6 = 0; var6 < var5; ++var6) {
                String i = var4[var6];
                if (count >= this.startMessage && count <= this.endMessage) {
                    ++count;
                    StringBuilder sbf = new StringBuilder(i);
                    int len = sbf.length();
                    sbf.deleteCharAt(len - 1);
                    sbf.deleteCharAt(0);
                    String str = sbf.toString();
                    String[] ele = str.split(",");
                    Date date = new Date(Long.parseLong(ele[2]));
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                    format.format(date);
                    format.setTimeZone(TimeZone.getTimeZone("IST"));
                    String formatted = format.format(date);
                    System.out.println(Arrays.toString(ele));
                    dText.append(String.format("%s<font color='green'>%s</font> <font color='white'> said: %s</font><br/>", formatted, this.getNameFromEmail(ele[1]), ele[0].replace("\"", "")));
                } else {
                    ++count;
                }
            }

            display.setText("<html>" + dText + "</html>");
            if (this.firstLoad) {
                AdjustmentListener[] var17 = this.s.getAdjustmentListeners();
                var5 = var17.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    AdjustmentListener a = var17[var6];
                    a.adjustmentValueChanged((AdjustmentEvent)null);
                }

                this.firstLoad = false;
            }

        }
    }

    Group(String groupName, String userName) throws IOException {
        this.currentGroup = DataExtractor.getGuildInfo(groupName);
        this.name = groupName;
        JFrame jf = new JFrame(groupName + " window");
        JPanel jp = new JPanel();
        JPanel jp1 = new JPanel();
        Font def = new Font("Agency FB", 1, 18);
        String description = null;

        try {
            description = this.currentGroup.getDescription();
        } catch (Exception var10) {
            Exception ex = var10;
            ex.printStackTrace();
        }

        JLabel label = new JLabel(String.format("<html><font color='green'>%s</font> / <font color='white'>%s</font></html>", groupName, description));
        label.setFont(def);
        CompletableFuture.runAsync(() -> {
            while(true) {
                try {
                    this.updateMessages();
                } catch (Exception var3) {
                    Exception e = var3;
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var2) {
                    InterruptedException ex = var2;
                    ex.printStackTrace();
                }
            }
        });
        jp.add(label);
        display = new JLabel("<html> <font color='green'></font></html>");
        display.setFont(def);
        display.setSize(500, 500);
        jp1.add(display);
        final JTextField message = new JTextField("Message here");
        message.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (message.getText().equals("Message here")) {
                    message.setText("");
                }

            }
        });
        message.setFont(def);
        message.setBackground(Color.blue);
        message.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    ActionListener[] var2 = Group.this.enter.getActionListeners();
                    int var3 = var2.length;

                    for(int var4 = 0; var4 < var3; ++var4) {
                        ActionListener a = var2[var4];
                        a.actionPerformed((ActionEvent)null);
                    }
                }

            }

            public void keyTyped(KeyEvent e) {
            }
        });
        this.enter = new JButton("<html> <font color='blue'>Enter your </font><font color='green'> Text</font></html>");
        this.enter.setEnabled(true);
        this.enter.setBackground(Color.black);
        this.s = new JScrollBar();
        this.enter.addActionListener((clicked) -> {
            if (message.getText().length() != 0) {
                try {
                    DataExtractor.sendMessage(this.name, new BaseMessage(message.getText(), userName, System.currentTimeMillis()));
                } catch (IOException var11) {
                    IOException e = var11;
                    e.printStackTrace();
                }

                InterruptedException ex;
                String reason;
                if (DataExtractor.getLast_status_code() == 406) {
                    reason = DataExtractor.getLast_request_response().getReason();

                    try {
                        this.showAlert("\tALERT : " + reason, 5000);
                    } catch (InterruptedException var10) {
                        ex = var10;
                        ex.printStackTrace();
                    }
                } else if (DataExtractor.getLast_status_code() == 409) {
                    reason = DataExtractor.getLast_request_response().getReason();

                    try {
                        this.showAlert("\tALERT : " + reason, 5000);
                    } catch (InterruptedException var9) {
                        ex = var9;
                        ex.printStackTrace();
                    }
                } else if (DataExtractor.getLast_status_code() == 429) {
                    this.rateLimited = true;
                    int retryAfter = (int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds"));
                    CompletableFuture.runAsync(() -> {
                        try {
                            Thread.sleep((long)(retryAfter * 1000));
                        } catch (InterruptedException var3) {
                            InterruptedException e = var3;
                            e.printStackTrace();
                        }

                        this.rateLimited = false;
                    });

                    try {
                        this.showTimerAlert(retryAfter * 1000);
                    } catch (InterruptedException var8) {
                        ex = var8;
                        ex.printStackTrace();
                    }
                }

                message.setText("Message here");
                ++this.lastMessageCount;
                AdjustmentListener[] var14 = this.s.getAdjustmentListeners();
                int var15 = var14.length;

                for(int var6 = 0; var6 < var15; ++var6) {
                    AdjustmentListener a = var14[var6];
                    a.adjustmentValueChanged((AdjustmentEvent)null);
                }

            }
        });
        this.s.setSize(500, 500);
        this.s.setValue(100);
        this.s.addAdjustmentListener((e) -> {
            long currentValue = (long)this.s.getValue();
            long endLimit = (currentValue + 1L) * 5L;
            long startLimit = endLimit - 5L;
            if (endLimit >= this.lastMessageCount && startLimit < this.lastMessageCount) {
                endLimit = this.lastMessageCount - 1L;
            } else if (endLimit >= this.lastMessageCount) {
                endLimit = this.lastMessageCount - 1L;
                startLimit = endLimit - 5L;
            }

            this.startMessage = startLimit;
            this.endMessage = endLimit;
        });
        this.s.setEnabled(false);
        jp1.add(this.s);
        jp.setBackground(Color.black);
        jp.setSize(200, 200);
        jp1.setBackground(Color.black);
        jp1.setSize(500, 500);
        jp.setLayout(new GridLayout(10, 1, 0, 25));
        jf.add(jp, "First");
        jp.add(jp1, "Before");
        jp.add(message, "Last");
        jp.add(this.enter, "Last");
        jf.getContentPane().setBackground(Color.black);
        jf.setSize(1000, 1000);
        jf.setVisible(true);
    }

    private String setColor(String color, String text) {
        return String.format("<html> <font color='%s'>%s</font></html>", color, text);
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

    private void showTimerAlert(int delay) throws InterruptedException {
        this.endingTime = System.currentTimeMillis() + (long)delay;
        CompletableFuture.runAsync(() -> {
            while(System.currentTimeMillis() <= this.endingTime) {
                this.retryAfter = (this.endingTime - System.currentTimeMillis()) / 1000L;
                this.text = "\tALERT : You are being rate limited, retry after " + this.retryAfter + "s.";
                display.setText(this.setColor("red", this.text));
            }

            display.setText("");
        });
    }
}
