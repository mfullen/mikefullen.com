
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.mfullen.repositories.UserRepository;
import com.mfullen.repositories.jpa.JpaUserRepository;

/**
 *
 * @author mfullen
 */
public class RepositoryModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(UserRepository.class).to(JpaUserRepository.class).in(Scopes.SINGLETON);
    }
}
