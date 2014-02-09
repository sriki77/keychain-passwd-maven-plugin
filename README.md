OSX Keychain Password Maven Plugin
==============================

I use maven for deployments. This requires me to provide credentials - username and password to maven. You can provide the password when the command is run everytime or store it in maven pom file. Securing password is an issue with all the systems that support only username/password base authentication.

Thats the background for creation of this plugin. This plugin retrieves the password from OSX keychain and sets it as a Maven Project property - which can be used by other plugins. As expected, the plugin is OSX specific.

Usage
---------
* Create a password item in Mac key chain. Screenshot below.
![Password Item Image](https://raw.github.com/sriki77/keychain-passwd-maven-plugin/master/passwd_key.png)
* Configure the maven plugin as follows to access the created keychain it
<pre>
      &lt;plugin&gt;
                &lt;groupId&gt;org.sriki&lt;/groupId&gt;
                &lt;artifactId&gt;keychain-passwd-maven-plugin&lt;/artifactId&gt;
                &lt;version&gt;1.0&lt;/version&gt;
                &lt;configuration&gt;
                    &lt;accountName&gt;maven-test-item&lt;/accountName&gt;
                    &lt;itemName&gt;maven-test-item&lt;/itemName&gt;
                    &lt;passwordProperty&gt;password&lt;/passwordProperty&gt;
                &lt;/configuration&gt;
             &lt;executions&gt;
                 &lt;execution&gt;
                      &lt;goals&gt;
                            &lt;goal&gt;getpasswd&lt;/goal&gt;
                     &lt;/goals&gt;
                &lt;/execution&gt;
           &lt;/executions&gt;
   &lt;/plugin&gt;
</pre> 
* **itemName** (default: mvn-app-passwd) and **accountName** (default: mvn-app-passwd) are help identify the password item from keychain
* **passwordProperty** (default: password)is the name of the system property to which the retrieved password value should be set. In the above case a Java system property named *password* will set the value retrieved from keychain.
* When the keychain is accessed by maven plugin, OSX will prompt on whether you like the Java program to access keychain; reply *Allow* will cause the password to be retrived successfully.
* If the password retrieval fails, an error is logged in console indicating the same. The failure to retrive password is not considered fatal and will not halt maven execution. The **passwordProperty** value will be *null*.
* The plugin by default runs in *validate* phase of the maven build lifecycle.

Location of Maven Plugin
---------------------------------
This maven plugin is found in OSS Sonatype repository
<pre>
	&lt;repository&gt;
      &lt;id&gt;Sonatype repository&lt;/id&gt;
      &lt;name&gt;Sonatype&apos;s Maven repository&lt;/name&gt;
      &lt;url&gt;http://oss.sonatype.org/content/groups/public&lt;/url&gt;
      &lt;snapshots&gt;
        &lt;enabled&gt;false&lt;/enabled&gt;
      &lt;/snapshots&gt;
 &lt;/repository&gt;
</pre>


Acknowledgments
-------------------------
* This maven plugin uses an excellent [Java based implementation - osx-key-chain-java](https://github.com/conormcd/osx-keychain-java)  by **Conor McDermottroe** to access the OSX keychain. This implementation is core to the plugin. Thanks to him for letting me use the same for the plugin.