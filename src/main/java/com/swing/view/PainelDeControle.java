package com.swing.view;

import com.swing.controller.DadosController;
import com.swing.model.DadosModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;


public class PainelDeControle extends JFrame {
    JTextField txtcodigo;
    JTextField txtnome;
    JTextField txtemail;
    JTextField txtfone;
    JTextField txtdata;

    //atualizar
    JTextField txtcodigoAtualizar;
    JTextField txtnomeAtualizar;
    JTextField txtemailAtualizar;
    JTextField txtfoneAtualizar;
    JTextField txtdataAtualizar;
    //Deletar
    JTextField txtDeletar;
    //botoes
    JButton inserirBotao;

    public static void main(String[] args) {
        PainelDeControle painelDeControle = new PainelDeControle();
        painelDeControle.setVisible(true);
    }

    public PainelDeControle(){
        txtcodigo = new JTextField();
        txtnome = new JTextField();
        txtemail = new JTextField();
        txtfone = new JTextField();
        txtdata = new JTextField();

        txtcodigoAtualizar = new JTextField();
        txtnomeAtualizar = new JTextField();
        txtemailAtualizar = new JTextField();
        txtfoneAtualizar = new JTextField();
        txtdataAtualizar = new JTextField();

        txtDeletar = new JTextField();
        painelzao();
        todosOsComponentes();
        criarTabelinha();

    }

    public void painelzao(){
        //super("Minha Janela"); // título da janela
        setSize(900, 600); // tamanho da janela em pixels
        setLocationRelativeTo(null); // centraliza a janela na tela
        setLayout(null); // define o layout da janela como null
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // comportamento ao fechar a janela
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximizado
    }
    public void todosOsComponentes(){


        //Jlabels
        JlabelCampo("Codigo", 1, 50);
        JlabelCampo("Nome", 220, 50);
        JlabelCampo("Email", 480, 50);
        JlabelCampo("Telefone", 740, 50);
        JlabelCampo("Data de nascimento", 1000, 50);
        //Jlabel Crud
        JlabelCampo("Painel Dados da API", 10, 5);

        //Jtextfield inserir abaixo

        txtnome = JtextCampo(txtnome, 220, 100);
        txtemail = JtextCampo(txtemail, 480, 100);
        txtfone = JtextCampo(txtfone, 740, 100);
        txtdata = JtextCampo(txtdata, 1000, 100);

        //Jtextfield Atualizar abaixo
        txtcodigoAtualizar = JtextCampo(txtcodigoAtualizar, 1, 150);
        txtnomeAtualizar = JtextCampo(txtnomeAtualizar, 220, 150);
        txtemailAtualizar = JtextCampo(txtemailAtualizar, 480, 150);
        txtfoneAtualizar = JtextCampo(txtfoneAtualizar, 740, 150);
        txtdataAtualizar = JtextCampo(txtdataAtualizar, 1000, 150);

        //Jtext Deletar
        txtDeletar = JtextCampo(txtDeletar, 1, 200);

        //Botoes aqui
        JbuttonCampo("Inserir", 1240,100, (event) ->{
            System.out.println("Inserindo..."+ txtnome.getText());
            DadosController dadosController = new DadosController();

            dadosController.setNome(txtnome.getText());
            dadosController.setEmail(txtemail.getText());
            dadosController.setFone(txtfone.getText());
            dadosController.setData(txtdata.getText());

            DadosModel dadosModel = new DadosModel();

            String nome = dadosController.getNome();
            String email = dadosController.getEmail();
            String fone = dadosController.getFone();
            String data = dadosController.getData();

            dadosModel.enviadoparaweb(nome, email, fone, data);
            criarTabelinha();

            txtnome.setText("");
            txtemail.setText("");
            txtfone.setText("");
            txtdata.setText("");
             });



        JbuttonCampo("Atualizar", 1240,150,(event) ->{
            System.out.println("Atualizalizando...");
            DadosController dadosController = new DadosController();

            dadosController.setNome(txtnomeAtualizar.getText());
            dadosController.setEmail(txtemailAtualizar.getText());
            dadosController.setFone(txtfoneAtualizar.getText());
            dadosController.setData(txtdataAtualizar.getText());

            DadosModel dadosModel = new DadosModel();


            String nome = dadosController.getNome();
            String email = dadosController.getEmail();
            String fone = dadosController.getFone();
            String data = dadosController.getData();

            dadosModel.atualizarDados(txtcodigoAtualizar.getText(), nome, email, fone, data);
            criarTabelinha();

            txtnomeAtualizar.setText("");
            txtemailAtualizar.setText("");
            txtfoneAtualizar.setText("");
            txtdataAtualizar.setText("");
        });
        JbuttonCampo("Deletar", 220,200,(event) ->{
            System.out.println("Deletando...");

            String codigo = txtDeletar.getText();

            System.out.println("Excluindo código: " + codigo);
            DadosModel dadosModel = new DadosModel();
            dadosModel.deletarDados(codigo);
            criarTabelinha();
            txtDeletar.setText("");

        });
        JbuttonCampo("Refresh", 1240,250,(event) ->{
            criarTabelinha();
        });

    }
    public JTextField JtextCampo(JTextField NometextField, int x, int y){
        NometextField = new JTextField(); // cria um novo campo de texto com texto predefinido
        NometextField.setBounds(x, y, 190, 30); // define as coordenadas X e Y do botão e o seu tamanho
        add(NometextField); // adiciona o campo de texto ao topo da janela
        return NometextField;
        }

    public void JlabelCampo(String NomedoLabel, int x, int y){
        // adicionando um JLabel com "Olá, Mundo!"
        JLabel MinhaLabel = new JLabel(NomedoLabel); // cria um novo JLabel com o texto "Olá, Mundo!"
        MinhaLabel.setBounds(x, y, 100, 50); // define as coordenadas X e Y do JLabel e o seu tamanho
        add(MinhaLabel); // adiciona o JLabel à janela
    }
    public void JbuttonCampo(String NomedoBotao, int x, int y, Consumer<ActionEvent> action){
        //adicionando botão
        JButton botao = new JButton(NomedoBotao); // cria um novo botão
        botao.setBounds(x, y, 100, 30); // define as coordenadas X e Y do botão e o seu tamanho
        add(botao); // adiciona o botão ao centro da janela

        //ação do botao
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action.accept(e);
            }
        });
    }
    public void criarTabelinha(){

        JTable tabela = new JTable();

        DefaultTableModel model = new DefaultTableModel();
        tabela.setModel(model);

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


        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 250, 1200, 300);
        add(scrollPane);
    }
}
