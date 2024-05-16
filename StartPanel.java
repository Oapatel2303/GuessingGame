package SkillsProject;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class StartPanel extends JPanel
{

    public CelebrityGame controller;


    private SpringLayout panelLayout;


    private ButtonGroup typeGroup;

    private JRadioButton celebrityRadio;

    private JRadioButton literatureRadio;

    private JLabel clueLabel;

    private JLabel celebrityCountLabel;

    private JTextField answerField;

    private JTextField clueField;

    private JButton addCelebrityButton;

    private JButton startButton;

    private String celebrityClue;

    private String literatureClue;

    private String countLabelText;

    private int celebrityCount;

    public StartPanel(CelebrityGame controller)
    {
        super();
        this.controller = controller;
        this.panelLayout = new SpringLayout();
        this.typeGroup = new ButtonGroup();
        this.celebrityRadio = new JRadioButton("Celebrity");
        this.literatureRadio = new JRadioButton("Literature Celebrity");
        this.celebrityClue = "Enter the clue for the celebrity";
        this.literatureClue = "Enter the clues for the literature celeb separated by commas";
        this.clueLabel = new JLabel(celebrityClue);

        this.answerField = new JTextField("Type celebrity here (4 letters minimum thx Cher)");
        this.clueField = new JTextField("Enter celebrity clue here (10 letters minimum)");
        this.addCelebrityButton = new JButton("Add current celebrity");
        this.startButton = new JButton("Start Celebrity game");
        this.celebrityCount = 0;
        this.countLabelText = "Current Celebrity Count: " + celebrityCount;
        this.celebrityCountLabel = new JLabel(countLabelText);

        setupPanel();
        setupLayout();
        setupListeners();
    }

    private boolean validate(String answerText, String clueText)
    {
        boolean validClue;
        boolean validAnswer = false;

        if (literatureRadio.isSelected())
        {
            validClue = controller.validateClue(clueText, "Literature");
        }
        else
        {
            validClue = controller.validateClue(clueText, "");
        }

        if (answerText.length() > 4)
        {
            validAnswer = controller.validateCelebrity(answerText);
        }

        return (validClue && validAnswer);
    }

    private void setupPanel()
    {
        setLayout(panelLayout);
        typeGroup.add(celebrityRadio);
        typeGroup.add(literatureRadio);
        add(celebrityRadio);
        add(literatureRadio);
        add(clueLabel);
        add(celebrityCountLabel);
        add(answerField);
        add(clueField);
        add(addCelebrityButton);
        add(startButton);
    }

    private void setupLayout()
    {
        panelLayout.putConstraint(SpringLayout.WEST, clueLabel, 0, SpringLayout.WEST, celebrityRadio);
        panelLayout.putConstraint(SpringLayout.NORTH, celebrityRadio, 15, SpringLayout.NORTH, this);
        panelLayout.putConstraint(SpringLayout.WEST, celebrityRadio, 15, SpringLayout.WEST, this);
        panelLayout.putConstraint(SpringLayout.EAST, addCelebrityButton, 0, SpringLayout.EAST, startButton);
        panelLayout.putConstraint(SpringLayout.NORTH, addCelebrityButton, 20, SpringLayout.SOUTH, clueField);
        panelLayout.putConstraint(SpringLayout.WEST, addCelebrityButton, 0, SpringLayout.WEST, celebrityRadio);

        panelLayout.putConstraint(SpringLayout.NORTH, startButton, 20, SpringLayout.SOUTH, addCelebrityButton);
        panelLayout.putConstraint(SpringLayout.NORTH, celebrityCountLabel, 0, SpringLayout.NORTH, celebrityRadio);
        panelLayout.putConstraint(SpringLayout.EAST, celebrityCountLabel, -45, SpringLayout.EAST, this);

        //Put your custom radio button info here

        panelLayout.putConstraint(SpringLayout.NORTH, literatureRadio, 10, SpringLayout.SOUTH, celebrityRadio);
        panelLayout.putConstraint(SpringLayout.WEST, literatureRadio, 0, SpringLayout.WEST, celebrityRadio);

        panelLayout.putConstraint(SpringLayout.NORTH, clueLabel, 10, SpringLayout.SOUTH, answerField);
        panelLayout.putConstraint(SpringLayout.NORTH, answerField, 40, SpringLayout.SOUTH, literatureRadio);
        panelLayout.putConstraint(SpringLayout.WEST, answerField, 0, SpringLayout.WEST, celebrityRadio);
        panelLayout.putConstraint(SpringLayout.EAST, answerField, -15, SpringLayout.EAST, this);

        panelLayout.putConstraint(SpringLayout.WEST, clueField, 0, SpringLayout.WEST, celebrityRadio);
        panelLayout.putConstraint(SpringLayout.SOUTH, clueField, 55, SpringLayout.SOUTH, answerField);
        panelLayout.putConstraint(SpringLayout.EAST, clueField, 0, SpringLayout.EAST, answerField);
        panelLayout.putConstraint(SpringLayout.WEST, startButton, 0, SpringLayout.WEST, celebrityRadio);
        panelLayout.putConstraint(SpringLayout.EAST, startButton, 0, SpringLayout.EAST, answerField);

    }

    private void setupListeners()
    {

        startButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent mouseClick)
            {
                controller.play();
            }
        });

        addCelebrityButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent mouseClick)
            {
                answerField.setBackground(Color.WHITE);
                clueField.setBackground(Color.WHITE);
                if (validate(answerField.getText(), clueField.getText()))
                {
                    addToGame();
                }
                else
                {
                    invalidInput();
                }
                celebrityCount = controller.getCelebrityGameSize();
                celebrityCountLabel.setText(countLabelText + celebrityCount);
            }
        });

        literatureRadio.addActionListener(select -> clueLabel.setText(literatureClue));
        celebrityRadio.addActionListener(select -> clueLabel.setText(celebrityClue));

    }

    private void invalidInput()
    {
        answerField.setText("INVALID!!");
        answerField.setBackground(Color.RED);
        clueField.setText("INVALID!!");
        clueField.setBackground(Color.RED);
    }

    private void addToGame()
    {
        String type = "Celebrity";
        if (literatureRadio.isSelected())
        {
            type = "Literature";
        }
        String answer = answerField.getText().trim();
        String clue = clueField.getText().trim();
        answerField.setText("");
        clueField.setText("");
        controller.addCelebrity(answer, clue, type);
        startButton.setEnabled(true);
    }

}