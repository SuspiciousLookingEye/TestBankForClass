import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import javax.swing.*;

public class Server extends JFrame{
//sets initial fields for use in other methods
	private static final long serialVersionUID = 1L;
	private JTextArea serverLog = new JTextArea();

	public static void main(String[] args) {
		//upon activation, creates a calculator object, creates a server and GUI, and passes the calculator to the server and instructs it to listen for requests.
		PrimeCalculator calc = new PrimeCalculator();
		Server server = new Server();
		server.create();
		server.await(calc);
	}
	//generates GUI when called
	public void create() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Prime Calculation Server");
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.add(new JLabel("Server Log"));
		//adds in server log text area and sets parameters
		this.add(serverLog);
		serverLog.setMaximumSize(new Dimension(300,300));
		serverLog.setEditable(false);
		//sets up scroll bar support for text area.
		 JScrollPane scroll = new JScrollPane (serverLog);
		    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		    this.add(scroll);
		//sets positions within JPanel and screen and enables the UI
		this.pack();
		this.setBounds(0, 0, 350, 350);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void await(PrimeCalculator calc) {
		//sets operation conditions, a likely open port, and a loopback address to the local host.
		boolean shutdown = false;
		int port = 4444;
		ServerSocket serverSocket = null;
		String address = "127.0.0.1";
		
		try {
			//opens a server with the following parameters: port value, backlog connection count, and address to bind the server to.
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName(address));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		//while server is running
		while (!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				//listens for and accepts valid connections, as well as setting variables
				socket = serverSocket.accept();
				serverLog.append("Connection Established with Client");
				serverLog.append("\n");
				input = socket.getInputStream();
				output = socket.getOutputStream();
				//creates reader and parses the query sent from the client as a long value.
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String query = reader.readLine();
				serverLog.append(query + " inputted as query");
				serverLog.append("\n");
				long parsedQuery = Long.parseLong(query);
				serverLog.append("Query " + query + " Parsed");
				serverLog.append("\n");
				//calls the calculator to determine if the number is prime.
				boolean IsPrimeResult = calc.isPrime(parsedQuery);
				serverLog.append("Response Calculated");
				serverLog.append("\n");
				//if the value is prime, constructs a response string and sends it back to the client. Reports to log.
				if (IsPrimeResult) {
					String response = parsedQuery + " is a prime number." + "\n";
					output.write(response.getBytes());
					serverLog.append("Evaluated response for " + parsedQuery + ", value is prime");
					serverLog.append("\n");
					serverLog.append("\n");
				}
				//Otherwise, it constructs a similar response to state that it is not prime.
				else if (!IsPrimeResult) {
					String response = parsedQuery + " is not a prime number." + "\n";
					output.write(response.getBytes());
					serverLog.append("Evaluated response for " + parsedQuery + ", value is not prime");
					serverLog.append("\n");
					serverLog.append("\n");
				}
				//close connection
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//custom catch block to prevent empty strings or nulls from being parsed by parseLong()
			} catch (NumberFormatException e) {
					String errorMsg = "Empty strings cannot be passed to server. \n";
					try {
						//if an empty string is detected, sends an error back to the client, closes the connection, and reports to log.
						output.write(errorMsg.getBytes());
						socket.close();
						serverLog.append("Empty String passed to server. Rejecting value.");
						serverLog.append("\n");
						serverLog.append("\n");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			}
		}
		
	}

}
