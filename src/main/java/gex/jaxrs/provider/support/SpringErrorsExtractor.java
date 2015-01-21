/**
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
package gex.jaxrs.provider.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import static org.springframework.util.Assert.notNull;

/**
 * Created by domix on 12/23/14.
 */
@Slf4j
public class SpringErrorsExtractor {
  public List<String> extractError(Errors errors, MessageSource messageSource) {
    notNull(errors, "The errors can not be null");
    notNull(messageSource, "The messageSource can not be null");
    return errors.getFieldErrors().stream()
      .map(fieldError -> {
        return of(fieldError.getCodes())
          .map(code -> {
            log.debug("Searching code: '{}' with arguments '{}' and locale '{}'", code, fieldError.getArguments(), getLocale());
            return messageSource.getMessage(code, fieldError.getArguments(), "", getLocale());
          }).filter(StringUtils::hasText).findFirst().orElseGet(() -> {
              log.debug("Searching code: '{}' with arguments '{}', defaultMessage '{}' and locale '{}'", fieldError.getCode(), fieldError.getArguments(), fieldError.getDefaultMessage(), getLocale());
              return messageSource.getMessage(fieldError.getCode(), fieldError.getArguments(), fieldError.getDefaultMessage(), getLocale());
            }
          );
      }).collect(toList());
  }
}
