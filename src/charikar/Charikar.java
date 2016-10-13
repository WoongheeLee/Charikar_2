package charikar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

public class Charikar {
	public static void main(String[] args) {
		double time = System.currentTimeMillis();
		
		String data = "facebook";
		
		UndirectedGraph<String, DefaultEdge> graphG = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		
		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("./"+data+"/"+data+" edges.txt"));
			while((line = reader.readLine()) != null) {
//				String[] edges = line.split("\t"); // 엔론, 아마존, 유투브 데이터 디리미터
				String[] edges = line.split(" "); // 페이스북 데이터용 디리미터
				graphG.addVertex(edges[0]);
				graphG.addVertex(edges[1]);
				if(!edges[0].equals(edges[1])) {
					graphG.addEdge(edges[0], edges[1]);
				}
//				System.out.println(edges[0] + " " + edges[1]);
			}
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(data+" log.csv"));
			
			while(graphG.vertexSet().size() > 0) {
				int minDegree = graphG.edgeSet().size();
				String minNode = null;
				
				Iterator<String> itr = graphG.vertexSet().iterator();
				while(itr.hasNext()) {
					String vtx = itr.next();
					
					if(graphG.degreeOf(vtx) <= minDegree) {
						minDegree = graphG.degreeOf(vtx);
						minNode = vtx;
					}
				}
				System.out.println(graphG.vertexSet().size()+"\t" +(double)graphG.edgeSet().size() / (double)graphG.vertexSet().size());
				bw.write((double)graphG.edgeSet().size() / (double)graphG.vertexSet().size()+"\n");
				bw.flush();
				
				graphG.removeVertex(minNode);
			}
			bw.close();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		
		System.out.println(System.currentTimeMillis() - time);
	}
}
