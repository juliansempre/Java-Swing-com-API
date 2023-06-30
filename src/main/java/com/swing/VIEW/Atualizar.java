package com.swing.VIEW;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Atualizar extends JFrame {

    private JTextField idTextField;
    private JTextField nomeTextField;
    private JTextField emailTextField;
    private JTextField foneTextField;
    private JTextField dataTextField;

    private Listar listar;

    public Atualizar(Listar listar) {
        this.listar = listar;
        setTitle("Atualizar Registro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        configurarComponentes();
    }


    private void configurarComponentes() {
        JLabel idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField();
        JLabel foneLabel = new JLabel("Fone:");
        foneTextField = new JTextField();
        JLabel dataLabel = new JLabel("Data:");
        dataTextField = new JTextField();
        JButton enviarButton = new JButton("Enviar");

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(foneLabel);
        panel.add(foneTextField);
        panel.add(dataLabel);
        panel.add(dataTextField);
        panel.add(enviarButton);

        setContentPane(panel);

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Get the values from the text fields
                String id = idTextField.getText();
                String nome = nomeTextField.getText();
                String email = emailTextField.getText();
                String fone = foneTextField.getText();
                String data = dataTextField.getText();

                // Update the record with the provided values
                atualizarDados(id, nome, email, fone, data);

                System.out.println("Registro atualizado:");
                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
                System.out.println("Fone: " + fone);
                System.out.println("Data: " + data);
                listar.tabelaAqui();

                dispose(); // Close the window after updating the record
            }
        });
    }

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
                listar.atualizarRegistros();
                con.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
