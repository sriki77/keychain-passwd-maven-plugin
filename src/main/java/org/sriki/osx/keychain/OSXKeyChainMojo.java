package org.sriki.osx.keychain;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


@Mojo(name = "getpasswd", defaultPhase = LifecyclePhase.INITIALIZE)
public class OSXKeyChainMojo
        extends AbstractMojo {

    @Parameter(required = true)
    private String itemName;

    @Parameter(required = true)
    private String accountName;

    @Parameter(required = true)
    private String passwordProperty;

    public void execute()
            throws MojoExecutionException {
        getLog().info(String.format("Accessing ItemName: %s, AccountName: %s", itemName, accountName));
        setPasswordValue();
    }

    private void setPasswordValue() throws MojoExecutionException {
        try {
            final String password = new PasswordReader().getPassword(itemName, accountName);
            getLog().debug(String.format("Got Password: %s for ItemName: %s, AccountName: %s", password, itemName, accountName));
            System.setProperty(passwordProperty, password);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to retrieve data from key chain", e);
        }
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPasswordProperty(String passwordProperty) {
        this.passwordProperty = passwordProperty;
    }
}
