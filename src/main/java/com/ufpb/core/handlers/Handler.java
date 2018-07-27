package com.ufpb.core.handlers;

import java.net.Socket;
import java.util.Map;

import com.ufpb.core.handlers.metodos.*;

public class Handler {

    private Estrategia estrategia;

    public Handler(Socket sock, String metodo, String urn, String version, Map<String, String> headers) {
        switch(metodo) {
            case "GET":
                estrategia = new Get(sock, urn, version, headers);
            break;
            case "POST":
                estrategia = new Post(sock, urn);
            break;
            case "CONNECT":
                estrategia = new Connect(sock, version);
            break;
            case "HEAD":
                estrategia = new Head(sock, urn);
            break;
            case "OPTIONS":
                estrategia = new Options(sock, version);
            break;
            case "PATCH":
                estrategia = new Patch(sock, urn);
            break;
            case "PUT":
                estrategia = new Put(sock, urn);
            break;
            default:
                //metodo desconhecido
            break;
        }
    }

    public void executa() {
        estrategia.trata();
    }
}