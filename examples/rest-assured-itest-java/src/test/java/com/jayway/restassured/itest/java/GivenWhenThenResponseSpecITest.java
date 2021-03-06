/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jayway.restassured.itest.java;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.itest.java.support.WithJetty;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GivenWhenThenResponseSpecITest extends WithJetty {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test public void
    simple_given_when_then_works() {
        exception.expect(AssertionError.class);
        exception.expectMessage("2 expectations failed.\n" +
                "Expected status code <201> doesn't match actual status code <200>.\n" +
                "\n" +
                "JSON path greeting doesn't match.\n" +
                "Expected: Greetings John Doo\n" +
                "  Actual: Greetings John Doe");

        ResponseSpecification specification = new ResponseSpecBuilder().expectStatusCode(201).expectBody("greeting", equalTo("Greetings John Doo")).build();

        given().
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greet").
        then().
                spec(specification);
    }
}
