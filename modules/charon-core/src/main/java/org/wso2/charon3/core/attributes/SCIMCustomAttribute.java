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

package org.wso2.charon3.core.attributes;

import java.util.Map;

/**
 * This data model represents the attribute of custom schema.
 */
public class SCIMCustomAttribute {

    private int tenantId;
    private Map<String, String> properties;

    public void setTenantId(int tenantId) {

        this.tenantId = tenantId;
    }

    public int getTenantId() {

        return tenantId;
    }

    public void setProperties(Map<String, String> properties) {

        this.properties = properties;
    }

    public Map<String, String> getProperties() {

        return properties;
    }
}
