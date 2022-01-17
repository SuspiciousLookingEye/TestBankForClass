package cop2805;

import java.io.*;
import java.net.*;
import java.util.List;

import javax.swing.SwingUtilities;

public class Server {

	public static void main(String[] args) {
		WordSearcher searcher = new WordSearcher("hamlet.txt");
		searchTest(searcher);
		Server server = new Server();
		server.await(searcher);
	}
	
	public static void searchTest(WordSearcher searcher) {
		System.out.println(searcher.searchWord("Denmark").toString());
	}
	
	public void await(WordSearcher searcher) {
		boolean shutdown = false;
		int port = 10192;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		while (!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				System.out.println("Accepted");
				input = socket.getInputStream();
				output = socket.getOutputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String query = reader.readLine();
				List<Integer> returnList = searcher.searchWord(query);
				for (Integer x : returnList) {
					String response = x.toString() + "\n";
					output.write(response.getBytes());
				}
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}
}
