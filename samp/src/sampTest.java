import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class sampTest {

public static String strSeparator = "__,__";
	
	public static String keyValueSeparator = "__=__";
	
	public static String mapSeparator = "__&__";
	
	
	public static void main(String[] args)
	{
		Map<String,List<String>> data = new HashMap<String, List<String>>();
		for(int i = 0 ; i < 2 ; i ++)
		{
			List<String> listData = new ArrayList<String>();
			for (int j = 0; j < 2; j++) {
				listData.add("list data " + j );
			}
			data.put("map key" + i, listData);
		}
		System.out.println(data);
		System.out.println(convertMapToString(data));
		System.out.println(convertStringToMap(convertMapToString(data)));
	}
	
	public static String convertMapToString(Map<String,List<String>> array){
	    String str = "";
	    int j = 0;
	    for(Entry<String, List<String>> entry : array.entrySet())
	    {
	    	String listStr = "";
	    	 for (int i = 0;i<entry.getValue().size(); i++) {
	    		 listStr = listStr+entry.getValue().get(i);
	    		 
	 	        if(i<entry.getValue().size()-1){
	 	        	listStr = listStr+strSeparator;
	 	        }
	 	    }
	    	 str = str + entry.getKey() + keyValueSeparator + listStr;
	    	 if(j<array.size()-1)
	    	 {
	    		 str = str + mapSeparator;
	    	 }
	    	 j++;
	    }
	    return str;
	}
	
	public static Map<String,ArrayList<String>> convertStringToMap(String str){
		Map<String,ArrayList<String>> strToMap = new HashMap<String, ArrayList<String>>();
		String[] mapArr = str.split(mapSeparator);
		for(int i = 0 ; i < mapArr.length ; i++)
		{
			String[] keyValue = mapArr[i].split(keyValueSeparator);
			String[] keyValueArr = keyValue[1].split(strSeparator);
			ArrayList<String> list = new ArrayList<String>();
			for(int j = 0 ; j < keyValueArr.length ; j++)
			{
				list.add(keyValueArr[j]);
			}
			strToMap.put(keyValue[0], list);
		}
		return strToMap;
	}
}
