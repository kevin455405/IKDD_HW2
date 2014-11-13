import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class IKDDhw2{
	
	public static void main(String[] args) throws IOException{
		
		Connection con = null;
		try{
	        Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://iservdb.cloudopenlab.org.tw:5432/kevin455405_db_7049";
	        con = DriverManager.getConnection(url, "kevin455405_user_7049", "y879hfT4" );
			Statement st = con.createStatement();
			
			String sql = " SELECT * FROM \"twitter\" WHERE q = '"+args[0]+"' " ;
			ResultSet rs = st.executeQuery(sql);
			
			Map<String, String[]> map_name=new HashMap<String, String[]>();
			while(rs.next())
			{
				map_name.put(rs.getString(1), new String[] {rs.getString(4),rs.getString(3),rs.getString(2)});
			}
			
			List<Map.Entry<String, String[]>> list_Data = new ArrayList<Map.Entry<String, String[]>>(map_name.entrySet());
			Collections.sort(list_Data, new Comparator<Map.Entry<String, String[]>>() {
				public int compare(Map.Entry<String, String[]> o1, Map.Entry<String, String[]> o2) {
					String[] str1 = o1.getValue();
					String[] str2 = o2.getValue();
					return Long.valueOf(str1[0]).compareTo(Long.valueOf(str2[0]));
            }});
			
			System.setOut(new PrintStream(System.out, true, "UTF-8"));
			System.out.format("%-15s %-30s %-20s%n","user_id","user_name","text");
			for(Map.Entry<String, String[]> mapping:list_Data)
			{
				String[] str=mapping.getValue();
				int strlen = str[2].length(),now=0;
				//System.out.println(mapping.getKey()+"   "+str[0]+"   "+str[1]); 
				if(strlen>20)
				{
					System.out.format("%-15s %-30s ", str[0], str[1]);
					int count=0;
					while(now!=strlen)
					{
						if(str[2].charAt(now)=='\n'||count==20)
						{
							count=0;
							System.out.format("\n%-15s %-30s ","","");
							now++;
							continue;
						}
						System.out.format("%c", str[2].charAt(now));
						now++;
						count++;
					}
					System.out.format("\n");
				}
				else
					System.out.format("%-15s %-30s %-20s\n", str[0], str[1], str[2]);
				
			}
			 
			 
	    }catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	    }
	}    
}

