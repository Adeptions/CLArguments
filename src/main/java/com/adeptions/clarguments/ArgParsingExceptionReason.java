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
package com.adeptions.clarguments;

/**
 * Enumerator to define the categorized reasons for args parsing exceptions (ArgParsingException)
 */
public enum ArgParsingExceptionReason {
	MISSING_MANDATORY,
	INVALID_VALUE,
	MISSING_VALUE,
	INVALID_ARGUMENT_NAME_PREFIX,
	INVALID_ARGUMENT_NAME_SUFFIX,
	UNKNOWN_ARGUMENT,
	UNKNOWN_ARGUMENT_VALUE,
	UNEXPECTED_EXCEPTION,
	FLAG_WITH_VALUE,
	INFORMATIONAL_WITH_VALUE,
	MULTIPLE_ARGUMENT_NOT_ALLOWED,
	UNDEFINED,
}