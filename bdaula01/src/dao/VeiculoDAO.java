/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import beans.Veiculo;
import Conexao.Conexao;
import beans.Pessoa;
import dao.*;
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
public class VeiculoDAO {
    private Conexao conexao;
    private Connection conn;

    public VeiculoDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    /**
     * Método que insere um novo Veiculo no banco
     * @param veiculo - Passa um objeto do tipo Veiculo
     */
    public void inserir(Veiculo veiculo){
        //Comando que vai inserir um novo Veiculo no banco
        //Param ('?'), modelo do veiculo, placa do veiculo e foreignkey da tabela pessoa
        String sql = "INSERT INTO veiculo (modelo, placa, id_pessoa) VALUES (?,?,?);";
        
        try {
            //Prepara o comando
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            
            //Pega os tres parametros (modelo, placa, id_pessoa)
            stmt.setString(1, veiculo.getModelo());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setInt(3, veiculo.getPessoaid().getId());
            
            //Insere o veiculo
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir veiculo: "+ex.getMessage());
        }
    }
     
    public Veiculo getVeiculo(int id){
        String sql = "SELECT * FROM veiculo WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Veiculo v = new Veiculo();
            
            
            
            rs.first();
            v.setId(id);
            v.setModelo(rs.getString("modelo"));
            v.setPlaca(rs.getString("placa"));
            
            int pessoaID = rs.getInt("id_pessoa");
            PessoaDAO pDAO = new PessoaDAO();
            Pessoa p = pDAO.getPessoa(pessoaID);
            
            v.setPessoaid(p);
            return v;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar veiculo: "+ ex.getMessage());
            return null;
        }
    }
    
    public void editar(Veiculo veiculo){
        try {
            String sql = "UPDATE veiculo set modelo=?, placa=?, id_pessoa=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, veiculo.getModelo());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setInt(3, veiculo.getPessoaid().getId());
            stmt.setInt(4, veiculo.getId());
            
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar veiculo; " + ex.getMessage());
        }
    }
    
    /**
     * Método que exclui um veiculo do banco, conforme o id do Veiculo
     * @param id - Identificador do veiculo que deseja excluir
     */
    public void excluir(int id){
        try {
            //Consulta que vai ser feita no banco
            //Cada '?' é um parametro pego no stmt
            String sql = "delete from veiculo WHERE id = ?";
            
            //Prepara a consulta
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            //Define o primeiro parametro ('?') como o id e executa a consulta
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir veiculo: "+ex.getMessage());
        }
    }
}
