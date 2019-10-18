public class Item {
	public String name;
	public String description;
	public boolean holding;
	public Location location;

	public Item(String name, String description, Location location) {
		this.name = name;
		this.description = description;
		holding = false;
		this.location = location;
	}

	public void get(Location playerLocation) {
		if (holding == false) {
			if (playerLocation == location) {
				holding = true;
				location = null;
			}
			else {
				System.out.println("That item is not at this location");
			}
		}
		else
		{
			System.out.println("You are already holding that item");
		}
	}

	public String toString() {
		return name;
	}

}
