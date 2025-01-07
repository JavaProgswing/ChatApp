//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
    static String getname = "";
    static boolean nameclear = true;
    static boolean passclear = true;
    static JButton enter;
    static JButton register;
    static JButton forgotPass;
    static JLabel display;

    Login() {
        JFrame jf = new JFrame("Login / Register Window");
        JPanel jp = new JPanel();
        Font def = new Font("Agency FB", Font.BOLD, 18);
        JLabel name = new JLabel("<html><font color='white'>Enter </font><font color='green'>Email</font></html>");
        name.setFont(def);
        jp.add(name);
        final JTextField username = new JTextField("Must be a valid email address");
        final JPasswordField userpass = new JPasswordField("Must have at least 4 characters");
        enter = new JButton("<html> <font color='blue'>Continue</font></html>");
        final JCheckBox showPass = new JCheckBox("<html> <font color='green'> Show Password.</font></html>", false);
        username.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (!username.getText().equals("Must be a valid email address")) {
                    Login.enter.setText("<html> <font color='blue'>Continue As </font> <font color='red'> " + username.getText() + "</font></html>");
                }

                if (Login.nameclear) {
                    username.setText("");
                }

                Login.nameclear = false;
            }
        });
        username.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (Login.enter.isEnabled() && e.getKeyCode() == 10) {
                    userpass.requestFocus();
                    showPass.setSelected(false);
                    userpass.setText("");
                    if (username.getText().equals("Must be a valid email address")) {
                        return;
                    }

                    Login.enter.setText("<html> <font color='blue'>Continue As </font> <font color='red'> " + username.getText() + "</font></html>");
                }

            }

            public void keyTyped(KeyEvent e) {
            }
        });
        username.setFont(def);
        username.setBackground(Color.blue);
        jp.add(username);
        JLabel pass = new JLabel("<html><font color='white'>Enter </font><font color='red'>Password</font></html>");
        pass.setFont(def);
        jp.add(pass);
        userpass.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (Login.passclear) {
                    userpass.setText("");
                }

                Login.passclear = false;
            }
        });
        userpass.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10 && Login.enter.isEnabled()) {
                    ActionListener[] var2 = Login.enter.getActionListeners();

                    for (ActionListener a : var2) {
                        a.actionPerformed(null);
                    }
                }

            }

            public void keyTyped(KeyEvent e) {
            }
        });
        getname = username.getText();
        userpass.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!username.getText().equals("Must be a valid email address")) {
                    showPass.setSelected(false);
                    Login.enter.setText("<html> <font color='blue'>Continue As </font> <font color='red'> " + username.getText() + "</font></html>");
                }
            }
        });
        userpass.setFont(def);
        userpass.setBackground(Color.blue);
        jp.add(userpass);
        showPass.addItemListener((e) -> {
            boolean checked = showPass.isSelected();
            if (checked) {
                userpass.setEchoChar('\u0000');
            } else {
                userpass.setEchoChar('•');
            }

        });
        showPass.setBackground(Color.black);
        showPass.setSelected(true);
        jp.add(showPass);
        register = new JButton("<html> <font color='blue'>Register Your </font> <font color='red'> Account </font></html>");
        register.setEnabled(false);
        enter.setEnabled(true);
        enter.setBackground(Color.black);
        enter.addActionListener((clicked) -> {
            getname = username.getText().trim().toLowerCase();
            StringBuffer getpass = new StringBuffer(String.valueOf(userpass.getPassword()));
            boolean userAuthenticated = false;

            try {
                new AccessTokenGenerator(getname, getpass);
                userAuthenticated = AccessTokenGenerator.isValidAccessToken();
            } catch (ConnectException var13) {
                var13.printStackTrace();

                try {
                    this.showAlert("\tALERT : The servers are down, mail chatapp.javainc@gmail.com for more information!", 5000);
                } catch (InterruptedException var9) {
                    var9.printStackTrace();
                }

                return;
            } catch (IOException var14) {
                var14.printStackTrace();
            }

            if (AccessTokenGenerator.getLast_status_code() == 401) {
                try {
                    this.showAlert("\tALERT : Incorrect password.", 5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                forgotPass.setEnabled(true);
            } else if (AccessTokenGenerator.getLast_status_code() == 400) {
                try {
                    this.showAlert("\tALERT : Incorrect username.", 5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                enter.setEnabled(false);
                register.setEnabled(true);
            } else if (AccessTokenGenerator.getLast_status_code() == 429) {
                this.showTimerAlert((int)Double.parseDouble(AccessTokenGenerator.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds")) * 1000);
            } else if (userAuthenticated) {
                try {
                    User user = DataExtractor.getUserInfo();
                    if (DataExtractor.getLast_status_code() == 429) {
                        this.showTimerAlert((int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds")) * 1000);
                        return;
                    }

                    this.showAlert(String.format("\tSuccessfully logged in as user %s.", getname), "green", 5000);
                    Thread.sleep(1000L);
                    jf.setVisible(false);
                    new Groups(getname);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        jp.add(enter);
        register.setFont(def);
        register.setBackground(Color.black);
        register.addActionListener((clicked) -> {
            getname = username.getText().trim().toLowerCase();
            StringBuffer getpass = new StringBuffer(String.valueOf(userpass.getPassword()));

            try {
                DataExtractor.sendOTPUser(new BaseUser(getname, getpass));
            } catch (IOException var13) {
                IOException ex = var13;
                ex.printStackTrace();
            }

            if (DataExtractor.getLast_status_code() == 429) {
                this.showTimerAlert((int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds")) * 1000);
            } else {
                InterruptedException e;
                if (DataExtractor.getLast_status_code() == 406) {
                    try {
                        this.showAlert("\tALERT : The email provided is invalid.", 5000);
                    } catch (InterruptedException var12) {
                        e = var12;
                        e.printStackTrace();
                    }
                } else if (DataExtractor.getLast_status_code() == 200) {
                    try {
                        this.showAlert("\tALERT : The OTP has successfully been sent to the user!.", "green", 5000);
                    } catch (InterruptedException var11) {
                        e = var11;
                        e.printStackTrace();
                    }

                    while(true) {
                        while(true) {
                            String OTP = JOptionPane.showInputDialog((Component)null, "Enter the OTP", "000000");
                            if (OTP == null) {
                                JOptionPane.showMessageDialog((Component)null, "Please enter something and try again!");
                            } else {
                                try {
                                    DataExtractor.verifyOTPUser(new BaseUser(getname, getpass), OTP);
                                } catch (IOException var10) {
                                    IOException exx = var10;
                                    exx.printStackTrace();
                                }

                                if (DataExtractor.getLast_status_code() == 429) {
                                    double retryAfter = Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds")) * 1000.0;
                                    long endingTime = System.currentTimeMillis() + (long)((int)retryAfter);

                                    while(System.currentTimeMillis() <= endingTime) {
                                        long var10001 = endingTime - System.currentTimeMillis();
                                        JOptionPane.showMessageDialog((Component)null, "You are being rate-limited, try again in " + var10001 / 1000L + "s.");
                                    }
                                } else {
                                    if (DataExtractor.getLast_status_code() != 401) {
                                        if (DataExtractor.getLast_status_code() == 409) {
                                            JOptionPane.showMessageDialog((Component)null, "This email is already registered as another user.");
                                            return;
                                        } else {
                                            if (DataExtractor.getLast_status_code() == 406) {
                                                JOptionPane.showMessageDialog((Component)null, DataExtractor.getLast_request_response().getReason());
                                            } else {
                                                JOptionPane.showMessageDialog((Component)null, "Successfully registered.");
                                                register.setEnabled(false);
                                                enter.setEnabled(true);
                                            }

                                            return;
                                        }
                                    }

                                    int result = JOptionPane.showConfirmDialog((Component)null, "The provided OTP was wrong, Wanna retry?", "Wrong OTP", 0, 3);
                                    if (result == 1) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        });
        jp.add(register);
        forgotPass = new JButton("<html> <font color='blue'>Forgot </font> <font color='red'> Password </font></html>");
        forgotPass.setEnabled(false);
        forgotPass.setFont(def);
        forgotPass.setBackground(Color.black);
        forgotPass.addActionListener((clicked) -> {
            getname = username.getText().trim().toLowerCase();
            StringBuffer getpass = new StringBuffer(String.valueOf(userpass.getPassword()));

            try {
                DataExtractor.sendRecoveryOTPUser(new BaseUser(getname, getpass));
            } catch (IOException var14) {
                IOException ex = var14;
                ex.printStackTrace();
            }

            if (DataExtractor.getLast_status_code() == 429) {
                this.showTimerAlert((int)Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds")) * 1000);
            } else {
                InterruptedException e;
                if (DataExtractor.getLast_status_code() == 406) {
                    try {
                        this.showAlert("\tALERT : The email provided is invalid.", 5000);
                    } catch (InterruptedException var13) {
                        e = var13;
                        e.printStackTrace();
                    }
                } else if (DataExtractor.getLast_status_code() == 200) {
                    try {
                        this.showAlert("\tALERT : The recovery OTP has successfully been sent to the user!.", "green", 5000);
                    } catch (InterruptedException var12) {
                        e = var12;
                        e.printStackTrace();
                    }

                    while(true) {
                        while(true) {
                            String OTP = JOptionPane.showInputDialog((Component)null, "Enter the recovery OTP", "000000");
                            if (OTP == null) {
                                JOptionPane.showMessageDialog((Component)null, "Please enter something and try again!");
                            } else {
                                try {
                                    DataExtractor.verifyRecoveryOTPUser(new BaseUser(getname, getpass), OTP);
                                } catch (IOException var11) {
                                    IOException exx = var11;
                                    exx.printStackTrace();
                                }

                                if (DataExtractor.getLast_status_code() == 429) {
                                    double retryAfter = Double.parseDouble(DataExtractor.getLast_request_content().getHeaderField("X-Rate-Limit-Retry-After-Seconds")) * 1000.0;
                                    long endingTime = System.currentTimeMillis() + (long)((int)retryAfter);

                                    while(System.currentTimeMillis() <= endingTime) {
                                        long var10001 = endingTime - System.currentTimeMillis();
                                        JOptionPane.showMessageDialog((Component)null, "You are being rate-limited, try again in " + var10001 / 1000L + "s.");
                                    }
                                } else {
                                    if (DataExtractor.getLast_status_code() != 401) {
                                        if (DataExtractor.getLast_status_code() == 406) {
                                            JOptionPane.showMessageDialog((Component)null, DataExtractor.getLast_request_response().getReason());
                                            return;
                                        } else {
                                            while(true) {
                                                String newPass = getPass();
                                                if (newPass != null) {
                                                    try {
                                                        DataExtractor.changePasswordUser(new BaseUser(getname, new StringBuffer(newPass)));
                                                    } catch (IOException var10) {
                                                        IOException exxx = var10;
                                                        exxx.printStackTrace();
                                                    }

                                                    if (DataExtractor.getLast_status_code() != 200) {
                                                        JOptionPane.showMessageDialog((Component)null, DataExtractor.getLast_request_response().getReason());
                                                    } else {
                                                        JOptionPane.showMessageDialog((Component)null, "Successfully changed your password.");
                                                        forgotPass.setEnabled(false);
                                                    }

                                                    return;
                                                }

                                                JOptionPane.showMessageDialog((Component)null, "Please enter something and try again!");
                                            }
                                        }
                                    }

                                    int result = JOptionPane.showConfirmDialog((Component)null, "The provided recovery OTP was wrong, Wanna retry?", "Wrong recovery OTP", 0, 3);
                                    if (result == 1) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        });
        jp.add(forgotPass);
        display = new JLabel("<html> <font color='green'></font></html>");
        display.setFont(def);
        jp.add(display);
        jp.setBackground(Color.black);
        jp.setSize(900, 900);
        jp.setLayout(new GridLayout(10, 1, 0, 25));
        jf.add(jp);
        jf.setSize(900, 900);
        jf.setDefaultCloseOperation(3);
        jf.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10 && Login.enter.isEnabled()) {
                    ActionListener[] var2 = Login.enter.getActionListeners();
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
        jf.setVisible(true);
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

    private void showTimerAlert(int delay) {
        enter.setEnabled(false);
        boolean registerPreviousState = register.isEnabled();
        if (registerPreviousState) {
            register.setEnabled(false);
        }

        CompletableFuture.runAsync(() -> {
            long stopTimeMillis = System.currentTimeMillis() + (long)delay;

            long currentTimeMillis;
            do {
                currentTimeMillis = System.currentTimeMillis();
                long millis = stopTimeMillis - currentTimeMillis;
                String formattedTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                display.setText(this.setColor("red", String.format("Try again in %s", formattedTime)));

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var11) {
                    InterruptedException e = var11;
                    e.printStackTrace();
                }
            } while(currentTimeMillis <= stopTimeMillis);

            enter.setEnabled(true);
            if (registerPreviousState) {
                register.setEnabled(true);
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

    public static String getPass() {
        JPasswordField jpf = new JPasswordField(24);
        JLabel jl = new JLabel("Password: ");
        Box box = Box.createHorizontalBox();
        box.add(jl);
        box.add(jpf);
        int x = JOptionPane.showConfirmDialog((Component)null, box, "Password Entry", 2);
        return x == 0 ? jpf.getText() : null;
    }

    private String setColor(String color, String text) {
        return String.format("<html> <font color='%s'>%s</font></html>", color, text);
    }
}
