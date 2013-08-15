/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javaone.con3638.facesflow;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

@Named
@Dependent
public class CredentialsFlow {

    private static final long serialVersionUID = 1;

    public static final String ID = "credentialsFlow";

    @Produces @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        flowBuilder.id("", ID);
        // 1. page
        flowBuilder.viewNode(ID, "/" + ID + "/start.xhtml").markAsStartNode();
        flowBuilder.returnNode("back").fromOutcome("/index");

        // 2. page
        flowBuilder.viewNode("address", "/" + ID + "/second.xhtml");

        // 3. page - we can use also implicit navigation
        // flowBuilder.viewNode("confirmation", "/" + ID + "/third.xhtml");

        // flow switch
        flowBuilder.flowCallNode("chooseAvatar")
                // which flow to call
                .flowReference("", "avatarChooser")
                // name of the user
                .outboundParameter("name", "#{personBean.givenname += ' ' += personBean.surname}");

        // store data by leaving the flow - can be restored then
        flowBuilder.finalizer("#{storageBean.storeData()}");

        // restore data by usage of the restore link
        flowBuilder.methodCallNode("restore")
                // method to call
                .expression("#{storageBean.restoreData()}")
                // where to go
                .defaultOutcome(ID);

        // if we are going to the flow from the avatarChooser we should restore all values
        flowBuilder.initializer("#{storageBean.handleLoad()}");

        // saved information about avatar set to the personBean
        flowBuilder.inboundParameter("avatar", "#{personBean.avatar}");

        return flowBuilder.getFlow();
    }

    public String id() {
        return ID;
    }

}