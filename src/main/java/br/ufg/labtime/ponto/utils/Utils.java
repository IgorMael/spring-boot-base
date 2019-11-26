package br.ufg.labtime.ponto.utils;

import java.text.Normalizer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isNullOrEmpty(Object object) {
        return object == null
                || (object.getClass() == String.class && object.toString().isEmpty())
                || (object instanceof Collection && ((Collection) object).isEmpty());
    }

    public static Boolean isValidPassword(String pw) {
        String p_pattern = "^(?=.*\\d)(?=.*[a-z])(?!.*\\s).*$";
        Pattern pattern = Pattern.compile(p_pattern);
        Matcher matcher = pattern.matcher(pw);

        return matcher.matches() && (pw.length() >= 6 && pw.length() <= 15);
    }

    public static String lowerWithLike(String text) {
        if (isNullOrEmpty(text)) {
            return "%%";
        }
        return "%" + text.toLowerCase() + "%";
    }

    public static Date resetHour(Date date, boolean dateStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (dateStart) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
        }
        date = calendar.getTime();
        return date;
    }

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
