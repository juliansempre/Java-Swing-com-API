package com.swing.model;

import com.swing.view.PainelDeControle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class APIStarter {
    private static Process apiProcess;
    private static boolean apiRunning = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Selecionar pasta da API");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton startButton = new JButton("Iniciar API");
        startButton.setPreferredSize(new Dimension(180, 30));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!apiRunning) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int result = fileChooser.showOpenDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String apiPath = fileChooser.getSelectedFile().getAbsolutePath();
                        startAPI(apiPath);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "API já está em execução.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JButton stopButton = new JButton("Fechar API");
        stopButton.setPreferredSize(new Dimension(180, 30));
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAPI();
            }
        });

        JButton controlPanelButton = new JButton("Abrir Painel de Controle");
        controlPanelButton.setPreferredSize(new Dimension(180, 30));
        controlPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openControlPanel();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(controlPanelButton);

        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    private static void startAPI(String apiPath) {
        try {
            String[] command = {"cmd", "/c", "start", "cmd", "/k", "node", apiPath + File.separator + "index.js"};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            apiProcess = processBuilder.start();
            apiRunning = true;

            JOptionPane.showMessageDialog(null, "API iniciada", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao iniciar a API", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void stopAPI() {
        if (apiProcess != null && apiProcess.isAlive()) {
            apiProcess.destroy();
            apiRunning = false;
            JOptionPane.showMessageDialog(null, "API encerrada", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "API não está em execução.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void openControlPanel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PainelDeControle painelDeControle = new PainelDeControle();
                painelDeControle.main(new String[]{});
            }
        });
    }
}
