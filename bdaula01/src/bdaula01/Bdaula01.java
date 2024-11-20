/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bdaula01;

import beans.Pessoa;
import dao.PessoaDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author laboratorio
 */
public class Bdaula01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int porta = 12345;
        
        try (ServerSocket servidorSocket= new ServerSocket(porta)){
            System.out.println("Servidor aguardando conexao na porta: " + porta);
            
            while (true) {                
                try {
                    Socket clienteSocket = servidorSocket.accept();
                    System.out.println("Conexao aceita de: "+ clienteSocket.getInetAddress());
                    //ObjectOutputStream out = new ObjectOutputStream(clienteSocket.getOutputStream());
                    //ObjectInputStream in = new ObjectInputStream(clienteSocket.getInputStream());
                    
                    //int id = in.readInt();
                    //System.out.println("ID recebido: " + id);
                    
                    //PessoaDAO pDAO = new PessoaDAO();
                    //Pessoa p = pDAO.getPessoa(id);
                            
                    //out.writeObject(p);
                    
                    Thread threadCliente = new ThreadServer(clienteSocket);
                    threadCliente.start();
                } catch (IOException ex) {
                    System.out.println("Erro ao aceitar conexao com o cliente");
                }
            }  
        } catch (IOException e) {
            System.out.println("Erro ao criar o ServerSocket");
        }
    }
}
