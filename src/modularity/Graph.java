package modularity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class Graph {
	private HashMap<Integer, Node> container;
	int E;
	
	public Graph() {
		super();
		this.container = new HashMap<Integer, Node>();
	}

	public HashMap<Integer, Node> getContainer() {
		return container;
	}
	
	public void readCommunity(String communityFileName) throws Exception {
		
		BufferedReader reader = null;
		String line;
		String[] token;
		reader = new BufferedReader(new FileReader(communityFileName));
		
		while((line = reader.readLine()) != null){
			if(line.trim().length()==0){				continue;			}
			
			token = line.split("\\s+");
			
			int nodeId = Integer.parseInt(token[0]);

			container.get(nodeId).addCommunity(Integer.parseInt(token[1]));

		}
		reader.close();
	}

	public void readSocialNetwork(String filename) throws NumberFormatException, IOException {
		BufferedReader reader = null;
		String line;
		String[] token;
		reader = new BufferedReader(new FileReader(filename));
		
		while((line = reader.readLine()) != null){
			token = line.split("\\s+");
			this.insertNode(Integer.parseInt(token[0]), Integer.parseInt(token[1]));
		}
		reader.close();
		
		System.out.println("***************Data Load Finished!***************");
		System.out.println("Total Number of nodes = "+container.size());
		System.out.println("Total Number of edges = "+this.getTotalDegree()/2);			

		this.setDegree();
		E = this.getTotalDegree()/2;	
	}
	
	public int getE() {
		return E;
	}

	private void setDegree() {
		for(Entry<Integer, Node> t : container.entrySet()){
			t.getValue().setDegree();
		}
	}
	
	public void writeCommunity(CommunitySet cS, String fileName) throws IOException{
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(fileName));
		
		HashMap<Integer, HashSet<Node>> communityContainer = cS.getCommunityContainer();
		
		for(Entry<Integer, HashSet<Node>> t : communityContainer.entrySet()){
			if(t.getValue().size()==1){
				continue;
			}
			Iterator<Node> it = t.getValue().iterator();
			while(it.hasNext()){
				Node tt = it.next();
				
				writer.write(tt.getElementId()+" ");
			}
			writer.write("\n");
		}
		writer.close();
		
		System.out.println("Writing to "+fileName+" has finished!");
	}

	public int getTotalDegree(){
		int sum = 0;
		for(Entry<Integer, Node> t : container.entrySet()){
			sum += t.getValue().getNeighborSet().size();
		}
		return sum;
	}

	public void insertNode(int n1, int n2){
	
		if(!container.containsKey(n1)){
			Node e = new Node(n1);
			container.put(n1, e);
		}
		
		if(!container.containsKey(n2)){
			Node e = new Node(n2);
			container.put(n2, e);	
		}
		
		container.get(n1).insertNeighborhood(container.get(n2));
		container.get(n2).insertNeighborhood(container.get(n1));
	}
}
