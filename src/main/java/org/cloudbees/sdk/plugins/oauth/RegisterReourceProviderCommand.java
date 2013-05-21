package org.cloudbees.sdk.plugins.oauth;

import com.cloudbees.sdk.cli.BeesCommand;
import com.cloudbees.sdk.cli.CLICommand;
import org.cloudbees.sdk.plugins.oauth.model.Application;
import org.kohsuke.args4j.Option;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Kohsuke Kawaguchi
 */
@BeesCommand(group="OAuth",description="Register new OAuth application")
@CLICommand("oauth:create")
public class RegisterReourceProviderCommand extends AbstractOAuthCommand {
    @Option(name="-n",usage="Application Name. This name will be displayed on the authorization screen.")
    public String name;

    @Option(name="--callback",usage="Absolute HTTPS URL where CloudBees authorization server will redirect during OAuth2 authorization.")
    public String callback_uri;

    @Option(name="--url",usage="Absolute URL of your application.")
    public String app_url;

    @Option(name="--account",usage="Account in which the app gets registered.")
    public String account;

    @Override
    public int main() throws Exception {
        String defaultAccount = getDefaultAccount();


        Application reg = new Application();
        reg.name = prompt(String.class,"name");
        reg.callback_uri = prompt(String.class,"callback_uri");
        reg.app_url = prompt(String.class,"app_url");

        reg.account = account;
        if (reg.account==null) {
            System.out.println("Account in which the app gets registered. Default="+defaultAccount);
            System.out.print(": ");
            reg.account = System.console().readLine();
            if (reg.account.isEmpty())
                reg.account = defaultAccount;
        }

        HttpURLConnection con = makePostRequest(new URL("https://grandcentral.cloudbees.com/api/v2/applications/"));
        con.connect();
        om.writeValue(con.getOutputStream(), reg);
        con.getOutputStream().close();

        return dumpResponse(con);
    }
}
