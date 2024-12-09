package shared.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwearDetection
{
  private ArrayList<String> restrictedWords;
  private Map<String, String> restrictedMap;

  public SwearDetection()
  {
    restrictedWords = new ArrayList<>();
    restrictedMap = new HashMap<>();
    addRestrictedMap("crap", "c***");
    addRestrictedMap("fuck", "f***");
  }

  public void addRestrictedMap(String key, String value)
  {
    restrictedMap.put(key, value);
  }

  public String changeSwearMap(String word)
  {
    ArrayList<String> splitword = new ArrayList<String>(
        List.of(word.split(" ")));
    for (int x = 0; x < splitword.size(); x++)
    {
      for (String key : restrictedMap.keySet())
      {
        if (splitword.get(x).toLowerCase().contains(key))
        {
          String replaceWord = restrictedMap.get(key);
          splitword.set(x, replaceWord);
        }
      }
    }
    String finalWord = "";

    for (int z = 0; z < splitword.size(); z++)
    {
      finalWord = finalWord + " " + splitword.get(z);
    }

    finalWord = finalWord.trim();

    return finalWord;
  }

  public boolean checkSwear(String word)
  {
    Map<String, String> restrictedStuff = new HashMap<>();
    restrictedStuff.put("fuck", "f***");
    restrictedStuff.put("shit", "s***");
    restrictedStuff.put("bitch", "b****");
    restrictedStuff.put("crap", "c***");

    ArrayList<String> splitword = new ArrayList<String>(
        List.of(word.split(" ")));

    for (int x = 0; x < splitword.size(); x++)
    {
      for (String key : restrictedStuff.keySet())
      {
        if (splitword.get(x).toLowerCase().contains(key))
        {
          return true;
        }
      }
    }
    return false;
  }

  public static String changeSwearStatic(String word)
  {
    Map<String, String> restrictedStuff = new HashMap<>();
    restrictedStuff.put("fuck", "f***");
    restrictedStuff.put("shit", "s***");
    restrictedStuff.put("bitch", "b****");
    restrictedStuff.put("crap", "c***");

    ArrayList<String> splitword = new ArrayList<String>(
        List.of(word.split(" ")));

    for (int x = 0; x < splitword.size(); x++)
    {
      for (String key : restrictedStuff.keySet())
      {
        if (splitword.get(x).toLowerCase().contains(key))
        {
          String replaceWord = restrictedStuff.get(key);
          splitword.set(x, replaceWord);
        }
      }
    }
    String finalWord = "";

    for (int z = 0; z < splitword.size(); z++)
    {
      finalWord = finalWord + " " + splitword.get(z);
    }

    finalWord = finalWord.trim();

    return finalWord;
  }
}





