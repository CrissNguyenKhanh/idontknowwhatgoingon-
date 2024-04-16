package gioadienchatclinet;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Color;

public class sever extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtport;
    private ServerSocket srv = null;
    private Thread t;
    private BufferedReader bf = null;
    private JTabbedPane tabbedPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    sever frame = new sever();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public sever() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 853, 789);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(277, 10, 552, 645);
        contentPane.add(panel);
        panel.setLayout(null);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(Color.RED);
        tabbedPane.setBounds(10, 10, 532, 623);
        panel.add(tabbedPane);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(277, 683, 597, 59);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        txtport = new JTextField();
        txtport.setText("8088");
        txtport.setBounds(280, 30, 261, 19);
        panel_1.add(txtport);
        txtport.setColumns(10);
        setLocationRelativeTo(null);

        int serverPort = Integer.parseInt(txtport.getText());
        try {
            srv = new ServerSocket(serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            try {
                Socket aff = srv.accept();
                if (aff != null) {
                    bf = new BufferedReader(new InputStreamReader(aff.getInputStream()));
                    String s = bf.readLine();
                    int pos = s.indexOf(":");
                    String staffname = s.substring(pos + 1);
                    chatpanel p = new chatpanel(aff, "admin", staffname);
                    tabbedPane.add(staffname, p);
                    p.updateUI();
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
