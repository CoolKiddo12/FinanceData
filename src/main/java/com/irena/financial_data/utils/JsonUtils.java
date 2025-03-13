package com.irena.financial_data.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irena.financial_data.dto.StockItemDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * JSON utility class.
 */
@Slf4j
public final class JsonUtils {

    private JsonUtils() { }

    /**
     * Converts the given object into a json string representation using the ObjectMapper bean from the context.
     *
     * @param object            the java object for conversion.
     * @param stockItemDtoClass
     * @return the json string representation or null if no object provided.
     */
    public static String convertObjectToJsonString(Object object, Class<StockItemDto> stockItemDtoClass) {

        if (object != null) {
            try {
                ObjectMapper objectMapper = StaticContextAccessor.getBean(ObjectMapper.class);
                return objectMapper != null ? objectMapper.writeValueAsString(object) : null;
            } catch (Exception e) {
                String stringValue = String.valueOf(object);
                log.error("Object to JSON conversion failed for object (" + stringValue + ").", e);
            }
        }

        return null;
    }

    /**
     * Convert json string to the object of the given class.
     *
     * @param jsonValue the json string.
     * @param clazz the target object class.
     * @param <T> the generic.
     *
     * @return the generic object instance.
     */
    public static <T> T convertJsonStringToObject(String jsonValue, Class<T> clazz) {
        if (StringUtils.isNotBlank(jsonValue)) {
            try {
                ObjectMapper objectMapper = StaticContextAccessor.getBean(ObjectMapper.class);
                return objectMapper != null ? objectMapper.readValue(jsonValue, clazz) : null;
            } catch (Exception e) {
                log.error("Json to Object conversion failed for JSON string (" + jsonValue + ").", e);
            }
        }

        return null;
    }

    /**
     * Gets double value from the given parameter.
     * @param parameterValue the parameter value.
     */
    public static Double getDoubleValue(Object parameterValue) {
        return getDoubleValueWithPrecision(parameterValue, 1);
    }

    /**
     * Gets the double value of the parameter with the specified precision.
     *
     * @param parameterValue the parameter value.
     * @param precision the precision.
     */
    public static Double getDoubleValueWithPrecision(Object parameterValue, int precision) {

        try {
            Number value = null;
            if (parameterValue instanceof String) {
                if (NumberUtils.isParsable((String) parameterValue)) {

                    value = NumberUtils.createNumber((String) parameterValue);
                }
            } else {
                value = (Number) parameterValue;
            }

            if (value != null) {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator('.');
                symbols.setGroupingSeparator('\'');

                String decimalPlaces = "#".repeat(Math.max(1, precision));
                String decimalFormat = String.format("###.%s", decimalPlaces);

                DecimalFormat df = new DecimalFormat(decimalFormat, symbols);
                String formattedValue = df.format(value.doubleValue());

                double doubleValue = Double.parseDouble(formattedValue);

                if (Double.isNaN(doubleValue)) {
                    String message =
                            String.format("Double value [%s] is 'NaN'. Returning null as fallback",
                                    ReflectionToStringBuilder.toString(parameterValue));
                    log.debug(message);
                    return null;
                }

                return doubleValue;
            }
        } catch (Exception e) {
            String stringValue = String.valueOf(parameterValue);
            log.error("Double conversion failed for JSON parameter (" + stringValue + ").", e);
            return null;
        }

        return null;
    }

    /**
     * Gets the integer value from the parameter.
     *
     * @param parameterValue the parameter value.
     */
    public static Integer getIntegerValue(Object parameterValue) {

        try {
            return (Integer) parameterValue;
        } catch (Exception e) {
            String stringValue = String.valueOf(parameterValue);
            log.error("Integer conversion failed for JSON parameter (" + stringValue + ").", e);
            return null;
        }
    }

    /**
     * Gets the boolean value from the parameter.
     *
     * @param parameterValue the parameter value.
     */
    public static Boolean getBooleanValue(Object parameterValue) {

        if (parameterValue != null) {

            String stringValue = String.valueOf(parameterValue);
            if (StringUtils.isNotBlank(stringValue)) {
                try {
                    return Boolean.valueOf(stringValue);
                } catch (Exception e) {
                    log.error("Boolean conversion failed for JSON parameter(" + stringValue + ").", e);
                    return null;
                }
            } else {
                return null;
            }
        }

        return null;
    }
}
