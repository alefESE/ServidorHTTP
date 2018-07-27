package com.ufpb.core.handlers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.ufpb.core.util.HttpResponseBuilder;
import com.ufpb.core.util.Status;

public class Get implements Estrategia {

    private final String urn;
	private final Socket sock;
	private final String host;
	private HttpResponseBuilder builder = new HttpResponseBuilder();
	private Logger log = Logger.getLogger(Get.class.getName());

    public Get(Socket sock, String urn, String host) {
        this.sock = sock;
		this.urn = urn;
		this.host = host;
    }

	@Override
	public void trata() {
        try {
			if(host.contains("localhost:")) {
				OutputStream os = sock.getOutputStream();
				try {
					FileReader arquivo = new FileReader("src/main/resources/" 
					+ host.split(":")[0] + "/" + host.split(":")[1] + urn);
					BufferedReader br = new BufferedReader(arquivo);
					builder.setStatus(Status.OK)
					.addHeader("Date", getServerTime())
					.addHeader("Content-Length" , "230")
					.addHeader("Content-Type", "text/html")
					.addHeader("Connection", "Closed");
					String linha;
					StringBuilder sb = new StringBuilder();
					while((linha = br.readLine()) != null)
						sb.append(linha);
					builder.setPayload(sb.toString());
					os.write(builder.build().getBytes(Charset.forName("UTF-8")));
					br.close();
				} catch(FileNotFoundException e) {
					//TODO: tornar headers automaticos
					log.info(e.getMessage());
					builder.setStatus(Status.NOT_FOUND)
					.addHeader("Date", getServerTime())
					.addHeader("Content-Length" , "230")
					.addHeader("Content-Type", "text/html")
					.addHeader("Connection", "Closed")
					.setPayload("<html>\n<head>\n<title>404 Not Found</title>\n"+
					"</head>\n<body>\n<h1>Not Found</h1><p>The requested URL"+ urn + " was not"+ 
					" found on this server.</p></body>\n</html>\n");
					os.write(builder.build().getBytes(Charset.forName("UTF-8")));
				}
			} else {
				//TODO: host com DNS
			}
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
	}

	private String getServerTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(calendar.getTime());
	}
}