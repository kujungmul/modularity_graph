package modularity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class CommunitySet {
	private HashMap<Integer, HashSet<Node>> communityContainer;
	private HashMap<Integer, Integer> communitySelfEdges;
	
	public CommunitySet(){
		this.communityContainer = new HashMap<Integer, HashSet<Node>>();
		this.communitySelfEdges = new HashMap<Integer, Integer>();
	}
	
	public HashMap<Integer, Integer> getCommunitySelfEdges() {
		return communitySelfEdges;
	}

	public HashMap<Integer, HashSet<Node>> getCommunityContainer(){
		return this.communityContainer;
	}

	public void setupCommunity(HashMap<Integer, Node> container) {
		
		// SETUP COMMUNITY TO NODES
		for(Entry<Integer, Node> t : container.entrySet()){
			Iterator<Integer> ccm = t.getValue().getCommunitySet().iterator();
			while(ccm.hasNext()){
				int currentCmty = ccm.next();
				if(!communityContainer.containsKey(currentCmty)){
					HashSet<Node> newSet = new HashSet<Node>();
					newSet.add(t.getValue());
					communityContainer.put(currentCmty, newSet);
				}
				else{
					communityContainer.get(currentCmty).add(t.getValue());
				}
			}
		}

		// SETUP COMMUNITY TO NUMBER OF EDGES
		for(Entry<Integer, HashSet<Node>> entry : communityContainer.entrySet()){
			
			int sumOfWeight = 0;
			Iterator<Node> it = entry.getValue().iterator();
			while(it.hasNext()){	// the value it means node
				Node t = it.next();
				if(t.getCommunityToNeighbors().containsKey(entry.getKey()))
					sumOfWeight = sumOfWeight + t.getCommunityToNeighbors().get(entry.getKey()).size();
			}
			communitySelfEdges.put(entry.getKey(), sumOfWeight);
		}		
	}
}
