import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwearDetection
{
  private ArrayList<String> restrictedWords;
  private Map<String,String>restrictedMap;

  public SwearDetection()
  {
    restrictedWords=new ArrayList<>();
    restrictedMap=new HashMap<>();
    addRestrictedMap("crap","c***");
    addRestrictedMap("fuck","f***");
  }

  public void addRestrictedMap(String key,String value)
  {
    restrictedMap.put(key, value);
  }

  public String changeSwearMap(String word)
  {
    ArrayList<String> splitword=new ArrayList<String>(List.of(word.split(" ")));
    for(int x=0;x<splitword.size();x++)
    {
      for(String key:restrictedMap.keySet())
      {
        if(splitword.get(x).toLowerCase().contains(key))
        {
          String replaceWord=restrictedMap.get(key);
          splitword.set(x,replaceWord);
        }
      }
    }
    String finalWord="";

    for(int z=0;z<splitword.size();z++)
    {
      finalWord=finalWord+ " " +splitword.get(z);
    }

    finalWord=finalWord.trim();

    return finalWord;
  }

/*
  public void addRestrictedWord(String swear)
  {
    restrictedWords.add(swear);
  }

  public String changeSwear(String word)
  {
    ArrayList<String> splitWord=new ArrayList<String>(List.of(word.split(" ")));

    for(int x=0;x<splitWord.size();x++)
    {
      for(int y=0;y<restrictedWords.size();y++)
      {
        if(splitWord.get(x).toLowerCase().contains(restrictedWords.get(y)))
        {
          String moderatedWord=splitWord.get(x).toLowerCase().replace(restrictedWords.get(y),"[PROFANITY]");
          splitWord.set(x,moderatedWord);
        }
      }
    }
    String finalWord="";

    for(int z=0;z<splitWord.size();z++)
    {
      finalWord=finalWord+ " " +splitWord.get(z);
    }

    finalWord=finalWord.trim();

    return finalWord;
  }

  public boolean detectSwear(String word)
  {
    ArrayList<String> splitWord=new ArrayList<String>(List.of(word.split(" ")));

    for(int x=0;x<splitWord.size();x++)
    {
      for(int y=0;y<restrictedWords.size();y++)
      {
        if(splitWord.get(x).toLowerCase().contains(restrictedWords.get(y)))
        {
          return true;
        }
      }
    }
    return false;
  }

/*
  public boolean detectSwear(String word)
  {
    for (int x = 0; x < restrictedWords.size(); x++)
    {
      if (word.contains(restrictedWords.get(x)))
      {
        return true;
      }
    }
    return false;
  }

  public String changeSwear(String word)
  {
    for(int x=0;x<restrictedWords.size();x++)
    {
      if(word.contains(restrictedWords.get(x)))
      {
        word=word.replace(restrictedWords.get(x),"*");
      }
    }
    return word;
  }
*/

}



