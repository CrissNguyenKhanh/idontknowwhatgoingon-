package gioadienchatclinet;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class chatpanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea receivedMessagesArea;
    private JTextArea messageTextArea;
    private Socket socket;
    private BufferedReader reader;
    private DataOutputStream outputStream;
    luongthuhai k = null;
    private String employeeName;
    private String adminName;

    public chatpanel(Socket socket, String employeeName, String adminName) {
        this.socket = socket;
        this.employeeName = employeeName;
        this.adminName = adminName;

        setLayout(null);

        BackgroundPanel panel = new BackgroundPanel();
        panel.setBounds(10, 20, 529, 371);
        add(panel);
        panel.setLayout(null);
        receivedMessagesArea = new JTextArea();
        receivedMessagesArea.setBounds(10, 10, 509, 351);
        receivedMessagesArea.setEditable(false);
        panel.add(receivedMessagesArea);

        JScrollPane scrollPane = new JScrollPane(receivedMessagesArea);
        scrollPane.setBounds(10, 10, 509, 351);
        panel.add(scrollPane);

        messageTextArea = new JTextArea();
        messageTextArea.setBounds(10, 421, 323, 58);
        add(messageTextArea);
     

        JButton sendButton = new JButton("SEND");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        customizeButton(sendButton);
        sendButton.setBounds(344, 421, 195, 58);
        add(sendButton);

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());
           k  = new luongthuhai(socket, receivedMessagesArea, employeeName, adminName);
          k.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try {
            outputStream.writeBytes(messageTextArea.getText() + "\r\n");
            outputStream.flush();
            receivedMessagesArea.append("\n" + employeeName + ": " + messageTextArea.getText().trim()+"\n");
            messageTextArea.setText("");
        } catch (Exception e) {
            messageTextArea.setText("");
            e.printStackTrace();
        }
    }

    public JTextArea getReceivedMessagesArea() {
        return this.receivedMessagesArea;
    }


    private void customizeButton(JButton button) {
        button.setBackground(new Color(100, 149, 237)); // Light blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
       

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(30, 144, 255)); // Darker blue when hovered
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237)); // Light blue
            }
        });
    }

    

    private class BackgroundPanel extends JPanel {
        private Color color1 = new Color(100, 149, 237); // Light blue
        private Color color2 = new Color(30, 144, 255);  // Darker blue

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
