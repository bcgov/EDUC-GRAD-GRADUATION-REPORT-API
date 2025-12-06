package ca.bc.gov.educ.api.grad.report.util;

import java.util.ArrayList;
import java.util.List;

import static ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.BusinessEntity.nullSafe;


public class StringUtils {

    public static String strip(String string, String toStrip) {
        final String response = nullSafe(string).trim().replaceAll(toStrip + "$|^" + toStrip, "");
        return response;
    }

    public static boolean StringUtilsIsNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * @param builder
     * @param delimiter
     * @param info
     */
    public static void appendString(StringBuilder builder, String delimiter, String info) {

        if (StringUtilsIsNotBlank(info)) {
            builder.append(delimiter).append(info);
        }
    }

    public static List<Integer> findPositions(String string, char character) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == character) {
                positions.add(i);
            }
        }
        return positions;
    }

    public static int nearestValue(int value, List<Integer> source) {
        int lo = 0;
        int hi = source.size() - 1;
        int lastValue = 0;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            lastValue = source.get(mid);
            if (value < lastValue) {
                hi = mid - 1;
            } else if (value > lastValue) {
                lo = mid + 1;
            } else {
                return lastValue;
            }
        }
        return lastValue;
    }

}
