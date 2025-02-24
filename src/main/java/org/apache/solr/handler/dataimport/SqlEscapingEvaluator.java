/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.solr.handler.dataimport;

import java.util.List;

import static org.apache.solr.handler.dataimport.DataImportHandlerException.SEVERE;

/**
 * <p> Escapes values in SQL queries.  It escapes the value of the given expression
 * by replacing all occurrences of single-quotes by two single-quotes and similarily
 * for double-quotes </p>
 */
public class SqlEscapingEvaluator extends Evaluator {
    @Override
    public String evaluate(String expression, Context context) {
        List<Object> l = parseParams(expression, context.getVariableResolver());
        if (l.size() != 1) {
            throw new DataImportHandlerException(SEVERE, "'escapeSql' must have at least one parameter ");
        }
        String s = l.get(0).toString();
        // escape single quote with two single quotes, double quote
        // with two doule quotes, and backslash with double backslash.
        // See:  http://dev.mysql.com/doc/refman/4.1/en/mysql-real-escape-string.html
        return s.replaceAll("'", "''").replaceAll("\"", "\"\"").replaceAll("\\\\", "\\\\\\\\");
    }
}
