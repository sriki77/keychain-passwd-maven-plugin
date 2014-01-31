package org.sriki.osx.keychain;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OSXKeyChainMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();
    Properties props;


    private OSXKeyChainMojo getOsxKeyChainMojo() throws Exception {
        File pom = new File("src/test/resources/test-pom.xml");
        assertNotNull(pom);
        assertTrue(pom.exists());
        OSXKeyChainMojo osxKeyChainMojo = (OSXKeyChainMojo) rule.lookupMojo("getpasswd", pom);

        osxKeyChainMojo.project=new MavenProjectStub(){
            @Override
            public Properties getProperties() {
                return props;
            }
        };
        assertNotNull(osxKeyChainMojo);
        return osxKeyChainMojo;
    }

    @Test
    public void shouldGetPasswordFromKeyChain() throws Exception {
        props=new Properties();
        OSXKeyChainMojo osxKeyChainMojo = getOsxKeyChainMojo();
        osxKeyChainMojo.execute();
        assertThat(osxKeyChainMojo.project.getProperties().getProperty("password")==null,is(false));
    }
}
