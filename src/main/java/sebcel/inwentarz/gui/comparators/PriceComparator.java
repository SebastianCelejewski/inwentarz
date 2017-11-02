package sebcel.inwentarz.gui.comparators;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Comparator;

public class PriceComparator implements Comparator<Object> {

    DecimalFormat df = new DecimalFormat("0.00 zł");

    @Override
    public int compare(Object o1, Object o2) {
        Double d1 = getValue(o1);
        Double d2 = getValue(o2);
        if (d1 == d2) {
            return 0;
        }
        if (d1 != null && d2 != null) {
            return d1.compareTo(d2);
        }
        if (d1 == null) {
            return -1;
        } else {
            return 1;
        }
    }

    private Double getValue(Object o) {
        Object parsedObject = null;
        try {
            parsedObject = df.parseObject((String) o);
        } catch (ParseException ex) {
            return null;
        }
        if (parsedObject.getClass().equals(Double.class)) {
            return (Double) parsedObject;
        }
        if (parsedObject.getClass().equals(Long.class)) {
            return ((Long) parsedObject).doubleValue();
        }
        throw new RuntimeException("Can not convert class " + parsedObject.getClass() + " (value=" + parsedObject + ")");
    }
}