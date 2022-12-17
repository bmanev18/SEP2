package shared.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwearDetectionTest
{
  @Test public void checkSwear()
  {
    SwearDetection swearDetection = new SwearDetection();

    assertTrue(swearDetection.checkSwear("crap")); //Checking single String

    assertTrue(swearDetection.checkSwear(
        "thats a bunch of crap")); //Checking String with multiple words separated by spaces

    assertTrue(swearDetection.checkSwear(
        "thats a bunch of cRaP")); //Checking string with different case letters

  }

  @Test public void replaceSwear()
  {
    SwearDetection swearDetection = new SwearDetection();

    assertEquals("c***", swearDetection.changeSwearMap(
        "crap")); //Checking that method replaces profanity

    assertEquals("thats a bunch of c***", swearDetection.changeSwearMap(
        "thats a bunch of crap")); //Checking that profanity filter filters String separated by spaces

    assertEquals("thats a f*** bunch of c***", swearDetection.changeSwearMap(
        "thats a fucking bunch of crap"));//Checking that multiple profanities are filtered in a single String

    assertEquals("thats a bunch of c***", swearDetection.changeSwearMap(
        "thats a bunch of CrAp")); //Checking that due to different case per character filter still detects swears

  }

}