

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsc.contingencytables.ContingencyTable2x2;
import jsc.contingencytables.FishersExactTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class Outputs
 */
@WebServlet("/Outputs")
public class Outputs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Outputs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();	
		String[] selectedgenes1 = request.getParameterValues("choices1");
		String[] selectedgenes2 = request.getParameterValues("choices2");
		
		Gson gson = new GsonBuilder().create();
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<String> nodeNames = new ArrayList<String>();
		ArrayList<Link> links = new ArrayList<Link>();
		
		for(int i = 0; i<selectedgenes1.length;i++){
			String name = selectedgenes1[i];
			if(!nodeNames.contains(name)){
				nodeNames.add(name);				
			}
		}
		
		for(int i = 0; i<selectedgenes2.length; i++){
			String name = selectedgenes2[i];
			if(!nodeNames.contains(name)){
				nodeNames.add(name);				
			}
					
		}
		
		GetPPIconnection getData = new GetPPIconnection(nodeNames);
		getData.getGenePairs();
		List<String> validatedGenes = getData.getValidatedGenes();
		
		for(int i = 0; i< validatedGenes.size(); i++){
			for(String item1:selectedgenes1){
				if(validatedGenes.get(i).equalsIgnoreCase(item1.trim())){
					Node node = new Node(validatedGenes.get(i), 1);
					nodes.add(node);
				}
			}
			for(String item2:selectedgenes2){
				if(validatedGenes.get(i).equalsIgnoreCase(item2.trim())){
					Node node = new Node(validatedGenes.get(i), 2);
					nodes.add(node);
				}
			}
			
		}//end of validatedGenes for
		
		HashMap<String, Double> validatedGenePairs = getData.getHashGenePairs();
		Set set = validatedGenePairs.entrySet();
		Iterator iterator = set.iterator();
		
		DecimalFormat df = new DecimalFormat("#.##");
		
//		for(String val:validatedGenes){
//			System.out.println(val);
//			
//		}
		
		while(iterator.hasNext()){
			Map.Entry mentry = (Map.Entry)iterator.next();
			String genePairKey = (String) mentry.getKey();
			String[] genePair = genePairKey.split(",");
			
			System.out.println("genePair0 " + genePair[0]);
			System.out.println("genePair1 " + genePair[1]);
			
			int source = validatedGenes.indexOf(genePair[0]);
			int target = validatedGenes.indexOf(genePair[1]);
			
			double genePairValue = Double.valueOf(df.format(((Double)mentry.getValue()).doubleValue()));
			
			Link link = new Link(source, target, genePairValue);
			links.add(link);
		}
		
		graphJson gjson = new graphJson(nodes, links);
		String graphJn = gson.toJson(gjson);
		
		request.setAttribute("jsonString", graphJn);
		
		
		GetPathwayEnrichment getPathwayData = new GetPathwayEnrichment();
		getPathwayData.getEachPathwayGenes();
		HashMap<String, ArrayList<String>> pathway_gene_dict = getPathwayData.getPathway_gene_dict();
		
		//System.out.println("pathwaySize: " + pathway_gene_dict.size());	
		
		int pathwaySize = pathway_gene_dict.size();
		
		Set set1 = pathway_gene_dict.entrySet();
		Iterator iterator1 = set1.iterator();
		
//		int count1 = 0;
//		int count2 = 0;
		
		ArrayList<PathwayInfo> pathwayLists1 = new ArrayList<PathwayInfo>();
		ArrayList<PathwayInfo> pathwayLists2 = new ArrayList<PathwayInfo>();
		
		while(iterator1.hasNext()){
			Map.Entry mentry = (Map.Entry) iterator1.next();
			String pathway = (String) mentry.getKey();
			ArrayList<String> pathwayGenes = (ArrayList<String>) mentry.getValue();
			ArrayList<String> intersection1 = new ArrayList<String>();
			ArrayList<String> intersection2 = new ArrayList<String>();
			
			for(int i = 0; i<pathwayGenes.size(); i++){
				for(int j = 0; j<selectedgenes1.length; j++){
					if(pathwayGenes.get(i).trim().equalsIgnoreCase(selectedgenes1[j].trim())){
						intersection1.add(selectedgenes1[j].trim());
					}
					
				}
				for(int k = 0; k<selectedgenes2.length;k++){
					if(pathwayGenes.get(i).trim().equalsIgnoreCase(selectedgenes2[k].trim())){
						intersection2.add(selectedgenes2[k].trim());	
					}
					
				}
			}//end big for
		
			
			if(intersection1.size() != 0){
				double occurrence1 = (double) intersection1.size()/pathwayGenes.size();
				int[][] matrix1 = new int[2][2];
				
				matrix1[0][0] = intersection1.size();
				matrix1[0][1] = selectedgenes1.length;
				matrix1[1][0] = pathwayGenes.size();
				matrix1[1][1] = 20198;
				ContingencyTable2x2 contigencyTable1 = new ContingencyTable2x2(matrix1);
				FishersExactTest fisherTest1 = new FishersExactTest(contigencyTable1);
				
				PathwayInfo pathwayInfo = new PathwayInfo(pathway, fisherTest1.getSP(), occurrence1);
				pathwayLists1.add(pathwayInfo);
				//count1 ++;
			}
			
			if(intersection2.size() != 0){
				double occurrence2 = (double) intersection2.size()/pathwayGenes.size();
				int[][] matrix2 = new int[2][2];
				
				matrix2[0][0] = intersection2.size();
				matrix2[0][1] = selectedgenes2.length;
				matrix2[1][0] = pathwayGenes.size();
				matrix2[1][1] = 20198;
				ContingencyTable2x2 contigencyTable2 = new ContingencyTable2x2(matrix2);
				FishersExactTest fisherTest2 = new FishersExactTest(contigencyTable2);
				//System.out.println("fisherTest: " + fisherTest2.getSP());
				PathwayInfo pathwayInfo = new PathwayInfo(pathway, fisherTest2.getSP(), occurrence2);
				pathwayLists2.add(pathwayInfo);
				//count2 ++;
			}
		}// end while
		
		//System.out.print("fisher done");
		request.setAttribute("pathwayLists1", pathwayLists1);
		request.setAttribute("pathwayLists2", pathwayLists2);
		
		
		
		HttpSession session = request.getSession();
		String miRNA1 = (String) session.getAttribute("miRNA1");
		String miRNA2 = (String) session.getAttribute("miRNA2");
		String table = (String) session.getAttribute("database");
	
		//GO miRNAsim Value
		getMiRNAsimValue miRNAsimValue = new getMiRNAsimValue(miRNA1, miRNA2, table);
		miRNAsimValue.getMiRNAgoSimValue();
		double value = miRNAsimValue.getValue();
		
		request.setAttribute("miRNAsim", value);
		
		
		
		
		
		//RequestDispatcher dispatcher = request.getRequestDispatcher("NewFile.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("miRNAoutputs.jsp");
		dispatcher.forward(request, response);
//		try{
//			FileWriter writer = new FileWriter("/Users/JayceeHan/Documents/Research/researchWebsite/jayceeJSP/miRNAsim_human/WebContent/PPI.json");
//			writer.write(graphJn);
//			writer.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
	}
}
