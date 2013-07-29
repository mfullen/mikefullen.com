package com.mfullen.rest.model.mapping;

/**
 *
 * @author mfullen
 */
public class MapperUtils
{
    private static final ObjectListToIdListConverter objectListToIdListConverter = new ObjectListToIdListConverter();

    public static ObjectListToIdListConverter getObjectListToIdListConverter()
    {
        return objectListToIdListConverter;
    }
}
