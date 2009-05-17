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

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class MavenITmng3220ImportScopeTest
    extends AbstractMavenIntegrationTestCase
{
    public MavenITmng3220ImportScopeTest()
    {
        super( "(2.0.8,3.0-alpha-1)" ); // not supported in 3.0+
    }

    public void testitMNG3220a()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3220" );

        testDir = new File( testDir, "imported-pom-depMgmt" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3220" );
        verifier.filterFile( "../settings-template.xml", "settings.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.getCliOptions().add( "--settings" );
        verifier.getCliOptions().add( "settings.xml" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

    public void testitMNG3220b()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3220" );

        testDir = new File( testDir, "depMgmt-pom-module-notImported" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3220" );
        verifier.filterFile( "../settings-template.xml", "settings.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.getCliOptions().add( "--settings" );
        verifier.getCliOptions().add( "settings.xml" );

        try
        {
            verifier.executeGoal( "validate" );
            verifier.verifyErrorFreeLog();
            fail( "Should fail to build with missing junit version." );
        }
        catch ( VerificationException e )
        {
            // expected
        }

        verifier.resetStreams();

        List lines = verifier.loadFile( new File( testDir, "log.txt" ), false );

        boolean found = false;
        for ( Iterator it = lines.iterator(); it.hasNext(); )
        {
            String line = (String) it.next();
            if ( line.indexOf( "\'dependencies.dependency.version\' is missing for junit:junit") > -1 )
            {
                found = true;
                break;
            }
        }

        assertTrue( "Should have found validation error line in output.", found );
    }

}
