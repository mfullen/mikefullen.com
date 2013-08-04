
import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 *
 * @author mfullen
 */
public class MyInitializer
{
    @Inject
    public MyInitializer(PersistService persistService)
    {
        persistService.start();
    }
}
