package com.mfullen;

import com.google.inject.Inject;
import com.mfullen.model.UserModel;
import com.mfullen.repositories.UserRepository;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.InputStream;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage
{
    private static final long serialVersionUID = 1L;
    @Inject
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

        Form form = new Form("form")
        {
            @Override
            protected void onSubmit()
            {
                info("Form.onSubmit executed");
            }
        };

        Button button1 = new Button("button1")
        {
            @Override
            public void onSubmit()
            {
                String urlParameters = "{\"title\":\"TheSuperBestBlog\" }";
                String request = "http://localhost:8080/MfullenWeb/rest/blogs/create";
                InputStream in = null;

                try
                {
                    Client client = Client.create();

                    WebResource webResource = client
                            .resource(request);

                    String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";

                    ClientResponse response = webResource.type("application/json")
                            .post(ClientResponse.class, urlParameters);

                    if (response.getStatus() != 201)
                    {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + response.getStatus());
                    }

                    System.out.println("Output from Server .... \n");
                    String output = response.getEntity(String.class);
                    System.out.println(output);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        };
        form.add(button1);


        add(form);

    }

    public final String getUserName(String name)
    {
        List<UserModel> findByUserName = userRepository.findByUserName("mfullen");
        if (findByUserName != null)
        {
            name = findByUserName.isEmpty() ? "" : findByUserName.get(0).getUserName();
        }
        else
        {
            name = "";
        }

        return name;
    }
}
