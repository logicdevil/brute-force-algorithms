package first_program;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {

		HashMap<String, Node> nodesByNames = new HashMap<>();
		ArrayList<Route> routes = new ArrayList<>();
		String firstNodeName = "";
		routes = readRoutesFromFile(routes, nodesByNames);

		for (Route singleRoute : routes) {
			singleRoute.fromNode.addRoute(singleRoute);
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("Enter the name of the first Node: ");
			firstNodeName = reader.readLine();
		} catch (IOException e) {
			System.out.println("Unhandled error occurred.");
		}

		ArrayList<TreeNode> fullPaths = createPathsTree(routes, nodesByNames, firstNodeName);

		printShortestPath(fullPaths);
	}

	public static ArrayList<Route> createRoutes(ArrayList<Route> routes, HashMap<String, Node> nodesByNames, String[] stringRoute) {

		Node firstNode = nodesByNames.get(stringRoute[0]);
		Node secondNode = nodesByNames.get(stringRoute[1]);
		if (firstNode == null) {
			firstNode = new Node(stringRoute[0]);
			nodesByNames.put(stringRoute[0], firstNode);
		}
		if (secondNode == null) {
			secondNode = new Node(stringRoute[1]);
			nodesByNames.put(stringRoute[1], secondNode);
		}

		routes.add(new Route(firstNode, secondNode, Integer.valueOf(stringRoute[2])));
		routes.add(new Route(secondNode, firstNode, Integer.valueOf(stringRoute[2])));

		return routes;
	}

	public static ArrayList<Route> readRoutesFromFile(ArrayList<Route> routes, HashMap<String, Node> nodesByNames) {

		BufferedReader reader = null;
		FileReader fileReader = null;

		try {
			fileReader = new FileReader("./src/routes.txt");
			reader = new BufferedReader(fileReader);
			String line;
			do {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				String[] stringRoute = line.split("\\|");
				routes = createRoutes(routes, nodesByNames, stringRoute);
			} while (line != null);
		} catch (IOException e) {
			System.out.println("Error while trying to read file.");
			System.exit(1);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException exception) {
				System.out.println("Unhandled exception while trying to close reader");
				System.exit(1);
			}
		}

		return routes;
	}

	public static ArrayList<TreeNode> createPathsTree(ArrayList<Route> routes, HashMap<String, Node> nodesByNames, String firstNodeName) {

		ArrayList<TreeNode> fullPaths = new ArrayList<>();


		Integer depth = nodesByNames.size();
		String nodesToVisit = "";

		for (Node singleNode : nodesByNames.values()) {
			nodesToVisit += singleNode.nodeName;
		}

		fullPaths.add(new TreeNode(nodesByNames.get(firstNodeName), nodesToVisit, firstNodeName, 0));
		System.out.println("Nodes at the level #1:\n" + fullPaths.get(0).visitedNodes + ":" + fullPaths.get(0).totalCost);

		for (Integer i = 0; i < depth; i++) {
			ArrayList<TreeNode> tempList = fullPaths;
			fullPaths = new ArrayList<>();

			for (TreeNode singleTreeNode : tempList) {
				fullPaths.addAll(getTreeNodesForNode(singleTreeNode, i, depth, firstNodeName));
			}

			System.out.println("\nNodes at the level #" + (i + 2) + ":");
			for(TreeNode singleTreeNode : fullPaths) {
				System.out.println(singleTreeNode.visitedNodes + ":" + singleTreeNode.totalCost);
			}
		}

		return fullPaths;
	}

	public static ArrayList<TreeNode> getTreeNodesForNode(TreeNode singleTreeNode, Integer i, Integer depth, String firstNodeName) {

		ArrayList<TreeNode> fullPaths = new ArrayList<>();
		Node singleNode = singleTreeNode.node;

		for (Route singleRoute : singleNode.listOfRoutes) {
			if (singleTreeNode.nodesToVisit.contains(singleRoute.toNode.nodeName)) {
				if (singleRoute.toNode.nodeName.equals(firstNodeName) && i != (depth - 1)) {
					continue;
				}
				TreeNode newTreeNode = new TreeNode(singleRoute.toNode, singleTreeNode.nodesToVisit, singleTreeNode.visitedNodes, singleTreeNode.totalCost);
				newTreeNode.visitNode(singleRoute.toNode.nodeName, singleRoute.cost);
				fullPaths.add(newTreeNode);
			}
		}

		return fullPaths;
	}

	public static void printShortestPath(ArrayList<TreeNode> fullPaths) {

		TreeNode shortestPath = fullPaths.get(0);
		for (int i = 1; i < fullPaths.size(); i++) {
			if(fullPaths.get(i).totalCost < shortestPath.totalCost) {
				shortestPath = fullPaths.get(i);
			}
		}

		System.out.println("\n\nThe shortest path: " + shortestPath.visitedNodes + " with a total cost of " + shortestPath.totalCost);
	}
}
