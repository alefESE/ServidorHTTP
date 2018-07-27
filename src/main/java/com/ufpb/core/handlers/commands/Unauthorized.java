package com.ufpb.core.handlers.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class Unauthorized extends HttpCommand {

	private final String type;

	public Unauthorized(String type, OutputStream os) {
		super(os);
		this.type = type;
	}

	@Override
	public void executa() throws FileNotFoundException, IOException {
		builder.setStatus(Status.UNAUTHORIZED)
        .addHeader("Date", getServerTime())
        .addHeader("WWW-Authenticate", type + " Realm=\" Area nao autorizada \"");
        os.write(builder.build().getBytes());
	}

}