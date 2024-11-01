/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minhathread;

/**
 *
 * @author laboratorio
 */
public class ExemploThreads {
    public static void main(String[] args) {
        MinhaThread thread1 = new MinhaThread("Mensagem 1 a cada 1 segundo", 1000);
        MinhaThread thread2 = new MinhaThread("Mensagem 2 a cada 2 segundo", 2000);
   
        thread1.start();
        thread2.start();
        
        for (int i = 0; i < 5; i++) {
            System.out.println("Iteracao principal: "+ i);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        thread1.interrupt();
        thread2.interrupt();
    }
}
