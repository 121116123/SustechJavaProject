package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    JLabel userLabel;
    JTextField userField;
    JLabel userLabel2;
    JPasswordField userField2;
    JButton Login, Cancel, set;
    public static ArrayList<String> playerList = new ArrayList<>();
    public static ArrayList<String> passwordList = new ArrayList<>();

    public LoginFrame() {

        this.setTitle("Please log in");



        userLabel = new JLabel("Name");
        userLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        userLabel.setForeground(Color.BLUE);
        userLabel2 = new JLabel("PassWord");
        userLabel2.setFont(new Font("微软雅黑", Font.BOLD, 18));
        userLabel2.setForeground(Color.BLUE);

        userLabel.setBounds(20, 220, 100, 30);
        this.add(userLabel);
        userLabel2.setBounds(20, 280, 100, 30);
        this.add(userLabel2);

        userField = new JTextField();
        userField.setBounds(200, 220, 100, 30);
        userField.setBorder(BorderFactory.createLoweredBevelBorder());
        userField.setOpaque(false);
        this.add(userField);

        userField2 = new JPasswordField();
        userField2.setBounds(200, 280, 100, 30);
        userField2.setBorder(BorderFactory.createLoweredBevelBorder());
        userField2.setOpaque(false);
        this.add(userField2);


        Login = new JButton("Login in");
        Login.setBounds(45, 350, 200, 36);
        Login.setForeground(Color.BLUE);
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean rightPlayer = false;
                String userName = userField.getText();
                String passWord = userField2.getText();
                for (int i = 0; i < playerList.size(); i++) {
                    if (userName.equals(playerList.get(i)) && passWord.equals(passwordList.get(i))) {
                        JOptionPane.showMessageDialog(null, "welcome" + userName + "come to black and white");
                        rightPlayer = true;
                        dispose();
                        StartFrame startFrame=new StartFrame();
                        startFrame.setVisible(true);
//                        GameFrame mainFrame = new GameFrame(800);
//                        mainFrame.setVisible(true);
                        break;
                    }
                }
                if (!rightPlayer) {
                    if ("".equals(userName) || "".equals(passWord)) {
                        JOptionPane.showMessageDialog(null, "name or password can not be null, please login in again");
                    } else {
                        JOptionPane.showMessageDialog(null, "name and password is wrong");
                    }
                }
            }
        });
        this.add(Login);

        Cancel = new JButton("Cancel");
        Cancel.setBounds(255, 350, 200, 36);
        this.add(Cancel);
        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        set = new JButton("set a new one");
        set.setBounds(450, 350, 200, 36);
        this.add(set);
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(null, "set your name");
                String password = JOptionPane.showInputDialog(null, "set your password");
                playerList.add(name);
                passwordList.add(password);
            }
        });


        LoginPanel panel = new LoginPanel();
        this.add(panel);

        this.setSize(900, 530);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setUndecorated(true);

        //这里要写一个图片
        this.setIconImage(new ImageIcon("C:\\Users\\maxin-12345\\IdeaProjects\\class9_28\\.idea\\IMG_0912(20211117-150314).JPG").getImage());
        this.setVisible(true);
    }


//    public static void main(String[] args) {
//        new LoginFrame();
//    }

    class LoginPanel extends JPanel {
        Image background;

        public LoginPanel() {
            try {
                //这里要加背景图
                background = ImageIO.read(new File("C:\\Users\\maxin-12345\\IdeaProjects\\class9_28\\.idea\\登录背景2.0.jfif"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(background, 0, 0, 900, 530, null);
        }
    }


}
