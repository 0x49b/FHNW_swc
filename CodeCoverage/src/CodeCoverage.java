
public class CodeCoverage {
	public String pathExample(boolean condition){ 
	      String value = null; 
	      if(condition){ 
	         value = " " + condition + " "; 
	      }

	      if( 1 == 2 ){
			  System.out.println("unlogical");
		  }

	      return value.trim(); 
	   } 

}
