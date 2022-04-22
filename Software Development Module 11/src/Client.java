import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class Client extends JFrame implements ActionListener{
	//sets fields to be reusable across methods
	private JButton commitButton = new JButton();
	private JTextArea outputField = new JTextArea();
	private JTextField queryField = new JTextField();
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		//Standard code snippet to transfer control to EDT for runtime event handling
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Client clientInstance = new Client();
					}
				});
	}
	//inherits JFrame construction and calls for UI generation
	public Client() {
		super();
		create();
	}
	//creates GUI
	public void create() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Prime Calculator");
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.add(new JLabel());
		this.add(new JLabel("Press the button to determine prime numbers."));
		//creates action listener reference for button, and sets initial parameters.
		commitButton.addActionListener(this);
		this.add(commitButton);
		commitButton.setMaximumSize(new Dimension(150,50));
		commitButton.setText("Ready to Calculate");
		//adds text box for queries and sets parameters
		this.add(queryField);
		queryField.setMaximumSize(new Dimension(200,40));
		this.add(new JLabel("Response"));
		//sets up the output field and makes it non-editable.
		this.add(outputField);
		outputField.setMaximumSize(new Dimension(400,150));
		outputField.setEditable(false);
		//sets up scroll bars for output window, useful for multiple queries
		 JScrollPane scroll = new JScrollPane (outputField);
		    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		    this.add(scroll);
		//sets positions within JPanel and screen and enables the UI
		this.pack();
		this.setBounds(0, 0, 400, 250);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//sets initial parameters. Escape sequence on query is imperative.
		JButton source = (JButton) e.getSource();
		String query = queryField.getText() + "\n";
		InputStream input = null;
		OutputStream output = null;
		
		source.setText("Loading...");
		
		try {
			//looks for open port and requests connection to server, establishes IO stream data, and outputs to log.
			Socket clientSocket = new Socket(InetAddress.getByName("127.0.0.1"),4444);
			input = clientSocket.getInputStream();
			output = clientSocket.getOutputStream();
			outputField.append("Connection successful.");
			outputField.append("\n");
			//transmits query data as byte array to server
			output.write(query.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			outputField.append("Waiting on response from Server.");
			outputField.append("\n");
			//while response is available and not null, reads incoming data and displays it in output field.
			String response = "";
			while (response != null) {
				response = reader.readLine();
				if (response != null) {
					outputField.append(response);
					outputField.append("\n");
					outputField.append("\n");
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

}
