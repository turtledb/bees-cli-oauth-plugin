package org.cloudbees.sdk.plugins.oauth.app;

import com.cloudbees.sdk.cli.BeesCommand;
import com.cloudbees.sdk.cli.CLICommand;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * List all the registered OAuth applications
 *
 * @author Kohsuke Kawaguchi
 */
@BeesCommand(group="OAuth",description="List all the registered OAuth applications")
@CLICommand("oauth:app:list")
public class ListCommand extends AbstractOAuthCommand {
    @Override
    public int main() throws Exception {
        HttpURLConnection con = makeGetRequest(new URL("https://grandcentral.cloudbees.com/api/v2/applications/"));
        return dumpResponse(con);
    }
}
