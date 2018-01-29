package io.pestakit.integration.api.spec.helpers;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Miguel Santamaria on 14/12/17.
 */
public class Environment {

    private io.pestakit.users.api.DefaultApi usersApi = new io.pestakit.users.api.DefaultApi();
    private io.pestakit.discussions.api.DefaultApi discussionsApi = new io.pestakit.discussions.api.DefaultApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String usersUrl = properties.getProperty("io.pestakit.users.server.url");
        String discussionsUrl = properties.getProperty("io.pestakit.discussions.server.url");
        usersApi.getApiClient().setBasePath(usersUrl);
        discussionsApi.getApiClient().setBasePath(discussionsUrl);
    }

    public io.pestakit.users.api.DefaultApi getUsersApi() {
        return usersApi;
    }

    public io.pestakit.discussions.api.DefaultApi getDiscussionsApi() {
        return discussionsApi;
    }
}
