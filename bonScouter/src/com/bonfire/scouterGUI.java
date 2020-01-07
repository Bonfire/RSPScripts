package com.bonfire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class scouterGUI extends JFrame {
    private static JTextField itemTextField;
    private static JCheckBox membersCheckBox;
    private static JCheckBox bountyCheckBox;
    private static JCheckBox pvpCheckBox;
    private JCheckBox useDiscordWebhookCheckBox;
    private JTextField webhookURLText;

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
        membersCheckBox = new JCheckBox("Members Worlds?");
        bountyCheckBox = new JCheckBox("Bounty Worlds?");
        pvpCheckBox = new JCheckBox("PvP Worlds?");
        JButton setAreaToScoutButton = new JButton("Save Configuration and Begin Scouting");
        thirdRow.add(membersCheckBox);
        thirdRow.add(bountyCheckBox);
        thirdRow.add(pvpCheckBox);
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

    // Getter to fetch the text of the itemTextField
    public static String getItemTextField() {
        return itemTextField.getText();
    }

    public static boolean getMembersCheckBox() {
        return membersCheckBox.isSelected();
    }

    public static boolean getBountyCheckBox() {
        return bountyCheckBox.isSelected();
    }

    public static boolean getPVPCheckBox() {
        return pvpCheckBox.isSelected();
    }
}
