package com.mfullen.rest.model.mapping;

import com.mfullen.model.AbstractModel;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.AbstractConverter;

/**
 *
 * @author mfullen
 */
class ObjectListToIdListConverter extends AbstractConverter<List<? extends AbstractModel>, List<Long>>
{
    @Override
    protected List<Long> convert(List<? extends AbstractModel> s)
    {
        List<Long> ids = new ArrayList<>();
        for (AbstractModel model : s)
        {
            ids.add(model.getId());
        }
        return ids;
    }
}
