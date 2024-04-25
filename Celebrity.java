package SkillsProject;

public class Celebrity
{
  private String clue;
  private String answer;
  
  public Celebrity(String answer, String clue)
  {
    this.clue = clue;
    this.answer = answer;
  }
  
  public String getClue()
  {
    return clue;
  }
  
  public String getAnswer()
  {
    return answer;
  }
  
  public void setClue(String clue)
  {
    this.clue = clue;
  }
  
  public void setAnswer(String answer)
  {
    this.answer = answer;
  }

  @Override
  public String toString()
  {
    String description = "The Person's name is: " + answer + 
      ". The clue for this person is: " + clue;
    
    return description;
  }
}