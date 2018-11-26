package first_program;

public class Route {

	public Node fromNode;
	public Node toNode;
	public Integer cost;

	public Route(Node fromNode, Node toNode, Integer cost) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.cost = cost;
	}
}
