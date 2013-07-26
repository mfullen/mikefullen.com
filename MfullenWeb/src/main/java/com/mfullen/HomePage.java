package com.mfullen;

import com.mfullen.model.UserModel;
import com.mfullen.repositories.UserRepository;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.transaction.annotation.Transactional;

public class HomePage extends WebPage
{
    private static final long serialVersionUID = 1L;
    @SpringBean
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public HomePage(final PageParameters parameters)
    {
        super(parameters);

        // userRepository = new JpaUserRepository();

        //add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
        // TODO Add your page's components here
        String name = "NONE";
        name = getUserName(name);
        add(new Label("version", name));

    }

    @Transactional
    public final String getUserName(String name)
    {
        List<UserModel> findByUserName = userRepository.findByUserName("mfullen");
        name = findByUserName == null ? null : findByUserName.get(0).getUserName();
        return name;
    }
}
