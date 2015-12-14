/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2015 
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
 * See the License for the specific language governing permissions and limitations under the License.
 *******************************************************************************/
package com.ginema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ginema.server.GinemaServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GinemaServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTests {

  @Value("${local.server.port}")
  private int port;

  private RestTemplate template = new TestRestTemplate();

  @Test
  public void homePageProtected2() {
    
  }
  @Test
  public void homePageProtected() {
    ResponseEntity<String> response =
        template.getForEntity("http://localhost:" + port + "/ginema-server/", String.class);
    assertEquals(HttpStatus.FOUND, response.getStatusCode());
  }

  @Test
  public void authorizationRedirects() {
    ResponseEntity<String> response =
        template.getForEntity("http://localhost:" + port + "/ginema-server/authorize", String.class);
    assertEquals(HttpStatus.FOUND, response.getStatusCode());
    String location = response.getHeaders().getFirst("Location");
    assertTrue("Wrong header: " + location,
        location.startsWith("http://localhost:" + port + "/ginema-server/login"));
  }

  @Test
  public void loginSucceeds() {
    ResponseEntity<String> response =
        template.getForEntity("http://localhost:" + port + "/ginema-server/login", String.class);
    String csrf = getCsrf(response.getBody());
    MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
    form.set("username", "user");
    form.set("password", "password");
    form.set("_csrf", csrf);
    HttpHeaders headers = new HttpHeaders();
    headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
    RequestEntity<MultiValueMap<String, String>> request =
        new RequestEntity<MultiValueMap<String, String>>(form, headers, HttpMethod.POST,
            URI.create("http://localhost:" + port + "/ginema-server/login"));
    ResponseEntity<Void> location = template.exchange(request, Void.class);
    assertEquals("http://localhost:" + port + "/ginema-server/", location.getHeaders().getFirst("Location"));
  }

  private String getCsrf(String soup) {
    Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*").matcher(soup);
    if (matcher.matches()) {
      return matcher.group(1);
    }
    return null;
  }

}
