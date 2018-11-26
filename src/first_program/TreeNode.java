package first_program;

public class TreeNode {

	public Node node;
	public String nodesToVisit;
	public String visitedNodes;
	public Integer totalCost;

	public TreeNode(Node node, String nodesToVisit, String visitedNodes, Integer totalCost) {
		this.node = node;
		this.nodesToVisit = nodesToVisit;
		this.visitedNodes = visitedNodes;
		this.totalCost = totalCost;
	}

	public void visitNode(String nodeName, Integer cost) {
		nodesToVisit = new StringBuilder(nodesToVisit).deleteCharAt(nodesToVisit.indexOf(nodeName)).toString();
		visitedNodes += nodeName;
		totalCost += cost;
	}
}
