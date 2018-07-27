package com.ufpb.core.handlers.metodos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

import com.ufpb.core.handlers.commands.NotFound;
import com.ufpb.core.handlers.commands.Ok;
import com.ufpb.core.handlers.commands.Unauthorized;

public class Get extends Estrategia {

    
	private Logger log = Logger.getLogger(Get.class.getName());

    public Get(Socket sock, String urn, String version, Map<String, String> headers) {
        this.sock = sock;
		this.urn = urn;
		this.version = version;
		this.headers = headers;
    }

	@Override
	public void trata() {
        try {
			OutputStream os = sock.getOutputStream();
			String host = headers.get("Host");
			if(host.contains("localhost:")) {
				if(!urn.contains("auth")) {
					try {
						comando = new Ok(host, urn, os);
						comando.executa();
					} catch(FileNotFoundException e) {
						log.info(e.getMessage());
						comando = new NotFound(urn, os);
						comando.executa();
					}
				} else {
					if(headers.containsKey("Authorization")) {
						final String authorization = headers.get("Authorization");
						if (authorization.startsWith("Basic")) {
							// Authorization: Basic base64credentials
							String base64Credentials = authorization.substring("Basic".length()).trim();
							String credentials = new String(Base64.getDecoder().decode(base64Credentials),
									Charset.forName("UTF-8"));
							// credentials = username:password
							final String[] values = credentials.split(":", 2);

							if(values[0].contentEquals("admin") && values[1].contentEquals("admin")){
								comando = new Ok(host, urn, os);
								comando.executa();
							} else {

							}
						}
					} else {
						comando = new Unauthorized("Basic", os);
						comando.executa();
					}
				}
			} else {
				//TODO: host com DNS
			}
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
	}
}