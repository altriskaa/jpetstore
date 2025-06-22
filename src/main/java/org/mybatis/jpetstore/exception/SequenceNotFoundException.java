/*
 *    Copyright 2010-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.exception;

// [REFACTOR (java:S112)] 22/06/25 - "Define and throw a dedicated exception instead of using a generic one." [A]
public class SequenceNotFoundException extends RuntimeException {
  public SequenceNotFoundException(String name) {
    super("Error: A null sequence was returned from the database (could not get next " + name + " sequence).");
  }
}
