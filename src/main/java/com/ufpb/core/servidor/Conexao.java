package com.ufpb.core.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.ufpb.core.handlers.Handler;

public class Conexao implements Runnable {
    private final Socket sock;
    private String metodo, urn, versao;
    private static Logger log = Logger.getLogger(Conexao.class.getName());

    public Conexao(Socket sock) {
        this.sock = sock;
    }

	@Override
	public void run() {
        try {
            Map<String, String> headers = parseHttpHeaders();

            new Handler(sock, metodo, urn, versao, headers).executa();
		} catch (IOException e) {
			log.severe(e.getMessage());
		} finally {
            try {
				sock.close();
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
        }
    }
    
    private Map<String, String> parseHttpHeaders() throws IOException {
        InputStream is = sock.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String linha = br.readLine();
        metodo = linha.split(" ")[0];
        urn = linha.split(" ")[1];
        versao = linha.split(" ")[2];

        Map<String, String> headers = new HashMap<>();
        while((linha = br.readLine()).length() > 2) {// enquanto > CRLF
            headers.put(linha.split(": ")[0],
                    linha.split(": ")[1]);
        }

        return headers;
        }
}