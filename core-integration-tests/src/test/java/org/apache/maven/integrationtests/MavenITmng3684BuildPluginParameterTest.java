package org.apache.maven.integrationtests;

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

import java.io.File;

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

/**
 * Verify that the Build instance injected as a plugin parameter contains
 * interpolated values for things like the various build paths, etc.
 * 
 * @author jdcasey
 */
public class MavenITmng3684BuildPluginParameterTest
    extends AbstractMavenIntegrationTestCase
{
    public MavenITmng3684BuildPluginParameterTest()
    {
        super( "(2.0.9,)" );
    }
    
    public void testitMNG3684 ()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3684-buildPluginParameter" );
        File pluginDir = new File( testDir, "maven-mng3684-plugin" );
        File projectDir = new File( testDir, "project" );

        Verifier verifier = new Verifier( pluginDir.getAbsolutePath() );
        verifier.executeGoal( "install" );
        
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
        
        verifier = new Verifier( projectDir.getAbsolutePath() );
        verifier.executeGoal( "validate" );

        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
        
        File logFile = new File( projectDir, "log.txt" );
        logFile.renameTo( new File( projectDir, "log-validate.txt" ) );
        
        verifier.executeGoal( "site" );

        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
        
        logFile.renameTo( new File( projectDir, "log-site.txt" ) );
        
    }
}
