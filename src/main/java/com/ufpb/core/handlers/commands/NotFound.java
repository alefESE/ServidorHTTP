package com.ufpb.core.handlers.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;


public class NotFound extends HttpCommand {

    private String urn;
    
    public NotFound(String urn, OutputStream os) {
        super(os);
        this.urn = urn;
    }

	@Override
	public void executa() throws IOException, FileNotFoundException {
		builder.setStatus(Status.NOT_FOUND)
        .addHeader("Date", getServerTime())
        .addHeader("Content-Length" , "230")
        .addHeader("Content-Type", "text/html")
        .addHeader("Connection", "Closed") //TODO: jogar a tela de not found para os resources
        .setPayload("<html>\n<head>\n<title>404 Not Found</title>\n"+
        "</head>\n<body>\n<h1>Not Found</h1><p>The requested URL"+ urn + " was not"+ 
        " found on this server.</p></body>\n</html>\n");
        os.write(builder.build().getBytes(Charset.forName("UTF-8")));
	}

}