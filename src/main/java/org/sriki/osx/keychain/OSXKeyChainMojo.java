package org.sriki.osx.keychain;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;


@Mojo(name = "getpasswd", defaultPhase = LifecyclePhase.VALIDATE)
public class OSXKeyChainMojo
        extends AbstractMojo {

    @Parameter(required = true)
    private String itemName;

    @Parameter(required = true)
    private String accountName;

    @Parameter(required = true)
    private String passwordProperty;

    @Parameter(defaultValue = "${project}")
    MavenProject project;

    public void execute()
            throws MojoExecutionException {
        getLog().info(String.format("Accessing ItemName: %s, AccountName: %s", itemName, accountName));
        setPasswordValue();
    }

    private void setPasswordValue() {
        try {
            final String password = new PasswordReader().getPassword(itemName, accountName);
            getLog().debug(String.format("Got Password: %s for ItemName: %s, AccountName: %s", password, itemName, accountName));
            System.setProperty(passwordProperty, password);
            project.getProperties().setProperty(passwordProperty, password);
        } catch (Exception e) {
            getLog().error("Failed to retrieve data from key chain", e);
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
