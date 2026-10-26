package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class BMIRanges {

    private static final List<BMICategory> CATEGORIES = load();

    private BMIRanges() {}

    private static List<BMICategory> load()
    {
        try (InputStream in = BMIRanges.class.getResourceAsStream("/BMRangefile.json"))
        {
            if (in == null)
            {
                throw new IllegalStateException("Не найден ресурс /BMRangefile.json");
            }
            ObjectMapper mapper = new ObjectMapper();
            List<BMICategory> list = mapper.readValue(in, new TypeReference<List<BMICategory>>() {});

            if (list.isEmpty())
            {
                throw new IllegalStateException("BMRangefile.json не содержит категорий");
            }

            java.util.Set<Double> seen = new java.util.HashSet<>();
            for (BMICategory c : list)
            {
                if (Double.isNaN(c.getMax()) || Double.isInfinite(c.getMax()) || c.getMax() <= 0)
                {
                    throw new IllegalStateException("Некорректный max у категории \"" + c.getName() + "\": " + c.getMax());
                }
                if (!seen.add(c.getMax())) {
                    throw new IllegalStateException("Дублирующийся max=" + c.getMax());
                }
            }

            list.sort(Comparator.comparingDouble(BMICategory::getMax));
            return List.copyOf(list);
        } catch (IllegalStateException e) {
            throw e;
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new IllegalStateException("Невалидный JSON в BMRangefile.json: " + e.getOriginalMessage(), e);
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Ошибка чтения BMRangefile.json: " + e.getMessage(), e);
        }
    }

    public static List<BMICategory> getCategories() {
        return CATEGORIES;
    }

    public static void checkBMI(double bmi)
    {
        if (Double.isNaN(bmi)) {
            throw new IllegalArgumentException("ИМТ не может быть NaN");
        }
        if (Double.isInfinite(bmi)) {
            throw new IllegalArgumentException("ИМТ должен быть конечным числом");
        }
    }

    //линейный
    public static BMICategory determineCategory(double bmi)
    {
        checkBMI(bmi);

        for (BMICategory c : CATEGORIES)
        {
            if (bmi < c.getMax())
            {
                return c;
            }
        }
        //если bmi >= max последней категории — возвращаем её же
        return CATEGORIES.get(CATEGORIES.size() - 1);
    }

    //поиски данных в json файле
    //бинарный
    public static BMICategory determineCategoryBinary(double bmi)
    {
        checkBMI(bmi);

        int lo = 0;
        int hi = CATEGORIES.size() - 1;
        int found = CATEGORIES.size() - 1;

        while (lo <= hi)
        {
            int mid = (lo + hi) >>> 1;
            if (CATEGORIES.get(mid).getMax() > bmi)
            {
                found = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return CATEGORIES.get(found);
    }

    //map с диапазонами
    public static BMICategory determineCategoryMap(double bmi)
    {
        checkBMI(bmi);

        Map<BMICategory, double[]> ranges = new LinkedHashMap<>();
        double prevMax = Double.NEGATIVE_INFINITY;
        for (BMICategory c : CATEGORIES)
        {           // CATEGORIES уже отсортирован по max
            ranges.put(c, new double[]{prevMax, c.getMax()});
            prevMax = c.getMax();
        }

        for (Map.Entry<BMICategory, double[]> entry : ranges.entrySet())
        {
            double[] range = entry.getValue();
            if (bmi >= range[0] && bmi < range[1])
            {
                return entry.getKey();
            }
        }

        // bmi >= max последней категории — возвращаем её же
        return CATEGORIES.get(CATEGORIES.size() - 1);
    }

    //реализация этапа 1 с крайними значениями
    //private static final double[] UPPER_BOUNDS = {
            //16.0, 18.5, 25.0, 30.0, 35.0, 40.0
    //};
    //private static final BMICategory[] CATEGORIES = {
            //BMICategory.SEVERE_THINNESS,
            //BMICategory.UNDERWEIGHT,
            //BMICategory.NORMAL,
            //BMICategory.OVERWEIGHT,
            //BMICategory.OBESITY_I,
            //BMICategory.OBESITY_II,
            //BMICategory.OBESITY_III
    //};

    //public static BMICategory determineCategory(double bmi) {
        //if(Double.isNaN(bmi))
        //{
            //throw new IllegalArgumentException("ИМТ не может быть nul");
        //}
        //for (int i = 0; i < UPPER_BOUNDS.length; i++) {
            //if (bmi < UPPER_BOUNDS[i]) {
                //return CATEGORIES[i];
            //}
        //}
        //return BMICategory.OBESITY_III; // всё, что >= 40.0
    //}

    //реализация этапа 1
    //public static Map<BMICategory, double[]> getDefaultRanges() {
        //Map<BMICategory, double[]> ranges = new LinkedHashMap<>();

        //ranges.put(BMICategory.SEVERE_THINNESS, new double[]{0.0, 16.0});
        //ranges.put(BMICategory.UNDERWEIGHT, new double[]{16.0, 18.5});
        //ranges.put(BMICategory.NORMAL, new double[]{18.5, 25.0});
        //ranges.put(BMICategory.OVERWEIGHT, new double[]{25.0, 30.0});
        //ranges.put(BMICategory.OBESITY_I, new double[]{30.0, 35.0});
        //ranges.put(BMICategory.OBESITY_II, new double[]{35.0, 40.0});
        //ranges.put(BMICategory.OBESITY_III, new double[]{40.0, 100.0});

    //    return ranges;
    //}
}