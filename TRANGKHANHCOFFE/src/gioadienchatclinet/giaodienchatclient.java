package gioadienchatclinet;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class giaodienchatclient extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtnhanvien;
    private JTextField txtip;
    private JTextField txtport;
    private JLabel txtipadres;

    Socket socket = null;
    String mngip = "";
    int port = 0;
    String staffname = " ";
    BufferedReader bf = null;
    DataOutputStream dos = null;
    private giaodienchatclient thisframe;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    giaodienchatclient frame = new giaodienchatclient();
                    frame.setVisible(true);
                    } 
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public giaodienchatclient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 685, 783);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(55, 71, 79)); // Set the background color here

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 651, 80);
        panel.setBackground(new Color(69, 90, 100)); // Dark blue-grey background
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nhân Viên:");
        lblNewLabel.setForeground(Color.WHITE); // White text for visibility
        lblNewLabel.setBounds(10, 20, 89, 29);
        panel.add(lblNewLabel);

        txtipadres = new JLabel("IP Address:");
        txtipadres.setForeground(Color.WHITE);
        txtipadres.setBounds(180, 20, 89, 29);
        panel.add(txtipadres);

        JLabel lblPort = new JLabel("PORT:");
        lblPort.setForeground(Color.WHITE);
        lblPort.setBounds(366, 20, 89, 29);
        panel.add(lblPort);

        txtnhanvien = new JTextField();
        txtnhanvien.setBounds(69, 25, 101, 19);
        panel.add(txtnhanvien);
        txtnhanvien.setColumns(10);

        txtip = new JTextField();
        txtip.setColumns(10);
        txtip.setBounds(255, 25, 101, 19);
        panel.add(txtip);

        txtport = new JTextField();
        txtport.setColumns(10);
        txtport.setBounds(415, 25, 101, 19);
        panel.add(txtport);

        JButton btnNewButton = new JButton("CONNECT");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNewButton.setBackground(new Color(38, 50, 56)); // Darker shade for hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNewButton.setBackground(new Color(69, 90, 100)); // Original darker shade
            }
        });
        btnNewButton.setBackground(new Color(69, 90, 100)); // Dark blue-grey button
        btnNewButton.setForeground(Color.WHITE); // White text for contrast
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_btnNewButton_actionPerformed(e);
            }
        });
        btnNewButton.setBounds(541, 10, 100, 60);
        panel.add(btnNewButton);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 114, 651, 559);
        panel_1.setBackground(new Color(120, 144, 156)); // Lighter grey for inner panel
        contentPane.add(panel_1);
        thisframe = this;
        this.setLocationRelativeTo(null);
    }

    protected void do_btnNewButton_actionPerformed(ActionEvent e) {
    	try {
			mngip = txtip.getText();
			port = Integer.parseInt(txtport.getText());
			staffname = txtnhanvien.getText();	
			
		} catch (Exception e2) {
		 JOptionPane.showMessageDialog(this, "KHÔNG ĐƯỢC ĐỂ KÝ TỰ TRỐNG");
		}

		try {
			 socket = new Socket(mngip,port); 
			 if(socket!=null) {
			   chatpanel p = new chatpanel(socket, staffname,"admin");
			   p.setBounds(10, 100,1000, 1000);
			   thisframe.getContentPane().add(p);
			   p.updateUI();
			   bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			   dos = new DataOutputStream(socket.getOutputStream());
			   dos.writeBytes("Staff:" + staffname);
			   dos.write(13);
			   dos.write(10);  
			   dos.flush();
			 }
	    
			
		} catch (Exception e2) {
		 JOptionPane.showMessageDialog(this, "VUI LÒNG NHẬP ĐÚNG MÃ");
		}
		
	  
	}
    
}
