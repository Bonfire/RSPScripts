package com.bonfire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class scouterGUI extends JFrame {
    private JCheckBox useDiscordWebhookCheckBox;
    private JTextField webhookURLText;
    private static JTextField itemTextField;

    // Getter to fetch the text of the itemTextField
    public static String getItemTextField() {
        return itemTextField.getText();
    }

    public scouterGUI() {
        super("bonScouter Configuration");

        setLayout(new GridBagLayout());

        JPanel firstRow = new JPanel();
        JLabel itemLabel = new JLabel("Valuable Items: ");
        itemTextField = new JTextField(40);
        firstRow.add(itemLabel);
        firstRow.add(itemTextField);

        JPanel secondRow = new JPanel();
        useDiscordWebhookCheckBox = new JCheckBox("Use Discord Webhook?");
        JLabel webhookLabel = new JLabel("Webhook URL: ");
        webhookURLText = new JTextField(25);
        secondRow.add(useDiscordWebhookCheckBox);
        secondRow.add(webhookLabel);
        secondRow.add(webhookURLText);

        JPanel thirdRow = new JPanel();
        JButton setAreaToScoutButton = new JButton("Set Area to Scout and Save Configuration");
        thirdRow.add(setAreaToScoutButton);

        JPanel organizerPanel = new JPanel(new BorderLayout());
        organizerPanel.add(firstRow, BorderLayout.NORTH);
        organizerPanel.add(secondRow, BorderLayout.CENTER);
        organizerPanel.add(thirdRow, BorderLayout.SOUTH);

        add(organizerPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();

        setAreaToScoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // The user wants to use a Discord webhook
                if (useDiscordWebhookCheckBox.isSelected()) {
                    bonScouter.setUseWebhook(true);
                    bonScouter.setWebhookURL(webhookURLText.getText());
                    // The user does not want to use a Discord webhook
                } else {
                    bonScouter.setUseWebhook(false);
                }

                // Update the valuable items
                Scan.updateValuableItems();

                // Close the configuration menu
                scouterGUI.super.dispose();
            }
        });
    }
}
