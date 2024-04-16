package gioadienchatclinet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class luongthuhai extends Thread {
	Socket s = null;
	JTextArea txt;
	BufferedReader bf;
	String nhanvien;
	String admin;
	
	
	public luongthuhai(Socket s, JTextArea txt, String nhanvien, String admin) {
		super();
		this.s = s;
		this.txt = txt;
		this.nhanvien = nhanvien;
		this.admin = admin;
		try {
			bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public void run() {
		while(true ) { 
			try {
				if(s != null) {
					String msg  = " ";
					if((msg = bf.readLine())!= null && msg.length() > 0 ) {
						txt.append("\n" + admin + ":" + msg + "\n");
					}
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				
			}
			
		}
		
		
	}
	
}
