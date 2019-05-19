package modularity;



import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

// Just Elements
public class Node {
	
	public static String pattern = "#####.##";
	public static final DecimalFormat dformat = new DecimalFormat(pattern);

	
	private int elementId;
	private LinkedHashSet<Integer> communities;
	private HashSet<Node> neighborSet;
	private HashMap<Integer, HashSet<Node>> communityToNeighbors;
	private int degree;
	

	public Node(int elementId){
		super();
		this.neighborSet = new HashSet<Node>();
		this.elementId = elementId;
		this.communities = new LinkedHashSet<Integer>();
		this.communityToNeighbors = new HashMap<Integer, HashSet<Node>>();
	}
	
	
	public void insertNeighborhood(Node _nid){
		neighborSet.add(_nid);
	}
	
	public HashMap<Integer, HashSet<Node>> getCommunityToNeighbors(){
		return this.communityToNeighbors;
	}

	public int getElementId() {
		return elementId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

	public Set<Integer> getCommunitySet() {
		return communities;
	}

	public void addCommunity(int community) {
		this.communities.add(community);
	}

	public HashSet<Node> getNeighborSet() {
		return neighborSet;
	}
	
	public void addCommunityInfo(int adder){
		this.communities.add(adder);
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" #id=");
		builder.append(elementId);
		builder.append(", communities=");
		builder.append(communities);
		builder.append(",\t nbr=(");
		
		Iterator<Node> it = neighborSet.iterator();
		int j = 0;
		while(it.hasNext()){
			if(j==0){
				builder.append(it.next().elementId);				
			}
			else{				
				builder.append(","+it.next().elementId);
			}
			j++;
		}
		builder.append(")\t");
		
		builder.append("cmty lists about neighbors = ");
		for(Entry<Integer, HashSet<Node>>entry : communityToNeighbors.entrySet()){
			builder.append("[cmty="+entry.getKey()+"]={");
			Iterator<Node> ite = entry.getValue().iterator();
			while(ite.hasNext()){
				builder.append(ite.next().getElementId()+",");
			}
			builder.append("}");
		}
		
		builder.append("\t");

		
		return builder.toString();	
	}

	public void setDegree(){
		this.degree = this.neighborSet.size();
	}
	
	public int getDegree(){
		return this.degree;
	}

	// return exact number of community of neighbors of nodes
	public int getNumberOfNeighborsCmty() {
		return this.communityToNeighbors.size();
	}

	public void addNeighborCmty(int newCommunityLabel, Node node) {
		
		if(communityToNeighbors.containsKey(newCommunityLabel)){
			this.communityToNeighbors.get(newCommunityLabel).add(node);			
		}
		else{
			HashSet<Node> newCmty = new HashSet<Node>();
			newCmty.add(node);
			this.communityToNeighbors.put(newCommunityLabel, newCmty);
		}		
	}
}
