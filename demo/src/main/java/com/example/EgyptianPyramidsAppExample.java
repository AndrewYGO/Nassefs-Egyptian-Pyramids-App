package com.example;

import java.util.*;
import org.json.simple.*;

public class EgyptianPyramidsAppExample {

  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;
  HashSet<String> pyramidContributors = new HashSet<String>();
  HashSet<String> pharaohHash = new HashSet<String>();
  HashSet<String> HashPyramidNameAndID = new HashSet<String>();
  public static int ID = 0;

  public static void main(String[] args) {
    // create and start the app
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  // main loop for app
  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until user quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(scan, command);
    }
  }

  // constructor to initialize the app and read commands
  public EgyptianPyramidsAppExample() {
    // read egyptian pharaohs
    String pharaohFile = "/Users/andre/Documents/GitHub/Nassefs-Egyptian-Pyramids-App/demo/src/main/java/com/example/pharaoh.json";
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

    // create and intialize the pharaoh array
    initializePharaoh(pharaohJSONArray);

    // read pyramids
    String pyramidFile = "/Users/andre/Documents/GitHub/Nassefs-Egyptian-Pyramids-App/demo/src/main/java/com/example/pyramid.json";
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);

  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      // add a new pharaoh to array
      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
    }
  }

  // initialize the pyramid array
  private void initializePyramid(JSONArray pyramidJSONArray) {
    // create array and hash map
    pyramidArray = new Pyramid[pyramidJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pyramidJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pyramidJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
      String[] contributors = new String[contributorsJSONArray.size()];
      for (int j = 0; j < contributorsJSONArray.size(); j++) {
        String c = contributorsJSONArray.get(j).toString();
        contributors[j] = c;
      }

      // add a new pyramid to array
      Pyramid p = new Pyramid(id, name, contributors);
      pyramidArray[i] = p;
    }
  }

  // get a integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    Integer result = Integer.parseInt(s);
    return result;
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    Character command = '_';

    String rawInput = scan.nextLine();

    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  // print all pharaohs
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case '2':
        System.out.print("Enter the id of the pharaoh: ");
        String input = scan.nextLine();
        ID = toInteger(input);
        searchPharaohByID(ID);

        break;
      case '3':
        listAllPyramids();
        break;
      case '4':
        System.out.print("Enter the id of the pyramid: ");
        input = scan.nextLine();
        toInteger(input);
        multiFunction();
        break;
      case '5':
        System.out.println("Name and ID of the pyramid: ");
        for (String element : HashPyramidNameAndID) {
          System.out.println(element);
        }

        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        // System.out.println("ERROR: Unknown commmand");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

  private static void printMenuLine() {
    System.out.println(
        "--------------------------------------------------------------------------");
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("Nassef's Egyptian Pyramids App");
    printMenuLine();
    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharaohs");
    printMenuCommand('2', "Displays a specific Egyptian pharaoh");
    printMenuCommand('3', "List all the pyramids");
    printMenuCommand('4', "Displays a specific Egyptian pyramid");
    printMenuCommand('5', "Displays a list of requested pyramids");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }

  // search for pyrmaid by name
  private void searchPyramidByName(int ID) {
    for (int i = 0; i < pyramidArray.length; i++) {
      if (pyramidArray[i].id == ID) {
        System.out.println(pyramidArray[i].name);
        break;
      }
    }
  }

  // create search for pharaohs by name
  private void searchPharaohByID(int ID) {

    for (int i = 0; i < pharaohArray.length; i++) {
      if (pharaohArray[i].id == ID) {
        pharaohArray[i].print();
        break;

      }
    }
  }

  // list all pyramids and details
  private void listAllPyramids() {
    for (int i = 0; i < pyramidArray.length; i++) {
      // Clear collections to avoid accumulation
      pyramidContributors.clear();
      pharaohHash.clear();
      printMenuLine();

      Pyramid currentPyramid = pyramidArray[i];
      ID = currentPyramid.id;

      pyramidContributors.addAll(Arrays.asList(currentPyramid.contributors));

      addPharaohToHashSet();
      intersection();
      PharaohAfterIntersection();

      printMenuLine();
    }
  }

  // find pryamid by id and add contributors to hashset
  private void searchPyramidByID(int id) {
    for (int i = 0; i < pyramidArray.length; i++) {
      if (pyramidArray[i].id == id) {
        pyramidContributors.addAll(Arrays.asList(pyramidArray[i].contributors));
        break;
      }
    }
  }

  // put pharaoh info in hashset
  private void addPharaohToHashSet() {
    for (int i = 0; i < pharaohArray.length; i++) {
      pharaohHash.add(pharaohArray[i].hieroglyphic);
    }
  }

  // find intersection of pharaohs and pyramids when it comes to contributors and
  // hieroglyphics
  private void intersection() {
    pharaohHash.retainAll(pyramidContributors);
  }

  // convert string to integer
  private Integer toInteger(String input) {
    Integer result = Integer.parseInt(input);
    ID = result;
    return ID;
  }

  // Display the pharaohs name and contribution and Pyramid name and id
  private void PharaohAfterIntersection() {
    int count = 1;
    int totalGold = 0;
    System.out.print("Pyramid ID: " + ID + "\n");
    System.out.print("Pyramid Name: " + pyramidArray[ID].name + "\n");
    for (int i = 0; i < pharaohArray.length; i++) {
      if (pharaohHash.contains(pharaohArray[i].hieroglyphic)) {
        System.out.println(
            "Contributor " + count + ": " + pharaohArray[i].name + " " + pharaohArray[i].contribution
                + " gold coins ");
        count++;
        totalGold += pharaohArray[i].contribution;
      }
    }
    System.out.println("Total Gold: " + totalGold + " gold coins\n");
  }

  // create hashset string for pyramid name and id
  private void PyramidNameAndId() {
    String PyramidName = pyramidArray[ID].name;
    String PyramidID = Integer.toString(ID);
    String combinedNameAndID = PyramidName + " " + PyramidID;
    HashPyramidNameAndID.add(combinedNameAndID);

  }

  // create method that will call searchPyramidByID, addPharaohToHashSet,
  // intersection, PharaohAfterIntersection, and PyramidNameAndId
  private void multiFunction() {
    searchPyramidByID(ID);
    addPharaohToHashSet();
    intersection();
    PharaohAfterIntersection();
    PyramidNameAndId();
  }
}
