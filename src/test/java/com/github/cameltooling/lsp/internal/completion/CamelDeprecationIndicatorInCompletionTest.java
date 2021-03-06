/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.lsp.internal.completion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.junit.Test;

import com.github.cameltooling.lsp.internal.AbstractCamelLanguageServerTest;
import com.github.cameltooling.lsp.internal.CamelLanguageServer;

public class CamelDeprecationIndicatorInCompletionTest extends AbstractCamelLanguageServerTest {

	@Test
	public void testCamelComponentDeprecation() throws Exception {
		CamelLanguageServer camelLanguageServer = initializeLanguageServer("<from uri=\"cache\" xmlns=\"http://camel.apache.org/schema/blueprint\"></from>\n");
		CompletableFuture<Either<List<CompletionItem>, CompletionList>> completions = getCompletionFor(camelLanguageServer, new Position(0, 15));
		List<CompletionItem> items = completions.get().getLeft();
		assertThat(items.size()).isEqualTo(1);
		assertThat(items.get(0).getDeprecated()).isTrue();
	}
	
	@Test
	public void testParameterDeprecation() throws Exception {
		CamelLanguageServer camelLanguageServer = initializeLanguageServer("<from uri=\"bean:beanName?multiParameterArray\" xmlns=\"http://camel.apache.org/schema/blueprint\"></from>\n");
		CompletableFuture<Either<List<CompletionItem>, CompletionList>> completions = getCompletionFor(camelLanguageServer, new Position(0, 42));
		List<CompletionItem> items = completions.get().getLeft();
		assertThat(items.size()).isEqualTo(1);
		assertThat(items.get(0).getDeprecated()).isTrue();
	}
}
