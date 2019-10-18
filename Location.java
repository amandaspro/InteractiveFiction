import java.util.ArrayList;

import java.util.HashMap;

public class Location

{
	public String name;
	public String description;
	public boolean isVisited;
	public boolean hasOb = false;
	public HashMap<String, Location> exits = new HashMap<String, Location>();
	static public HashMap<String, String> map = new HashMap<String, String>();
	
	public Obstacle ob;

	static {
		map.put("north", "n");
		map.put("south", "s");
		map.put("east", "e");
		map.put("west", "w");
		map.put("northwest", "nw");
		map.put("north west", "nw");
		map.put("northeast", "ne");
		map.put("north east", "ne");
		map.put("southwest", "sw");
		map.put("south west", "sw");
		map.put("southeast", "se");
		map.put("south east", "se");
		map.put("n", "n");
		map.put("s", "s");
		map.put("e", "e");
		map.put("w", "w");
		map.put("ne", "ne");
		map.put("nw", "nw");
		map.put("se", "se");
		map.put("sw", "sw");
	}

	public ArrayList<Item> items = new ArrayList<Item>();
	public Location(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	public void addObstacle(String name, String description) {
		hasOb = true;
		Obstacle obst = new Obstacle(name, description);
		ob = obst;
	}
	
	public void obDone() {
		hasOb = false;
		
	}

	public class Obstacle {
		public String obName;
		public String obDescription;
		public boolean isDone;
		public Obstacle(String name, String description) {
			this.obName = name;
			this.obDescription = description;
		}

		public String toString() {
			return obName;
		}
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if (!isVisited) {
			sb.append("\n" + description);
			isVisited = true;
		}
		if (hasOb)
		{
			sb.append("\nAt " + name + " you see a " + ob.obName);
			sb.append("\n" + ob.obDescription);
			
		}

		if (!exits.isEmpty()) {
			sb.append("\nPossible exits: ");
			for (String direction : exits.keySet())
			{
				sb.append(direction + " ");
			}
		}

		if (!items.isEmpty()) {
			sb.append("\nYou see: ");
			for (Item item : items)
			{
				sb.append(item + " ");
			}
		}
		return sb.toString();
	}
}