package cop2805;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class WordSearcher {
	private List<String> text;
	
	public WordSearcher(String source) {
		Path sourcePath = Paths.get(source);
		Charset usAscii = Charset.forName("US-ASCII");
		try {
			text = Files.readAllLines(sourcePath, usAscii);
			text.replaceAll(String :: toUpperCase);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public List<Integer> searchWord(String searchQuery) {
		List<Integer> returnList = new ArrayList<Integer>();
		searchQuery = searchQuery.toUpperCase();
		for (int x = 0; x < text.size(); x++) {
			String result = text.get(x);
			if (result.indexOf(searchQuery) >= 0) {
				returnList.add(x);
			}
		}
		return returnList;
	}
}
