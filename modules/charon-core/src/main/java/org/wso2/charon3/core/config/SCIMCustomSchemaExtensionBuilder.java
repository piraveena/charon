/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.charon3.core.config;

import org.wso2.charon3.core.attributes.SCIMCustomAttribute;
import org.wso2.charon3.core.exceptions.CharonException;
import org.wso2.charon3.core.exceptions.InternalErrorException;
import org.wso2.charon3.core.schema.AttributeSchema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.wso2.charon3.core.schema.SCIMConstants.CUSTOM_USER_SCHEMA_URI;

/**
 * This builds the custom user schema.
 */
public class SCIMCustomSchemaExtensionBuilder extends ExtensionBuilder {

    private Map<String, ExtensionAttributeSchemaConfig> customConfig;
    // built schema map
    private static SCIMCustomSchemaExtensionBuilder customSchemaExtensionBuilder =
            new SCIMCustomSchemaExtensionBuilder();
    private Map<String, AttributeSchema> attributeSchemaConfig;
    // This contains the map of tenant and custom schema.
    private Map<Integer, AttributeSchema> extensionSchemaForTenant = new HashMap<>();

    public static SCIMCustomSchemaExtensionBuilder getInstance() {

        return customSchemaExtensionBuilder;
    }

    public AttributeSchema getExtensionSchema(int tenantId) {

        return extensionSchemaForTenant.get(tenantId);
    }

    public Map<Integer, AttributeSchema> getExtensionSchemaForAllTenant() {

        return extensionSchemaForTenant;
    }

    @Override
    public String getURI() {

        return CUSTOM_USER_SCHEMA_URI;
    }

    public void buildUserCustomSchemaExtension(int tenantId, List<SCIMCustomAttribute> schemaConfigurations) throws
            CharonException, InternalErrorException {

        attributeSchemaConfig = new HashMap<>();
        readConfiguration(schemaConfigurations);
        for (Map.Entry<String, ExtensionAttributeSchemaConfig> attributeSchemaConfig :
                customConfig.entrySet()) {
            // if there are no children its a simple attribute, build it
            if (!attributeSchemaConfig.getValue().hasChildren()) {
                buildSimpleAttributeSchema(attributeSchemaConfig.getValue(), this.attributeSchemaConfig);
            } else {
                // need to build child schemas first
                buildComplexAttributeSchema(attributeSchemaConfig.getValue(), this.attributeSchemaConfig, customConfig);
            }
        }
        // Now get the extension schema for tenant.
        extensionSchemaForTenant.put(tenantId, attributeSchemaConfig.get(getURI()));
    }

    private void readConfiguration(List<SCIMCustomAttribute> schemaConfigurations) throws CharonException {

        customConfig = new HashMap<>();
        for (SCIMCustomAttribute schemaConfiguration : schemaConfigurations) {
            ExtensionAttributeSchemaConfig schemaAttributeConfig =
                    new ExtensionAttributeSchemaConfig
                            (schemaConfiguration.getProperties());
            customConfig.put(schemaAttributeConfig.getURI(), schemaAttributeConfig);
        }
    }
}
