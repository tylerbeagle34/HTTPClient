/**
 * HTTP_Client
 * @author Tyler Beagle
 */
import java.io.*;
import java.net.*;
import java.util.*;
public class http_client {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(args[0]);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setRequestMethod("GET");
	    try {
	    	FileWriter fw = new FileWriter(new File("http_client_output"));
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	while(httpCon.getResponseCode() == 301 || httpCon.getResponseCode() == 302) {
				URL newURL = new URL(httpCon.getHeaderField("Location"));
				url = newURL;
				bw.write("URL redirected to " + url + "\n");
				bw.flush();
				httpCon = (HttpURLConnection) url.openConnection();
				httpCon.setRequestMethod("GET");
			}
	    	bw.write("\nPrinting HTTP header info from " + url + "\n");
	    	bw.flush();
			Map<String, List<String>> headers = httpCon.getHeaderFields();
	        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
	        	bw.write(header.getKey() + ": " + header.getValue() + "\n");
	        	bw.flush();
	        }
	        bw.write("\nURL Content...\n");
	        bw.flush();
			InputStream input = httpCon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while(reader.readLine() != null) {
				bw.write(reader.readLine() + "\n");
				bw.flush();
			}
	    	bw.close();
	    } catch (IOException ioe) {
	    	ioe.printStackTrace();
		} 
	}
}
