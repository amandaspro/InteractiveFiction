import java.util.ArrayList;
import java.util.Scanner;


public class IFGame {
	public Scanner in = new Scanner(System.in);
	public boolean echoOn = false;
	public Location location;
	public ArrayList<Item> allItems = new ArrayList<Item>();
	public ArrayList<Item> yourItems = new ArrayList<Item>();
	public String[] directions = new String[]{"n", "s", "e", "w", "nw", "ne", "sw", "se", "north", "south", "east",
			"west", "northeast", "northwest", "southeast", "southwest"};
	Location cub;
	Location glat;
	Location insideLibrary;
	Location servo;
	Location stine;
	Location plank;
	Location outsideLibrary;
	
	public IFGame(boolean echoOn)
	{
		this.echoOn = echoOn;
		initialize();
		play();
	}

	private void initialize()
	{
		servo = new Location("Servo", 
				"The best college food in PA");
		cub = new Location("CUB", 
				"The heart of college life at Gettysburg");
		stine = new Location("Stine Hall", 
				"Residental hall for first years");
		plank = new Location("Plank Gym", 
				"This old gym is now the location of many classes");
		glat = new Location("Glatfelter", 
				"This tall orange bilding is home to the best department of campus; computer science");
		outsideLibrary = new Location("Library", 
				"This is the outside of Gettysburg's famous Library, it is currently closed");
		insideLibrary = new Location("Library", 
				"Welcome in side!");

		servo.exits.put("sw", cub);
		servo.exits.put("se", stine);
		servo.exits.put("s", glat);
		cub.exits.put("ne", servo);
		cub.exits.put("e", stine);
		cub.exits.put("s", plank);
		stine.exits.put("nw", servo);
		stine.exits.put("w", cub);
		stine.exits.put("s", outsideLibrary);
		plank.exits.put("n", cub);
		plank.exits.put("se", glat);
		plank.exits.put("e", outsideLibrary);
		glat.exits.put("n", servo);
		glat.exits.put("nw", plank);
		glat.exits.put("ne", outsideLibrary);
		outsideLibrary.exits.put("sw", glat);
		outsideLibrary.exits.put("w", plank);
		outsideLibrary.exits.put("n", stine);
		location = servo;
		//Item squirrel = new Item("squirrel", "You see a squirrel");
		//servo.items.add(squirrel);
		//Item acorn = new Item("acorn", "It's just an acorn");
		//stine.items.add(acorn);

		Item book = new Item("book", "This is a Gettysburg Library Book that must be returned", cub);
		cub.items.add(book);
		allItems.add(book);
		Item cookie = new Item("cookie", "It is a delicious servo cookie", servo);
		servo.items.add(cookie);
		allItems.add(cookie);
		Item key = new Item("key", "This is a magical key that can only open one door on campus", stine);
		stine.items.add(key);
		allItems.add(key);
		glat.addObstacle("Secret Door", "The is a big wooden door");
		
		cub.addObstacle("squirrel", "There is a hungry squirrel sitting on the book");
		glat.addObstacle("Secret Tunnel", "This old tunnel leads to the library but, it is locked");
	}

	private void play()
	{
		while (true)
		{
			System.out.println(location);
			System.out.print("? ");
			String command = in.nextLine();
			String[] args = command.split(" ");
			if (echoOn)
			{
				System.out.println(command);
			}
			if (command.equals("quit"))
				System.exit(0);
			else if (command.equals("look"))
				location.isVisited = false;
			else if (args[0].equals("x") || args[0].equals("examine")) {
				for (Item i : yourItems)
				{
					if (i.name.equals(args[1]))
					{
						System.out.println(i.description);
					}
				}
			}
			else if (args[0].equals("get") || args[0].equals("take")) {
				if (args[1].equals("book") && cub.hasOb == true) {
					System.out.println("You can't pick the book up a squirrel is on it");
				}
				else {
					get(args[1]);
					System.out.println("You picked the " + args[1] + " up");
				}
			}
			else if (args[0].equals("drop")) {
				Item myItem = null; 
				for (Item i : yourItems) {
					if (i.name.equals(args[1])) {
						location.items.add(i);
						myItem = i;
					}
				}
				if (myItem != null)
				{
					yourItems.remove(myItem);
				}
			}
			else if (command.equals("inventory") || command.equals("i")) {
				StringBuilder sb = new StringBuilder();
				for (Item i : yourItems) {
					sb.append(i.name);
					sb.append(" ");
				}
				if (yourItems.size() == 0)
					System.out.println("You don't have any items");
				else {
					System.out.println("You are currently holding a " + sb);
				}
			}
			else  if (args[0].equals("go") || Location.map.containsKey(args[0])) {
				for (String s : directions) {
					if (args[0].equals("go") && args[1].equals(s)) 
					{
						if (Location.map.containsKey(args[1])) {
							s = Location.map.get(s);
						}
						Location nextLocation = location.exits.get(s);
						if (nextLocation == null)
							System.out.println("you can't go that way.");
						else {
							location = nextLocation;
						}
					}
					else if (args[0].equals(s)) {
						if (Location.map.containsKey(args[0])) {
							s = Location.map.get(s);
						}
						Location nextLocation = location.exits.get(s);
						if (nextLocation == null)
							System.out.println("you can't go that way.");
						else {
							location = nextLocation;
						}
					}
				}
			}
			else if (args[0].equals("use") && args[2].equals("with"))
			{
				if (args[1].equals("cookie") && args[3].equals("squirrel")) {
					cub.hasOb = false;
				}
				else if (args[1].equals("key") && args[3].equals("tunnel")) {
					glat.hasOb = false;
					location = insideLibrary;
				}
				yourItems.remove(args[1]);
				
			}
			else if (args[0].equals("return") && args[1].equals("book"))
			{
				System.out.println("The book is returned! You Win!");
				System.exit(0);
			}
			else if (command.equals("restart"))
			{
				cub.hasOb = true; 
				glat.hasOb = true;
				location = servo;
				cub.isVisited = false;
				servo.isVisited = false;
				stine.isVisited = false;
				plank.isVisited = false;
				outsideLibrary.isVisited = false;
				insideLibrary.isVisited = false;
				glat.isVisited = false;
				for (int i = 0; i < yourItems.size(); ++i)
				{
					if (yourItems.get(i).name.equals("book"))
					{
						cub.items.add(yourItems.get(i));
						yourItems.get(i).location = cub;
					}
					else if (yourItems.get(i).name.equals("key"))
					{
						stine.items.add(yourItems.get(i));
						yourItems.get(i).location = stine;

					}
					else if (yourItems.get(i).name.equals("cookie"))
					{
						servo.items.add(yourItems.get(i));
						yourItems.get(i).location = servo;

					}
					yourItems.get(i).holding = false;
				}
				yourItems.clear();	
			}
			else {
				System.out.println("I'm sorry, I didn't understand that");
			}
		}
	}

	public void get(String name)
	{
		for (Item i : allItems) {
			if (i.name.equals(name)) {
				i.get(location);
				location.items.remove(i);
				yourItems.add(i);
			}
		}
	}

	public static void main(String[] args) {
		new IFGame(args.length > 0 && args[0].equals("-e"));
	}


}