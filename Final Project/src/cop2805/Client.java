package cop2805;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame implements ActionListener{
	public DefaultListModel<Integer> listModel = new DefaultListModel<Integer>();
	private static final long serialVersionUID = 1L;
	private JButton transmitButton = new JButton("Transmit");
	private JTextField queryField = new JTextField();
	
	
	public static void main(String[] args) {
		//Standard code snippet to transfer control to EDT for runtime event handling
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Client clientInstance = new Client();
			}
		});

	}
	
	
	public Client() {
		super();
		create();
	}
	
	public void create() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Word Searcher");
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.add(new JLabel());
		this.add(new JLabel("Search Query"));
		
		this.add(queryField);
		queryField.setMaximumSize(new Dimension(150,30));
		
		this.add(new JLabel("Response"));
		this.add(new JList<Integer>(listModel));
		
		transmitButton.addActionListener(this);
		this.add(transmitButton);
		transmitButton.setMaximumSize(new Dimension(150,50));
		
		this.pack();
		this.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 300, 0, 300, 650);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		String query = queryField.getText().toUpperCase().concat("\n");
		InputStream input = null;
		OutputStream output = null;
		
		source.setText("Loading...");
		
		try {
			Socket clientSocket = new Socket(InetAddress.getByName("127.0.0.1"),10192);
			input = clientSocket.getInputStream();
			output = clientSocket.getOutputStream();
			output.write(query.getBytes());
			if (listModel.getSize() != 0) {
				listModel.removeAllElements();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String response = "";
			while (response != null) {
				response = reader.readLine();
				if (response != null) {
					listModel.addElement(Integer.parseInt(response));
				}
			}
			clientSocket.close();
			source.setText("Transmit");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
};
	
	

