/*
 *  Copyright (c) 2005-2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.integration.test.wunderlinedeals;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

public class WunderlineDealsConnectorIntegrationTest extends ConnectorIntegrationTestBase {
   
   private Map<String, String> esbRequestHeadersMap;
   private Map<String, String> apiRequestHeadersMap;
   
   private String apiEndpointUrl;
   private String authString;
   
   /**
   * Set up the environment.
   */
   @BeforeClass(alwaysRun = true)
   public void setEnvironment() throws Exception {
      
      init("wunderlinedeals-connector-1.0.0");
      
      esbRequestHeadersMap = new HashMap<String, String>();
      apiRequestHeadersMap = new HashMap<String, String>();
      
      esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
      esbRequestHeadersMap.put("Content-Type", "application/json");
      esbRequestHeadersMap.put("Accept", "application/json");
      
      apiRequestHeadersMap.putAll(esbRequestHeadersMap);
      
      apiEndpointUrl = connectorProperties.getProperty("apiUrl") + "/api/v3";
      authString = "?api_key=" + connectorProperties.getProperty("apiKey");
      
   }
   
   /**
   * Method name: init
   * Test scenario: Mandatory
   * Reason to skip: 
   */
   
   /**
   * Method name: init
   * Test scenario: Optional
   * Reason to skip: 
   */
   
   /**
   * Method name: init
   * Test scenario: Negative
   * Reason to skip: 
   */
   
   /**
   * Positive test case for createCompany method with mandatory parameters.
   * @throws JSONException
   * @throws IOException
   */
   @Test(groups = { "wso2.esb" }, description = "wunderlinedeals {createCompany} integration test with mandatory parameters.")
   public void testCreateCompanyWithMandatoryParameters() throws IOException, JSONException {
      
      esbRequestHeadersMap.put("Action", "urn:createCompany");
      RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_createCompany_mandatory.json");
      
      final String companyIdMandatory = esbRestResponse.getBody().getString("id");
      
      final String apiEndpoint = apiEndpointUrl + "/companies/" + companyIdMandatory + ".json" + authString;
      RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
      
      Assert.assertEquals(connectorProperties.getProperty("mandatoryCompanyName"), apiRestResponse.getBody().getString("name"));
      Assert.assertEquals(esbRestResponse.getBody().getString("created_at"), apiRestResponse.getBody().getString("created_at"));
      Assert.assertEquals(esbRestResponse.getBody().getString("updated_at"), apiRestResponse.getBody().getString("updated_at"));
      
   }
   
   /**
   * Positive test case for createCompany method with optional parameters.
   * @throws JSONException
   * @throws IOException
   */
   @Test(groups = { "wso2.esb" }, description = "wunderlinedeals {createCompany} integration test with optional parameters.")
   public void testCreateCompanyWithOptionalParameters() throws IOException, JSONException {
      
      esbRequestHeadersMap.put("Action", "urn:createCompany");
      RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_createCompany_optional.json");
      
      final String companyIdOptional = esbRestResponse.getBody().getString("id");
      connectorProperties.put("companyIdOptional", companyIdOptional);
      
      final String apiEndpoint = apiEndpointUrl + "/companies/" + connectorProperties.getProperty("companyIdOptional") + ".json" + authString;
      RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
      
      Assert.assertEquals(connectorProperties.getProperty("companyIdOptional"), apiRestResponse.getBody().getString("name"));
      Assert.assertEquals(connectorProperties.getProperty("optionalCompanyDescription"), apiRestResponse.getBody().getString("description"));
      Assert.assertEquals(connectorProperties.getProperty("optionalCompanyEmail"), apiRestResponse.getBody().getString("email"));
      Assert.assertEquals(connectorProperties.getProperty("optionalCompanyWebsite"), apiRestResponse.getBody().getString("web"));
      Assert.assertEquals(connectorProperties.getProperty("optionalCompanyFax"), apiRestResponse.getBody().getString("fax"));
      Assert.assertEquals(connectorProperties.getProperty("optionalCompanyCity"), apiRestResponse.getBody().getString("city"));
      
   }
   
   /**
   * Method name: createCompany
   * Test scenario: Negative
   * Reason to skip: 
   */
   
   /**
   * Method name: updateCompany
   * Test scenario: Mandatory
   * Reason to skip: 
   */
   
   /**
   * Method name: updateCompany
   * Test scenario: Optional
   * Reason to skip: 
   */
   
   /**
   * Method name: updateCompany
   * Test scenario: Negative
   * Reason to skip: 
   */
   
   /**
   * Positive test case for getCompany method with mandatory parameters.
   * @throws JSONException
   * @throws IOException
   */
   @Test(groups = { "wso2.esb" }, description = "wunderlinedeals {getCompany} integration test with mandatory parameters.",
   dependsOnMethods={ "testCreateCompanyWithOptionalParameters" })
   public void testGetCompanyWithMandatoryParameters() throws IOException, JSONException {
      
      esbRequestHeadersMap.put("Action", "urn:getCompany");
      RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getCompany_mandatory.json");
      
      final String apiEndpoint = apiEndpointUrl + "/companies/" + connectorProperties.getProperty("companyIdOptional") + ".json" + authString;
      RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
      
      Assert.assertEquals(esbRestResponse.getBody().getString("id"), apiRestResponse.getBody().getString("id"));
      Assert.assertEquals(esbRestResponse.getBody().getString("name"), apiRestResponse.getBody().getString("name"));
      Assert.assertEquals(esbRestResponse.getBody().getString("email"), apiRestResponse.getBody().getString("email"));
      
   }
   
   /**
   * Method name: getCompany
   * Test scenario: Optional
   * Reason to skip: 
   */
   
   /**
   * Negative test case for getCompany method.
   * @throws JSONException
   * @throws IOException
   */
   @Test(groups = { "wso2.esb" }, description = "wunderlinedeals {getCompany} integration test with negative case.")
   public void testGetCompanyWithNegativeCase() throws IOException, JSONException {
      
      esbRequestHeadersMap.put("Action", "urn:getCompany");
      RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getCompany_negative.json");
      
      final String apiEndpoint = ;
      RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
      
      Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 400);
      Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 400);
      Assert.assertEquals(esbRestResponse.getBody().getString("error"), apiRestResponse.getBody().getString("error"));
      
   }
   
   /**
   * Positive test case for listCompanies method with mandatory parameters.
   * @throws JSONException
   * @throws IOException
   */
   @Test(groups = { "wso2.esb" }, description = "wunderlinedeals {listCompanies} integration test with mandatory parameters.")
   public void testListCompaniesWithMandatoryParameters() throws IOException, JSONException {
      
      esbRequestHeadersMap.put("Action", "urn:listCompanies");
      RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_listCompanies_mandatory.json");
      
      final String apiEndpoint = apiEndpointUrl + "/people/" + connectorProperties.getProperty("companyIdOptional") + ".json" + authString;
      RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
      
      Assert.assertEquals(esbRestResponse.getBody().getString("id"), apiRestResponse.getBody().getString("id"));
      Assert.assertEquals(esbRestResponse.getBody().getString("name"), apiRestResponse.getBody().getString("name"));
      Assert.assertEquals(esbRestResponse.getBody().getString("email"), apiRestResponse.getBody().getString("email"));
      
   }
   
   /**
   * Method name: listCompanies
   * Test scenario: Optional
   * Reason to skip: 
   */
   
   /**
   * Negative test case for listCompanies method.
   * @throws JSONException
   * @throws IOException
   */
   @Test(groups = { "wso2.esb" }, description = "wunderlinedeals {listCompanies} integration test with negative case.")
   public void testListCompaniesWithNegativeCase() throws IOException, JSONException {
      
      esbRequestHeadersMap.put("Action", "urn:listCompanies");
      RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_listCompanies_negative.json");
      
      final String apiEndpoint = ;
      RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
      
      Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 400);
      Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 400);
      Assert.assertEquals(esbRestResponse.getBody().getString("error"), apiRestResponse.getBody().getString("error"));
      
   }
   
}
