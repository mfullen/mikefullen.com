
import com.google.inject.Inject;
import com.mfullen.model.UserModel;
import com.mfullen.repositories.UserRepository;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class FakeResource
{
    private UserRepository userRepository;

    @Inject
    public FakeResource(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<UserModel> getUserByName(String name)
    {
        return this.userRepository.findByUserName(name);
    }
    public Collection<UserModel> getAll()
    {
        return this.userRepository.getAll();
    }

    public void addUser(UserModel model)
    {
        this.userRepository.add(model);
    }
}
