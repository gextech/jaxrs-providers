/*
 *
 * Copyright (C) 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gex.jaxrs.provider

import gex.jaxrs.ApiResponse
import org.springframework.context.ApplicationContext
import org.springframework.validation.Errors
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.core.Response

import static java.util.Collections.emptyList
import static java.util.Collections.emptyMap

/**
 * Created by domix on 12/23/14.
 */


class ObjectWithErrorsField {
  Errors errors
}

class ObjectWithErrorsMethod {
  Errors getErrors() {
    null
  }
}

