package SkillsProject;

import java.util.ArrayList;

public class CelebrityGame
{
  private Celebrity gameCelebrity;
  private CelebrityFrame gameWindow;
  private ArrayList<Celebrity> celebGameList;

  public CelebrityGame()
  {
    celebGameList = new ArrayList<Celebrity>();
    gameWindow = new CelebrityFrame(this);
  }
  
  public void prepareGame()
  {
    celebGameList = new ArrayList<Celebrity>();
    gameWindow.replaceScreen("START");
  }
  
  public boolean processGuess(String guess)
  {
    boolean matches = false;
    
    if (guess.trim().equalsIgnoreCase(gameCelebrity.getAnswer()))
    {
      matches = true;
      celebGameList.remove(0);
      if (celebGameList.size() > 0)
      {
        gameCelebrity = celebGameList.get(0);
      }
      else
      {
        gameCelebrity = new Celebrity("","");
      }
    }
    
    return matches;
  }

  public void play()
  {
    if (celebGameList != null && celebGameList.size() > 0)
    {
      this.gameCelebrity = celebGameList.get(0);
      gameWindow.replaceScreen("GAME");
    }
  }

  public void addCelebrity(String name, String guess, String type)
  {
    
    Celebrity currentCelebrity;
   
    currentCelebrity = new Celebrity(name, guess);
  
    this.celebGameList.add(currentCelebrity);
  }
  
  public boolean validateCelebrity(String name)
  {
    boolean validCelebrity = false;
    
    if (name.trim().length() >= 4)
    {
      validCelebrity = true;
    }
    
    return validCelebrity;
  }
  
  public boolean validateCelebrity2(String name)
  {
    if (name.trim().length() >= 4)
    {
      return true;
    }
    
    return false;
  }
  
  public boolean validateClue(String clue, String type)
  {
    boolean validClue = false;
    
    if (clue.trim().length() >= 10)
    {
      validClue = true;
      
    }
    
    return validClue;
  }
  
  public boolean validateClue2(String clue, String type)
  {
    if (clue.trim().length() >= 10)
    {
      return true;
    }
    
    return false;
  }
  
  public int getCelebrityGameSize()
  {
    return celebGameList.size();
  }

  public String sendClue()
  {
    return gameCelebrity.getClue();
  }
}