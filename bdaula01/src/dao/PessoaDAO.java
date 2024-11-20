/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import beans.Pessoa;
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
public class PessoaDAO {
    private Conexao conexao;
    private Connection conn;
    
    public PessoaDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    /**
     * Método que insere uma nova Pessoa no banco
     * @param pessoa - Passa como parametro um objeto do tipo Pessoa
     */
    public void inserir(Pessoa pessoa){
        //Comando que vai inserir uma nova Pessoa no banco
        //Param ('?'), nome da pessoa, sexo da pessoa e idioma da pessoa
        String sql = "INSERT INTO pessoa (nome, sexo, idioma) VALUES (?,?,?);";
        
        try {
            //Prepara o comando
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            
            //Pega os tres parametros (nome, sexo, idioma)
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSexo());
            stmt.setString(3, pessoa.getIdioma());
            
            //Insere a pessoa
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir pessoa"+ex.getMessage());
        }
    }
    
    public Pessoa getPessoa(int id){
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Pessoa p = new Pessoa();
            
            rs.first();
            p.setId(id);
            p.setNome(rs.getString("nome"));
            p.setSexo(rs.getString("sexo"));
            p.setIdioma(rs.getString("idioma"));
            return p;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar pessoa: "+ ex.getMessage());
            return null;
        }
    }
    
    
    public void editar(Pessoa pessoa){
        try {
            String sql = "UPDATE pessoa set nome=?, sexo=?, idioma=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSexo());
            stmt.setString(3, pessoa.getIdioma());
            stmt.setInt(4, pessoa.getId());
            
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar pessoa; " + ex.getMessage());
        }
    }
    
    /**
     * Método que exclui uma pessoa do banco, conforme o id da Pessoa
     * @param id - Identificador da pessoa que deseja excluir
     */
    public void excluir(int id){
        try {
            //Consulta que vai ser feita no banco
            //Cada '?' é um parametro pego no stmt
            String sql = "delete from pessoa WHERE id = ?";
            
            //Prepara a consulta
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            //Define o primeiro parametro ('?') como o id e executa a consulta
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir pessoa: "+ex.getMessage());
        }
    }
    
    /**
     * Método que faz uma consulta no banco e armazena todas as pessoas em uma query
     * @return - Retorna uma lista com todas as pessoas cadastradas no banco
     */
    public List<Pessoa> getPessoas(){
        //Consulta que vai ser feita no banco
        String sql = "SELECT * FROM pessoa";
        
        try {
            //Prepara a consulta, 
            //Primeiro param - String da consulta em sql
            //Segundo param - Result set insensitive, pode ir tanto para frente como para tras, não reflete as mudançãs no banco
            //Terceiro param - Pode modificar as alterações direto do ResultSet
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //Executa a consulta
            ResultSet rs = stmt.executeQuery();
            
            //Cria uma lista para armazenar as Pessoas 
            List<Pessoa> listaPessoas = new ArrayList();
            
            //Loop que percorre todas as linhas da query executada 
            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setSexo(rs.getString("sexo"));
                p.setIdioma(rs.getString("idioma"));
                listaPessoas.add(p);
            }
            
            return listaPessoas;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todas as pesoas: "+ex.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * @param nome
     * @param sexo
     * @return 
     */
    public List<Pessoa> getPessoasNome(String nome, String sexo){
        //Consulta que vai ser feita no banco
        String sql = "SELECT * FROM pessoa WHERE nome LIKE ? and sexo LIKE ?";
        
        try {
            //Prepara a consulta, 
            //Primeiro param - String da consulta em sql
            //Segundo param - Result set insensitive, pode ir tanto para frente como para tras, não reflete as mudançãs no banco
            //Terceiro param - Pode modificar as alterações direto do ResultSet
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //Seta o primeiro '?' com o nome
            stmt.setString(1, "%"+ nome +"%");
            stmt.setString(2, "%"+ sexo +"%");
            
            //Executa a consulta
            ResultSet rs = stmt.executeQuery();
            
            //Cria uma lista para armazenar as Pessoas 
            List<Pessoa> listaPessoas = new ArrayList();
            
            //Loop que percorre todas as linhas da query executada 
            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setSexo(rs.getString("sexo"));
                p.setIdioma(rs.getString("idioma"));
                listaPessoas.add(p);
            }
            
            return listaPessoas;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todas as pesoas: "+ex.getMessage());
            return null;
        }
    }
    
}
