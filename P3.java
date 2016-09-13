import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.*;
class Graph{
	ArrayList<ArrayList<Integer> > adjList;
	String[] vertex;
	int V;
	public Graph(int V){
		this.V=V;
		adjList = new ArrayList<ArrayList<Integer> > ();
		vertex = new String[V];
	}
	public String[] getVertex(){
		return vertex;
	}
	public ArrayList<ArrayList<Integer> > getAdjacencyList(){
		return adjList;
	}
}
class Parse{
	Graph g;
	public Parse(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("Graph.xml");
			NodeList nodeList = doc.getElementsByTagName("Node");
			g = new Graph(nodeList.getLength());
			for(int i=0;i<nodeList.getLength();i++){
				Node noden = nodeList.item(i);
				if(noden.getNodeType()==Node.ELEMENT_NODE){
					Element node = (Element) noden;
					//System.out.println(node.getAttribute("id"));
					NodeList childList = node.getChildNodes();
					ArrayList<Integer> adjacent = new ArrayList<Integer>();
					for(int j=0;j<childList.getLength();j++){
						Node childn = childList.item(j);
						if(childn.getNodeType()==Node.ELEMENT_NODE){
							Element child = (Element) childn;
							if(child.getTagName()=="Name"){
								g.vertex[i] = child.getTextContent();
								continue;
							}
							adjacent.add(Integer.valueOf(child.getTextContent()));
						}
					}
					g.adjList.add(adjacent);
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		/*for(int i=0;i<g.adjList.size();i++){
			for(int j=0;j<g.adjList.get(i).size();j++)
				System.out.print(g.adjList.get(i).get(j)+" ");
			System.out.println();
		}*/
	}
	public Graph getGraph(){
		return g;
	}
}
class TopologicalSort{
	Graph g;
	boolean visited[],cycle;
	Stack s;
	ArrayList<ArrayList<Integer> > adjList;
	public TopologicalSort(){
		Parse p = new Parse();
		g = p.getGraph();
		visited = new boolean[g.getVertex().length];
		for(int i=0;i<g.getVertex().length;i++)
			visited[i] = false;
		cycle = false;
		adjList = g.getAdjacencyList();
		for(int i=0;i<g.getVertex().length;i++){
			if(!visited[i]){
				isCycle(i);
			}
		}
		if(cycle){
			System.out.println("A Cycle Is Present!");
			System.exit(0);
		}
		for(int i=0;i<g.getVertex().length;i++)
			visited[i] = false;
		s = new Stack<Integer>();
		for(int i=0;i<g.getVertex().length;i++){
			if(!visited[i]){
				sort(i);
			}
		}
		while(!s.empty()){
			System.out.print(g.vertex[((Integer)s.pop()).intValue()] + " ");
		}
	}
	public void isCycle(int i){
		visited[i] = true;
		Iterator itr = adjList.get(i).iterator();
		while(itr.hasNext()){
			int u = ((Integer)itr.next()).intValue();
			if(visited[u]){
				cycle=true;
				return;
			}
			isCycle(u);
		}
		visited[i] = false;
	}
	public void sort(int i){
		visited[i] = true;
		//System.out.println(i);
		Iterator itr = adjList.get(i).iterator();
		while(itr.hasNext()){
			int u = ((Integer)itr.next()).intValue();
			if(!visited[u]){
				sort(u);
			}
		}
		s.push(new Integer(i));
	}
}
class P3{
	public static void main(String str[]){
		new TopologicalSort();
	}
}