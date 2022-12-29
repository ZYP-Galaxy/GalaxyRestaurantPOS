package com.galaxy.restaurantpos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;

public class Json_class {
    public static JSONArray getJson(String url) {
        InputStream is = null;
        String result = "";
        JSONArray jsonarray = null;
        // HTTP
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (Exception e) {
            return null;
        }
        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            return null;
        }

        // Convert string to object
        try {
            jsonarray = new JSONArray(result);
        } catch (JSONException e) {
            return null;
        }
        return jsonarray;
    }

    public static JSONArray POST(String url, JSONArray headerarray) {
        InputStream is = null;
        JSONArray jsonarray = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            // 5. set json to StringEntity

            StringEntity se = new StringEntity(headerarray.toString(), "UTF-8");
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content

            //Commented by KNO (10-11-2022)
//	        httpPost.setHeader("Accept", "application/json");
//	        httpPost.setHeader("Content-type", "application/json");
//	        httpPost.setHeader("Accept-Charset", "UTF-8");
//	        httpPost.setHeader("Charset", "UTF-8");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            // 9. receive response as inputStream
            is = httpResponse.getEntity().getContent();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();

            } catch (Exception e) {
                return null;
            }

            // Convert string to object
            try {
                jsonarray = new JSONArray(result);
            } catch (JSONException e) {
                return null;
            }
            return jsonarray;


        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return jsonarray;
    }

    public static InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    public static JSONArray sendJson(String url, JSONArray saleitemjsonarray) {
        InputStream is = null;
        String result = "";
        JSONArray jsonarray = null;
        // HTTP
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            HttpResponse response;
            StringEntity se = new StringEntity(saleitemjsonarray.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            response = httpClient.execute(post);

            /*Checking response */
            if (response != null) {
                is = response.getEntity().getContent(); //Get the data in the entity
            }
        } catch (Exception e) {
            return null;
        }
        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            return null;
        }

        // Convert string to object
        try {
            jsonarray = new JSONArray(result);
        } catch (JSONException e) {
            return null;
        }
        return jsonarray;
    }

    public static String getString(String url) {
        InputStream is = null;
        String result = "";

        // HTTP
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (Exception e) {
            return null;
        }
        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static String getString_LOCID(String url)//added WHM [2020-09-29] menucategory for saledeflocationid
    {
        InputStream is = null;
        String result = "";

        // HTTP
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (Exception e) {
            return null;
        }
        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String RetrieveDataFromHttpRequest(HttpPost httppost, ArrayList<NameValuePair> nameValuePairs) {

        StringBuilder sb = new StringBuilder();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            if (nameValuePairs != null)
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            // convert response to string
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
}