package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class MavenITmng4112MavenVersionPropertyTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng4112MavenVersionPropertyTest()
    {
        super( "(3.0.3,)" );
    }

    /**
     * Test for ${maven.version} and ${maven.build.version} property
     */
    public void testit()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-4112" );

        Verifier verifier = newVerifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "validate" );

        Properties props = verifier.loadProperties( "target/pom.properties" );

        String testMavenVersion = props.getProperty( "project.properties.simpleVersion", "" );
        assertFalse( testMavenVersion, testMavenVersion.contains( "$" ) );
        assertTrue( testMavenVersion, testMavenVersion.matches( "[0-9]+\\.[0-9]+.*" ) );

        String testMavenBuildVersion = props.getProperty( "project.properties.fullVersion", "" );
        assertTrue( testMavenBuildVersion, testMavenBuildVersion.contains( testMavenVersion ) );

        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

}