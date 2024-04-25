package SkillsProject;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CelebrityFrame extends JFrame
{
	private CelebrityPanel gamePanel;
	private JPanel panelCards;
	private StartPanel startPanel;
	private CelebrityGame controller;
	
	public CelebrityFrame(CelebrityGame controllerRef)
	{
		super();
		this.controller = controllerRef;
		this.panelCards = new JPanel(new CardLayout());
		this.gamePanel = new CelebrityPanel(controller);
		this.startPanel = new StartPanel(controller);
		setupFrame();
	}
	
	private void setupFrame()
	{
		panelCards.add(gamePanel, "GAME");
		panelCards.add(startPanel, "START"); 
		this.setSize(800,800);
		this.setTitle("Person Game");
		this.add(panelCards);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		replaceScreen("START");

		this.setVisible(true); 
		
	}
	
	public void replaceScreen(String screen)
	{
		if(screen.equals("GAME"))
		{
			gamePanel.addClue(controller.sendClue());
		}

        ((CardLayout)panelCards.getLayout()).show(panelCards , screen);	
	}
}