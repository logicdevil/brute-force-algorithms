package second_program;

import first_program.Node;
import first_program.Route;
import first_program.TreeNode;
import jdk.nashorn.api.tree.Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {

		HashMap<String, Node> nodesByNames = new HashMap<>();
		ArrayList<Route> routes = new ArrayList<>();
		String firstNodeName = "";
		routes = first_program.Main.readRoutesFromFile(routes, nodesByNames);

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

		for(Integer i = 0; i < fullPaths.size(); i++) {
			System.out.println("Path #" + (i + 1) + ": " + fullPaths.get(i).visitedNodes + " : " + fullPaths.get(i).totalCost);
		}

		first_program.Main.printShortestPath(fullPaths);
	}

	public static ArrayList<TreeNode> createPathsTree(ArrayList<Route> routes, HashMap<String, Node> nodesByNames, String firstNodeName) {

		ArrayList<TreeNode> fullPaths = new ArrayList<>();

		Integer depth = nodesByNames.size();
		String nodesToVisit = "";

		for (Node singleNode : nodesByNames.values()) {
			nodesToVisit += singleNode.nodeName;
		}

		TreeNode rootOfTheTree = new TreeNode(nodesByNames.get(firstNodeName), nodesToVisit, firstNodeName, 0);

		fullPaths.addAll(getTreeNodesForNode(rootOfTheTree, firstNodeName, depth, 0));

		return fullPaths;
	}

	public static ArrayList<TreeNode> getTreeNodesForNode(TreeNode singleTreeNode, String firstNodeName, Integer depth, Integer currentLevel) {


		ArrayList<TreeNode> fullPaths = new ArrayList<>();
		Node singleNode = singleTreeNode.node;

		if(singleTreeNode.nodesToVisit.isEmpty()) {
			fullPaths.add(singleTreeNode);
			return fullPaths;
		}

		for (Route singleRoute : singleNode.listOfRoutes) {
			if (singleTreeNode.nodesToVisit.contains(singleRoute.toNode.nodeName)) {
				if (singleRoute.toNode.nodeName.equals(firstNodeName) && currentLevel != (depth - 1)) {
					continue;
				}
				TreeNode newTreeNode = new TreeNode(singleRoute.toNode, singleTreeNode.nodesToVisit, singleTreeNode.visitedNodes, singleTreeNode.totalCost);
				newTreeNode.visitNode(singleRoute.toNode.nodeName, singleRoute.cost);
				fullPaths.addAll(getTreeNodesForNode(newTreeNode, firstNodeName, depth, currentLevel + 1));
			}
		}

		return fullPaths;
	}
}
