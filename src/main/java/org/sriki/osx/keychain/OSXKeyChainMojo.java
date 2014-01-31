package org.sriki.osx.keychain;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Retrieves the password from the OSX keychain.
 * The password item in keychain is identified by item and account name.
 * The retrieved value is set as a project property.
 */
@Mojo(name = "getpasswd", defaultPhase = LifecyclePhase.VALIDATE)
public class OSXKeyChainMojo
        extends AbstractMojo {

    /**
     * Item name of the password keychain item.
     */
    @Parameter(required = true)
    private String itemName;

    /**
     * Account name of the password keychain item.
     */
    @Parameter(required = true)
    private String accountName;

    /**
     * Property name whose value should be the password
     * retrieved from the keychain item.
     */
    @Parameter(required = true)
    private String passwordProperty;

    /**
     * The project into which the property is set.
     */
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
