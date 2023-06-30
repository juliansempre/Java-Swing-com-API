package com.swing.model;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.BorderLayout;


public class DadosModel extends JFrame {
    String nome;
    String email;
    String fone;
    String data;

    //Enviar
    public void enviadoparaweb(String nome, String email, String fone, String data) {
        System.out.println("Testando "+ nome + " " + email + " " + fone + " " + data);

        //Validação de campos vazios
        if(nome.isEmpty()  || email.isEmpty()  || fone.isEmpty()  || data.isEmpty()){
            JOptionPane.showMessageDialog(null, "Preencha os campos");
            System.out.println("Usuario não inserido pois os campos estão vazios");
        }
        else {

            String url = "http://localhost:8800/api";


            // Criar o objeto JSON com os valores dos campos
            String dados = String.format("{\"nome\":\"%s\",\"email\":\"%s\",\"fone\":\"%s\",\"data_nascimento\":\"%s\"}",
                    nome, email, fone, data);

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(dados.getBytes());
                    os.flush();
                }

                int responseCode = con.getResponseCode();
                System.out.println("Código de resposta: " + responseCode);
                System.out.println("Dados enviados para web");
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");


                con.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    //Atualizar

    public void atualizarDados(String id, String nome, String email, String fone, String data) {
        System.out.println("Testando " + nome + " " + email + " " + fone + " " + data);

        // Validação de campos vazios
        if (nome.isEmpty() || email.isEmpty() || fone.isEmpty() || data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha os campos");
            System.out.println("Usuário não atualizado porque os campos estão vazios");
        } else {
            String url = "http://localhost:8800/api/" + id;

            // Criar o objeto JSON com os valores dos campos
            String dados = String.format("{\"nome\":\"%s\",\"email\":\"%s\",\"fone\":\"%s\",\"data_nascimento\":\"%s\"}",
                    nome, email, fone, data);

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("PUT");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(dados.getBytes());
                    os.flush();
                }

                int responseCode = con.getResponseCode();
                System.out.println("Código de resposta: " + responseCode);
                System.out.println("Dados atualizados na web");
                JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");

                con.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void deletarDados(String id) {
        //validação
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null, "Preencha o campo!");
        }else {

            String url = "http://localhost:8800/api/" + id;

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("DELETE");
                int responseCode = con.getResponseCode();
                System.out.println("Código de resposta: " + responseCode);
                System.out.println("Dados excluídos na web");
                JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");

                con.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    //refresh
    //Chama o criarTabelinha denovo



}