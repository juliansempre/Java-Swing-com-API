package com.swing.VIEW;

import com.swing.controller.DadosController;
import com.swing.model.DadosModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inserir extends JFrame {
    private JTextField Txtnome;
    private JPanel MeuPainel;
    private JTextField TxtEmail;
    private JButton button1;
    private JTextField TxtFone;
    private JTextField TxtData;
    private JButton inicioButton;

    public static void main(String[] args) {
        Inserir Inserir = new Inserir();
        Inserir.configurarPainel();
    }

    public Inserir() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                  EnviandoParaModel();

            }
        });
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Listar listar = new Listar();
                listar.setVisible(true);
                listar.atualizarRegistros();
                dispose();
            }
        });
    }

    public <e> void EnviandoParaModel(){
        DadosController dadosController = new DadosController();
        //settando os dados para o controller
        dadosController.setNome(Txtnome.getText());
        dadosController.setEmail(TxtEmail.getText());
        dadosController.setFone(TxtFone.getText());
        dadosController.setData(TxtData.getText());


        //Enviando para model
        DadosModel dadosModel = new DadosModel();
        try{
            //Teste
            dadosModel.enviadoparaweb(dadosController.getNome(), dadosController.getEmail(), dadosController.getFone(), dadosController.getData());
            //Enviando para a model
            System.out.println("Enviado para model com sucesso! ");

            //Limpar formulário
            Txtnome.setText("");
            TxtEmail.setText("");
            TxtFone.setText("");
            TxtData.setText("");

            Listar listar = new Listar();
            listar.setVisible(true);

            this.dispose(); //fecha a janela atual da classe Painel

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro na view inserir.java" + e);
        }

    }
    public void configurarPainel() {
        setContentPane(MeuPainel);
        setTitle("Inserir usuario");
        setSize(600, 400);
        setBounds(0, 0, 600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
