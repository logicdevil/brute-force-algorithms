package first_program;

import java.util.ArrayList;

public class Node {

	public String nodeName;
	public ArrayList<Route> listOfRoutes;

	public Node(String nodeName) {
		this.nodeName = nodeName;
		listOfRoutes = new ArrayList<>();
	}

	public void addRoute(Route routes) {
		this.listOfRoutes.add(routes);
	}
}
