package com.ufpb.core.handlers.metodos;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.ufpb.core.handlers.commands.HttpCommand;
import com.ufpb.core.util.HttpResponseBuilder;

public abstract class Estrategia {
    protected String urn;
	protected Socket sock;
	protected String version;
	protected Map<String, String> headers;
    protected HttpResponseBuilder builder;
    protected HttpCommand comando;

    public Estrategia() {
        headers = new HashMap<>();
        builder = new HttpResponseBuilder();
    }

    public abstract void trata();
}