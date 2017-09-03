/*
 * Copyright 2017 Martin Rowlinson. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.adeptions.arguments;

/**
 * Enumerator for defining the different type of argument
 *
 *  VALUED        - the argument has a name and value
 *  FLAG          - the argument is just a flag (has no value)
 *  INFORMATIONAL - the argument is an informational flag
 *                  (has no value and the application may want to halt normal execution and show some information when
 *                   the argument is encountered - e.g. -version or -help)
 */
public enum ArgumentDefinitionType {
	VALUED("Valued"),
	FLAG("Flag"),
	INFORMATIONAL("Informational");

	private String description;

	ArgumentDefinitionType(String description) {
		this.description = description;
	}

	/**
	 * Gets the printable description of the type
	 * @return the printable description of the type
	 */
	public String getDescription() {
		return description;
	}
}