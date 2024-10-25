/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import beans.Categorias;
import Conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laboratorio
 */
public class CategoriaDAO {
    private Conexao conexao;
    private Connection conn;
    
    public CategoriaDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Categorias categoria){
        String sql = "INSERT INTO categorias (nome) VALUES (?);";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            
            stmt.setString(1, categoria.getNome());
            
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
        }
        
    }
    
}
