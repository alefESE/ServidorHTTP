package com.ufpb.core;

import java.util.ArrayList;
import java.util.List;

import com.ufpb.core.servidor.Servidor;

public class Main {
    
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Uso: http <porta> <porta> <...>");
            System.exit(-1);
        }
        List<Servidor> servidores = new ArrayList<>();
        for(String porta: args) 
            servidores.add(new Servidor(Integer.parseInt(porta)));
        for(Servidor servidor: servidores)
            new Thread(servidor).start();
    }
}