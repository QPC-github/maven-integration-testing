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

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class MavenIT0110PluginDependenciesComeFromPluginReposTest
    extends AbstractMavenIntegrationTestCase
{
    public void testit0110()
        throws Exception
    {
        File testDir =
            ResourceExtractor.simpleExtractResources( getClass(), "/it0110-pluginDependenciesComeFromPluginRepos" );
        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.deleteArtifact( "org.apache.maven.it", "mail", "1.3.2", "jar" );        
        verifier.executeGoal( "clean" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

    }
}
