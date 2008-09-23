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

public class MavenIT0010Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Since the artifact resolution does not use the project builder, we must
     * ensure that the full hierarchy of all dependencies is resolved. This
     * includes the dependencies of the parent-pom's of dependencies. This test
     * will check this, by depending on classworlds, which is a dependency of
     * maven-component, which is the parent of maven-plugin, which is an
     * explicit dependency of this test.
     * # TODO: must correct the assumptions of this test
     */
    public void testit0010()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/it0010" );
        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.executeGoal( "compile" );
        verifier.assertFilePresent( "target/classes/org/apache/maven/it0010/PersonFinder.class" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

    }
}

