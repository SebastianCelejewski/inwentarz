package sebcel.inwentarz.gui.comparators;

import java.util.Comparator;

public class AuthorComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
	String d1 = getValue(o1);
	String d2 = getValue(o2);
	
	return transform(d1).compareTo(transform(d2));
    }
    
    private String getValue(String o) {
	if (o == null) {
	    return "";
	} else {
	    return o.toString();
	}
   }
    
   private String transform(String s) {
       String pierwszyAutor = getPierwszyAutor(s);
       String ostatniWyraz = getOstatniWyraz(pierwszyAutor);
       return ostatniWyraz;
   }
   
   private String getPierwszyAutor(String s) {
       if (s.indexOf(",") > -1) {
	   return s.substring(0, s.indexOf(","));
       } else {
	   return s;
       }
   }
   
   private String getOstatniWyraz(String s) {
       if (s.lastIndexOf(" ") > -1) {
	   return s.substring(s.lastIndexOf(" "), s.length());
       } else {
	   return s;
       }
   }
}
