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
 * Interface for handling BadArgException during parsing
 */
@FunctionalInterface
public interface BadArgExceptionsHandler {
    /**
     * Decides whether an encountered pasring exception should halt parsing immediately (by throwing the exception)
     * or should store the exception (by returning the exception) for reporting/handling after parsing completion
     *
     * @param badArgException the arg parsing exception to be handled
     * @return the exception to be stored (for reporting/handling after parsing completion) or null if
     *         the exception is swallowed
     * @throws BadArgException (if the exception should halt parsing immediately)
     */
    BadArgException handle(BadArgException badArgException) throws BadArgException;
}