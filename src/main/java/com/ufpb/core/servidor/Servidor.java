package com.ufpb.core.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Servidor implements Runnable {
    private static Logger log = Logger.getLogger(Servidor.class.getName());
    private final int porta;
    public Servidor(int porta) {
        log.config(String.format("Servidor configurado na porta %d", porta));
        this.porta = porta;
    }

	@Override
	public void run() {
        ServerSocket servidor = null;

        try{
            servidor = new ServerSocket(porta);
            log.info(String.format("Porta %d aberta", porta));

            while(true) {
                log.info("Aguardando clientes...");
                Socket cliente = servidor.accept();
                log.info(String.format("Cliente %s conectado!", 
                    cliente.getInetAddress().getHostAddress()));
                    
                Conexao con = new Conexao(cliente);
                new Thread(con).start();
            }
        } catch(IOException ex) {
            if(servidor != null && !servidor.isClosed())
                try{
                    servidor.close();
                } catch(IOException ex1) {
                    log.severe(ex1.getMessage());
                }
        }
	}
}