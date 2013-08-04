
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mfullen.model.UserModel;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mfullen
 */
public class GuiceTest
{
    @Test
    public void testGuice()
    {
        JpaPersistModule jpaPersistModule = new JpaPersistModule("test");

        Injector injector = Guice.createInjector(jpaPersistModule, new RepositoryModule());
        MyInitializer initializer = injector.getInstance(MyInitializer.class);
        FakeResource fakeResource = injector.getInstance(FakeResource.class);

        UserModel userModel = new UserModel();
        userModel.setEmail("mike@mikefullen.com");
        userModel.setFirstName("mike");
        userModel.setLastName("fullen");
        userModel.setUserName("mfullen");

        fakeResource.addUser(userModel);

        assertFalse(fakeResource.getAll().isEmpty());
        assertEquals("mfullen", fakeResource.getAll().iterator().next().getUserName());
    }
}
