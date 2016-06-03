

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserInput
 */
@WebServlet("/UserInput")
public class UserInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInput() {
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
		String miRNA1 = request.getParameter("miRNA1");
		String miRNA2 = request.getParameter("miRNA2");
		String radios = request.getParameter("radios");
		
		HttpSession session = request.getSession();

		
		session.setAttribute("miRNA1", miRNA1 );
		
		session.setAttribute("miRNA2", miRNA2);
		
		session.setAttribute("database", radios);
		
		
		
		boolean[] checkExist = new boolean[2];
		GetTargetGenes getData = new GetTargetGenes(miRNA1,miRNA2);
		checkExist = getData.checkMiRNAexist(miRNA1, miRNA2, radios);
		
		if(checkExist[0] == true && checkExist[1] == true){
			getData.SetBothGenesFromDatabase(miRNA1,miRNA2, radios);
			ArrayList<String> genes1 = getData.getGenes1();
			ArrayList<String> genes2 = getData.getGenes2();
			System.out.println(genes1.size());
			System.out.println(genes2.size());
			request.setAttribute("themeList1", genes1);
			request.setAttribute("themeList2", genes2);
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("miRNAoutputs.jsp");
			dispatcher.forward(request, response);	
		}
		else{
			if(checkExist[0] == false &&checkExist[1] == true){
				String message="miRNA one doesn't exist in database " + radios + " Please check again!";
			  	request.setAttribute("message", message);
				RequestDispatcher dispatcher = request.getRequestDispatcher("userInput.jsp");
				dispatcher.forward(request, response);
			}
			else if(checkExist[0] == true &&checkExist[1] == false){
				String message="miRNA two doesn't exist in database " + radios + " Please check again!";
			  	request.setAttribute("message", message);
				RequestDispatcher dispatcher = request.getRequestDispatcher("userInput.jsp");
				dispatcher.forward(request, response);
			}
			else{
				String message="Both miRNA one and miRNA two do not exist in database " + radios + " Please check again!";
			  	request.setAttribute("message", message);
				RequestDispatcher dispatcher = request.getRequestDispatcher("userInput.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		
	}

}
