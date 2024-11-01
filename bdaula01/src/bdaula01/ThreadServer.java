/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdaula01;

import beans.Pessoa;
import dao.PessoaDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author laboratorio
 */
public class ThreadServer extends Thread{
    private Socket clienteSocket;
    
    public ThreadServer(Socket clieSocket){
        this.clienteSocket = clienteSocket;
    }
    
    public void run(){
        try (ObjectOutputStream out = new ObjectOutputStream(clienteSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clienteSocket.getInputStream())){
            
            int id  = in.readInt();
            System.out.println("ID recebido: "+id);
            
            PessoaDAO pDAO = new PessoaDAO();
            Pessoa p = pDAO.getPessoa(id);
            
            out.writeObject(p);       
        } catch (IOException e) {
            System.out.println("Erro ao lidar com o cliente");
        }
    }
    
    
}
