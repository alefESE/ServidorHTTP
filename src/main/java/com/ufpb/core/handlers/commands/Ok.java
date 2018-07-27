package com.ufpb.core.handlers.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Ok extends HttpCommand {

	private final String host, urn;
	public Ok(String host, String urn, OutputStream os) {
		super(os);
		//TODO: pensar numa maneira de contextualizar navegacao
		if(host.contentEquals("/")) //pega o index do contexto
			this.host = "/index.html";
		else
			this.host = host;
		this.urn = urn;
	}

	@Override
	public void executa() throws IOException, FileNotFoundException {
		FileReader arquivo = new FileReader("src/main/resources/" 
		+ host.split(":")[0] + "/" + host.split(":")[1] + urn);
		BufferedReader br = new BufferedReader(arquivo);
		String linha;
		StringBuilder sb = new StringBuilder();
		while((linha = br.readLine()) != null)
			sb.append(linha);

		builder.setStatus(Status.OK)
		.addHeader("Date", getServerTime())
		.addHeader("Content-Length" , String.valueOf(sb.length()))
		.addHeader("Content-Type", "text/html")
		.addHeader("Connection", "Closed")
		.setPayload(sb.toString());
		os.write(builder.build().getBytes(Charset.forName("UTF-8")));
		br.close();
	}

}