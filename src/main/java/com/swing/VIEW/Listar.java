package com.swing.VIEW;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.swing.model.DadosModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class Listar extends JFrame {

    private JPanel listarPainel;
    private JLabel titulo;
    private JButton inserirButton;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JTable table;
    private DefaultTableModel model;

    public Listar() {
        setTitle("Dados da API");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        configuracoesDoPainel();

        adicionarBotoes();
        tabelaAqui();
        codigodeletar();

        setContentPane(listarPainel);
    }

    private void configuracoesDoPainel() {
        listarPainel = new JPanel(new BorderLayout());

        titulo = new JLabel("Dados da API");
        listarPainel.add(titulo, BorderLayout.NORTH);
    }

    public void tabelaAqui() {
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);

        // Adicione as colunas à tabela
        model.addColumn("Código");
        model.addColumn("Nome");
        model.addColumn("Email");
        model.addColumn("Fone");

        // Adicione as linhas à tabela
        try {
            URL url = new URL("http://localhost:8800/api");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            connection.disconnect();

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = String.valueOf(jsonObject.getInt("id"));
                String nome = jsonObject.getString("nome");
                String email = jsonObject.getString("email");
                String fone = jsonObject.getString("fone");
                model.addRow(new Object[]{id, nome, email, fone});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Adicione a tabela ao JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        listarPainel.add(scrollPane, BorderLayout.CENTER);
    }
    private void codigodeletar() {
        JLabel label = new JLabel("Digite o código para deletar:");
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, textField.getPreferredSize().height)); // Definindo a largura preferencial
        JButton button = new JButton("Excluir");

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(button);

        listarPainel.add(panel, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Lógica para ação do botão "Excluir"
                String codigo = textField.getText();


                System.out.println("Excluindo código: " + codigo);
                DadosModel dadosModel = new DadosModel();
                dadosModel.deletarDados(codigo);
                atualizarRegistros();
                textField.setText("");

            }
        });
    }

   public void atualizarRegistros() {
        model.setRowCount(0); // Limpa o modelo da tabela

        try {
            URL url = new URL("http://localhost:8800/api");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            connection.disconnect();

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = String.valueOf(jsonObject.getInt("id"));
                String nome = jsonObject.getString("nome");
                String email = jsonObject.getString("email");
                String fone = jsonObject.getString("fone");
                model.addRow(new Object[]{id, nome, email, fone});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void adicionarBotoes() {
        JPanel botoesPainel = new JPanel(new GridLayout(1, 2));
        inserirButton = new JButton("Inserir");
        atualizarButton = new JButton("Atualizar");

        botoesPainel.add(inserirButton);
        botoesPainel.add(atualizarButton);


        listarPainel.add(botoesPainel, BorderLayout.NORTH);

        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Lógica para ação do botão "Inserir"
                System.out.println("Abrindo janela de inserir");
                Inserir inserir = new Inserir();
                inserir.main(new String[]{});
                dispose();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Atualizar atualizar = new Atualizar(Listar.this);
                atualizar.setVisible(true);
                System.out.println("Abrir atualizarRegistro");
            }
        });

    }

    public static void main(String[] args) {
        Listar listar = new Listar();
        listar.setVisible(true);
    }
}
