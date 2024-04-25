package SkillsProject;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class CelebrityPanel extends JPanel
{
	private JButton guessButton;
	private JButton resetButton;
	private JLabel guessLabel;
	private JLabel staticTimerLabel;
	private JLabel dynamicTimerLabel;
	private Timer countdownTimer;
	private ActionListener timerListener;
	private JScrollPane cluePane;
	private JTextArea clueArea;
	private JTextField guessField;
	private SpringLayout panelLayout;
	private String success;
	private String tryAgain;
	private int seconds;
	private CelebrityGame controller;

	public CelebrityPanel(CelebrityGame controller)
	{
		super();
		this.controller = controller;
		this.panelLayout = new SpringLayout();
		this.guessLabel = new JLabel("Guess:");
		this.staticTimerLabel = new JLabel("Time remaining: ");
		this.dynamicTimerLabel = new JLabel("60");
		this.guessButton = new JButton("Submit guess");
		this.resetButton = new JButton("Start again");
		this.clueArea = new JTextArea("", 30, 20);
		this.cluePane = new JScrollPane(clueArea);
		this.guessField = new JTextField("Enter guess here", 30);
		this.success = "You guessed correctly!!! \nNext clue is: ";
		this.tryAgain = "You have chosen poorly, try again!\nThe clue is: ";
		this.seconds = 60;
		this.countdownTimer = new Timer(1000, timerListener);

		setupPanel();
		setupLayout();
		setupListeners();
	}

	private void setupPanel()
	{
		this.setLayout(panelLayout);
		this.add(guessLabel);
		this.add(cluePane);
		this.add(guessField);
		this.add(guessButton);
		this.add(resetButton);
		this.add(dynamicTimerLabel);
		this.add(staticTimerLabel);
		
		staticTimerLabel.setFont(new Font("Helvetica", Font.BOLD,20));
		dynamicTimerLabel.setFont(new Font("Helvetica", Font.BOLD,20));

		cluePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cluePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		clueArea.setWrapStyleWord(true);
		clueArea.setLineWrap(true);

        clueArea.setEditable(false);
	}

	private void setupLayout()
	{
		panelLayout.putConstraint(SpringLayout.NORTH, cluePane, 15, SpringLayout.NORTH, this);
		panelLayout.putConstraint(SpringLayout.WEST, cluePane, 15, SpringLayout.WEST, this);
		panelLayout.putConstraint(SpringLayout.SOUTH, cluePane, -100, SpringLayout.SOUTH, this);
		panelLayout.putConstraint(SpringLayout.EAST, cluePane, -15, SpringLayout.EAST, this);
		panelLayout.putConstraint(SpringLayout.NORTH, guessButton, 10, SpringLayout.SOUTH, guessLabel);
		panelLayout.putConstraint(SpringLayout.SOUTH, guessButton, -15, SpringLayout.SOUTH, this);
		panelLayout.putConstraint(SpringLayout.NORTH, resetButton, 0, SpringLayout.NORTH, guessButton);
		panelLayout.putConstraint(SpringLayout.EAST, guessButton, 0, SpringLayout.EAST, cluePane);
		panelLayout.putConstraint(SpringLayout.WEST, resetButton, 0, SpringLayout.WEST, cluePane);
		panelLayout.putConstraint(SpringLayout.NORTH, guessLabel, 10, SpringLayout.SOUTH, cluePane);
		panelLayout.putConstraint(SpringLayout.WEST, guessLabel, 0, SpringLayout.WEST, cluePane);
		panelLayout.putConstraint(SpringLayout.SOUTH, resetButton, 0, SpringLayout.SOUTH, guessButton);
		panelLayout.putConstraint(SpringLayout.NORTH, guessField, 0, SpringLayout.NORTH, guessLabel);
		panelLayout.putConstraint(SpringLayout.WEST, guessField, 5, SpringLayout.EAST, guessLabel);
		panelLayout.putConstraint(SpringLayout.EAST, guessField, 0, SpringLayout.EAST, cluePane);
		panelLayout.putConstraint(SpringLayout.NORTH, staticTimerLabel, 15, SpringLayout.NORTH, resetButton);
		panelLayout.putConstraint(SpringLayout.WEST, staticTimerLabel, 10, SpringLayout.EAST, resetButton);
		panelLayout.putConstraint(SpringLayout.NORTH, dynamicTimerLabel, 0, SpringLayout.NORTH, staticTimerLabel);
		panelLayout.putConstraint(SpringLayout.WEST, dynamicTimerLabel, 5, SpringLayout.EAST, staticTimerLabel);
	}

	private void setupListeners()
	{
		resetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent mouseClick)
			{
				controller.play();
			}
		});

		guessButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent mouseClick)
			{
				updateScreen();
			}
		});

		guessField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enterPress)
			{
				updateScreen();
			}
		});

		countdownTimer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent timerFire)
			{
				timerFires();
			}
		});

		countdownTimer.start();
	}

	private void timerFires()
	{
		seconds--;
		
		if (seconds <= 10)
		{
			dynamicTimerLabel.setFont(new Font("Helvetica", Font.BOLD,20));
			dynamicTimerLabel.setForeground(Color.RED);
		}
		
		if (seconds < 0)
		{
			staticTimerLabel.setText("Times up!");
			dynamicTimerLabel.setText("00");
			seconds = 60;
			countdownTimer.stop();
			guessButton.setEnabled(false);
			guessField.setEnabled(false);
		}
		else
		{
			dynamicTimerLabel.setText("" + seconds);
		}

	}

	public void addClue(String clue)
	{
		clueArea.setText("The clue is: " + clue);
		seconds = 60;
		dynamicTimerLabel.setForeground(Color.BLACK);
		countdownTimer.restart();
		staticTimerLabel.setText("Time remaining: ");
		guessButton.setEnabled(true);
		guessField.setEnabled(true);
	}

	private void updateScreen()
	{
		String currentGuess = guessField.getText();
		clueArea.append("\nYou guessed: " + currentGuess + "\n");

		if (controller.processGuess(currentGuess))
		{
			clueArea.append(success + controller.sendClue());
			clueArea.setBackground(Color.CYAN);
		}
		else
		{
			clueArea.setBackground(Color.WHITE);
			clueArea.append(tryAgain);
			clueArea.append(controller.sendClue());
		}

		if (controller.getCelebrityGameSize() == 0)
		{
			clueArea.append("\nNo more people to guess.");
			guessButton.setEnabled(false);
			guessField.setEnabled(false);
		}
		clueArea.setCaretPosition(clueArea.getDocument().getLength());
	}
}