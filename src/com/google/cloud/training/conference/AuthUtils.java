package com.google.cloud.training.conference;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/*
 * The following imports require that the Google Calendar APIs have been added to your project. In
 * Eclipse, select the Google menu > Add Google APIs > Calendar API
 */


/*
 * This class provides the methods that are required and common to both the
 * AppEngineAuthorizationCodeServlet and the AppEngineAuthorizationCodeCallbackServlet
 */
public class AuthUtils {

    static final String clientId = "659282091683-28h461ch0br8jjufs7el794hbjtdrvbd.apps.googleusercontent.com";
    static final String clientSecret = "-5ERsT0KETn92YDLMRiAAKrz";

    // The HttpTransport and JacksonFactory are needed for the authorization process
    static final HttpTransport httpTransport = new UrlFetchTransport();
    static final JacksonFactory jsonFactory = new JacksonFactory();

    /*
     * initializeFlow() starts the process of asking the user to give the application permission to
     * access Google APIs on their behalf. The scope of what the application wants to access is
     * determined by the scopeList.
     */
    public static AuthorizationCodeFlow initializeFlow(Iterable<String> scopeList) throws IOException {
        // Create a new GoogleAuthorizationCodeFlow builder
        // to run the authorization process
        Builder flowBuilder =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientId, clientSecret,
                        (Collection<String>) scopeList);

        // Force the user to give permission again if they previously gave
        // access for a different scope
        flowBuilder.setApprovalPrompt("force");

        // Request offline access so that we get a refresh token
        // to prolong the duration of the permission before the user
        // is asked to give permission again.
        flowBuilder.setAccessType("offline");

        // Save the credentials in the App Engine datastore
        flowBuilder.setCredentialDataStore(StoredCredential.getDefaultDataStore(AppEngineDataStoreFactory.getDefaultInstance()));

        // Return the GoogleAuthorizationCodeFlow
        return flowBuilder.build();
    }

    /*
     * Return the path to the callback that is invoked after the user grants or denies access to the
     * application to access Google APIs. The returned URI path must correspond to one of the
     * "redirects URI" you put in the APIs Project Console
     */
    public static String getRedirectUri(HttpServletRequest req)
            throws ServletException, IOException {
        // Return the URL to the authorization callback
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/authcallback");
        return url.build();
    }

}
