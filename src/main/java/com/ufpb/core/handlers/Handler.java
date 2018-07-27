package com.ufpb.core.handlers;

import java.net.Socket;

public class Handler {

    private Estrategia estrategia;

    public Handler(Socket sock, String metodo, String urn, String host) {
        switch(metodo) {
            case "GET":
                estrategia = new Get(sock, urn, host);
            break;
            case "POST":
                estrategia = new Post(sock, urn);
            break;
            case "CONNECT":
                estrategia = new Connect(sock, host);
            break;
            case "HEAD":
                estrategia = new Head(sock, urn);
            break;
            case "OPTIONS":
                estrategia = new Options(sock, host);
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