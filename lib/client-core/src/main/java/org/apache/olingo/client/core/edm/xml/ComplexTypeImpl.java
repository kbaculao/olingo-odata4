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
 * KIND, either express or >ied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.edm.xml;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.olingo.commons.api.edm.provider.ComplexType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;

@JsonDeserialize(using = ComplexTypeImpl.ComplexTypeDeserializer.class)
public class ComplexTypeImpl extends ComplexType {

  private static final long serialVersionUID = 4076944306925840115L;

  static class ComplexTypeDeserializer extends AbstractEdmDeserializer<ComplexType> {

    @Override
    protected ComplexType doDeserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException {

      final ComplexTypeImpl complexType = new ComplexTypeImpl();

      for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
        final JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.FIELD_NAME) {
          if ("Name".equals(jp.getCurrentName())) {
            complexType.setName(jp.nextTextValue());
          } else if ("Abstract".equals(jp.getCurrentName())) {
            complexType.setAbstract(BooleanUtils.toBoolean(jp.nextTextValue()));
          } else if ("BaseType".equals(jp.getCurrentName())) {
            complexType.setBaseType(jp.nextTextValue());
          } else if ("OpenType".equals(jp.getCurrentName())) {
            complexType.setOpenType(BooleanUtils.toBoolean(jp.nextTextValue()));
          } else if ("Property".equals(jp.getCurrentName())) {
            jp.nextToken();
            complexType.getProperties().add(jp.readValueAs(PropertyImpl.class));
          } else if ("NavigationProperty".equals(jp.getCurrentName())) {
            jp.nextToken();
            complexType.getNavigationProperties().add(jp.readValueAs(NavigationPropertyImpl.class));
          } else if ("Annotation".equals(jp.getCurrentName())) {
            jp.nextToken();
            complexType.getAnnotations().add(jp.readValueAs(AnnotationImpl.class));
          }
        }
      }

      return complexType;
    }
  }

}
