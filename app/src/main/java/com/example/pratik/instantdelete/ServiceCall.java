package com.example.pratik.instantdelete;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Pratik on 27-Sep-16.
 */
public class ServiceCall {
    public final static int GETRequest = 1;
    public final static int POSTRequest = 2;

    //Constructor with no parameter
    public ServiceCall() {
    }
    public String makeWebServiceCall(String url, int requestmethod) {
        return this.makeWebServiceCall(url, requestmethod, null);
    }

    public String makeWebServiceCall(String urladdress, int requestmethod,
                                     HashMap params) {
        URL url;
        String response = "";
        try {
            url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15001);
            conn.setConnectTimeout(15001);
            conn.setDoInput(true);
            conn.setDoOutput(true);
/* if (requestmethod == POSTRequest) {
conn.setRequestMethod(“POST”);
}
else {*/
            if (requestmethod == GETRequest) {
                conn.setRequestMethod("GET");
            }

/* if (params != null) {
OutputStream ostream = conn.getOutputStream();
BufferedWriter writer = new BufferedWriter(
new OutputStreamWriter(ostream, “UTF-8”));
StringBuilder requestresult = new StringBuilder();
boolean first = true;
for (Map.Entry entry : params.entrySet()) {
if (first)
first = false;
else
requestresult.append(“&”);
requestresult.append(URLEncoder.encode(entry.getKey(), “UTF-8”));
requestresult.append(“=”);
requestresult.append(URLEncoder.encode(entry.getValue(), “UTF-8”));
}
writer.write(requestresult.toString());

writer.flush();
writer.close();
ostream.close();
}*/
            int reqresponseCode = conn.getResponseCode();

            if (reqresponseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String doHTTPGetRequest(String urlStr) throws Exception {

        String outputString = "";

        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String userCredentials = "irishapps_virtualgames" + ":" + "!r!2Hap9s@#16@virtualgames*";
        String basicAuth = "Basic " + new String(Base64.encode(userCredentials.getBytes(), 0));
        urlConnection.setRequestProperty("Authorization", basicAuth);



        try {
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader in=new BufferedReader(isr);
            String responseString=null;
            while ((responseString = in.readLine()) != null)
            {
                outputString = outputString + responseString;
            }
        }
        finally{
            urlConnection.disconnect();
        }

        return outputString;
    }
}
