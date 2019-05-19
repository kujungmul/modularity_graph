package modularity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;


public class Main {	
	
	public static void main(String[] args) throws Exception{
		
		if(args.length < 2){
			System.out.println("Instruction : \n"
					+ "[RUN] java network.dat community.dat\n"
					);
			return;
		}
	
		// ARGUMENT SETTING
		String networkFileName = args[0];
		String communityFileName = args[1];
		
		// Graph Load
		Graph g = new Graph();
		g.readSocialNetwork(networkFileName);
		
		// Read Community File and Set community info
		g.readCommunity(communityFileName);
				
		// building communitySet
		CommunitySet cS = new CommunitySet();
		cS.setupCommunity(g.getContainer());
		
	
		double mod = calculateModularity(g, cS);
		
		double coverage = calculateCoverage(g);
	
		System.out.println(args[0]+"\t"+args[1]+"\t"+mod+"\t"+coverage);
	}
	
	
	private static double calculateCoverage(Graph g) {
		
		int cnt = 0;
		HashMap<Integer, Node> container = g.getContainer();
		for(Entry<Integer, Node> entry : container.entrySet()){
			Node cur = entry.getValue();
			if(cur.getCommunitySet().size() >= 1){
				cnt++;
			}
		}
		return (double)cnt / (double) container.size();
	}
	
	private static double calculateModularity(Graph g, CommunitySet cS) {
		
		HashMap<Integer, HashSet<Node>> communitySet = cS.getCommunityContainer();
		
		double m = g.getE();
		double q = 0;
		
		System.out.println("number of edges = "+m);
		
		for(Entry<Integer, HashSet<Node>> entry : communitySet.entrySet()){
			HashSet<Node> Cset = entry.getValue();
			Node[] Carr = new Node[Cset.size()];
			Cset.toArray(Carr);
			for(int i =0; i < Carr.length; i++) {
				Node u = Carr[i];
				for(int j = 0; j < Carr.length; j++) {
					Node v = Carr[j];
					int Auv = 0;
					if(u.getNeighborSet().contains(v)) {
						Auv = 1;
					}
					int ku = u.getDegree();
					int kv = v.getDegree();
					double subQ = Auv - ((ku*kv)/(2*m));
					q += subQ;
					System.out.println(u.getElementId()+"\t"+v.getElementId() + "\t"+subQ);
				}
			}
		}
		
		double mov = (1.0/(2.0*m)) * q;
		
		return mov;
	}
}
